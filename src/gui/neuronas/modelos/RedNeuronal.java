/*
    La idea principal para armar la estructura de una red neuronal es comenzando
    a armar las capas: inicial, ocultas, y final.
    Lo que se debe tener en cuenta es que al comienzo se inicializan los pesos
    y biases de manera aleatoria.
    
*/
package gui.neuronas.modelos;

import gui.aprendizaje.modelos.Backpropagation;
import gui.interfaces.IBackpropagation;
import gui.interfaces.IFuncionActivacion;
import gui.matrices.modelos.DimensionesIncompatibles;
import gui.matrices.modelos.Matriz;
import gui.matrices.modelos.Vector;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Benjamin
 */
public class RedNeuronal {
    private CapaEntrada capaEntrada;
    private List<CapaOculta> capasOcultas;
    private CapaSalida capaSalida;
    private CapaNeuronas ultimaCapa;
    private int numeroCapasOcultas = 0;
    private int numeroEntradas;
    private int numeroSalidas;
    protected Vector<Double> entrada;
    protected Vector<Double> salida;
    private IBackpropagation bp;
    private Double costoPromedio;
    private Double costoTotal;
    
    public RedNeuronal(int numeroEntradas, 
                       int numeroSalidas, 
                       int [] numeroNeuronasOcultas, 
                       IFuncionActivacion [] fnActOcultas, 
                       IFuncionActivacion fnActSalida, 
                       Double[][][] pesos) {
        this.capasOcultas = new ArrayList<>();
        this.entrada = new Vector();
        this.salida = new Vector();
        this.costoPromedio = 10.0;
        this.costoTotal = 0.0;
        
        this.numeroEntradas = numeroEntradas;
        addCapa(new CapaEntrada(numeroEntradas));
        
        for(int i = 0 ; i < numeroNeuronasOcultas.length ; i++) {
            int entradas = this.ultimaCapa.getNumeroSalidas();
            addCapa(new CapaOculta(numeroNeuronasOcultas[i], fnActOcultas[i], entradas));
            this.capasOcultas.add((CapaOculta) this.ultimaCapa);
        }
        
        int entradas = this.ultimaCapa.getNumeroSalidas();
        addCapaFinal(new CapaSalida(numeroSalidas, fnActSalida, entradas));
        this.numeroSalidas = numeroSalidas;
        
        this.bp = new Backpropagation();
    }
    
    private void addCapa(CapaNeuronas capaNueva) {
        if(this.capaEntrada == null) {
             this.capaEntrada = (CapaEntrada) capaNueva;
             this.ultimaCapa = (CapaEntrada) capaNueva;
             this.ultimaCapa.setNumeroCapa(0);
        }
        else {
            capaNueva.setCapaAnterior(this.ultimaCapa);
            this.ultimaCapa.setCapaSiguiente((CapaOculta) capaNueva);
            this.ultimaCapa = (CapaOculta) capaNueva;
            this.numeroCapasOcultas++;
            this.ultimaCapa.setNumeroCapa(this.numeroCapasOcultas);
        }
    }
    
    private void addCapaFinal(CapaSalida capaFinal) {
        capaFinal.setCapaAnterior(this.ultimaCapa);
        this.ultimaCapa.setCapaSiguiente((CapaSalida) capaFinal);
        this.ultimaCapa = capaFinal;
        this.capaSalida = capaFinal;
        this.ultimaCapa.setNumeroCapa(this.numeroCapasOcultas + 1);
    }
    
    /**
     * Se inicializan los datos en la capa de entrada
     * @throws gui.neuronas.modelos.CapaSinEntrada
     * @throws gui.matrices.modelos.DimensionesIncompatibles
     */
    public void calculoActivaciones() throws CapaSinEntrada, DimensionesIncompatibles {
        this.capaEntrada.setEntrada(this.entrada);
        this.capaEntrada.calculoSalida(true);
        
        for(int i = 0 ; i < this.numeroCapasOcultas ; i++) {
            CapaOculta hl = this.capasOcultas.get(i);
            hl.setEntrada(hl.getCapaAnterior().getSalida());
            hl.calculoSalida(false);
        }
        
        this.capaSalida.setEntrada(this.capaSalida.getCapaAnterior().getSalida());
        this.capaSalida.calculoSalida(false);
        this.salida = this.capaSalida.getSalida();
    }
    
    /**
     * 
     * @param salidaDeseada
     * @return 
     */
    public String calculoCosto(Vector<Double> salidaDeseada) {
        if(salidaDeseada.size() != this.salida.size()) 
            return "El tamaño de la salida deseada debe ser igual al de la salida de la red.\nIntente con otro conjunto de salidas, pero con el tamaño adecuado.";
        
        this.costoTotal = this.bp.actualizarCosto(this.salida, salidaDeseada, this.costoTotal);
        this.costoPromedio = (1.0/this.bp.obtenerIteracion()) * this.costoTotal;
        
        String vectorEntrada = "[";
        for(int i = 0 ; i < this.entrada.size() ; i++) {
            vectorEntrada += String.format("%.0f", this.entrada.get(i).getElemento());
            if(i != this.entrada.size() - 1)
                vectorEntrada += ", ";
            else
                vectorEntrada += "]";
        }
        
        String vectorSalida = "[";
        for(int i = 0 ; i < this.salida.size() ; i++) {
            vectorSalida += this.salida.get(i).getElemento();
            if(i != this.salida.size() - 1)
                vectorSalida += ", ";
            else
                vectorSalida += "]";
        }
        
        String vectorSalidaDeseada = "[";
        for(int i = 0 ; i < salidaDeseada.size() ; i++) {
            vectorSalidaDeseada += salidaDeseada.get(i).getElemento();
            if(i != this.salida.size() - 1)
                vectorSalidaDeseada += ", ";
            else
                vectorSalidaDeseada += "]";
        }
        
        return "\n\t\t--------------------O--------------------\n\nÉpoca: " + this.bp.obtenerIteracion() + "\nEntrada: " + vectorEntrada + "\nSalida esperada: " + vectorSalidaDeseada + "\nSalida: " + vectorSalida + "\nCosto: " + this.costoPromedio;
    }
    
    public void aprendizaje(Double learningRate) throws DimensionesIncompatibles {
        for(CapaNeuronas capa = this.capaSalida ; !(capa instanceof CapaEntrada) ; capa = capa.getCapaAnterior())
            capa.aprendizaje(this.bp, learningRate);
    }
    
    public void print(){
        System.out.println("Red Neuronal: "
                + "\n\tEntradas: " + this.numeroEntradas
                + "\n\tSalidas: " + this.numeroSalidas
                + "\n\tCapas ocultas: " + this.numeroCapasOcultas);
        int i = 0;
        for(CapaOculta aux : this.capasOcultas) {
            System.out.println("\t--------Capa oculta " + i + ": " + aux.getNumeroDeNeuronasEnCapa() + " neuronas");
            i++;
        }
    }
    
    public void printMatricesPesos() {
        for(CapaNeuronas capa = this.capaEntrada.getCapaSiguiente() ; capa != null ; capa = capa.getCapaSiguiente()) {
            System.out.println("Pesos capa " + capa.getNumeroCapa() + ":");
            capa.mostrarMatrizPesos();

            System.out.println("\nBiases capa " + capa.getNumeroCapa() + ":");
            capa.mostrarVectorBiases();

            System.out.println("");
        }
    }
    
    public void salidas(){
        for(int i = 0 ; i < this.salida.size() ; i++)
            System.out.println("Salida " + i + ": " + this.salida.get(i).getElemento());
    }

    /**
     * 
     * @return la entrada
     */
    public Vector<Double> getEntrada() {
        return entrada;
    }

    /**
     * 
     * @param entrada la entrada a setear
     */
    public void setEntrada(Vector<Double> entrada) {
        this.entrada = entrada;
    }

    /**
     * 
     * @return la salida
     */
    public Vector<Double> getSalida() {
        return salida;
    }
    
    public Double getCosto() {
        return this.costoPromedio;
    }
}
