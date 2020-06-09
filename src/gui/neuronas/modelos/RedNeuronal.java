/*
    La idea principal para armar la estructura de una red neuronal es comenzando
    a armar las capas: inicial, ocultas, y final.
    Lo que se debe tener en cuenta es que al comienzo se inicializan los pesos
    y biases de manera aleatoria.
    
*/
package gui.neuronas.modelos;

import gui.interfaces.IFuncionActivacion;
import gui.matrices.modelos.Matriz;
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
    protected List<Double> entrada;
    protected List<Double> salida;
    
    public RedNeuronal(int numeroEntradas, 
                       int numeroSalidas, 
                       int [] numeroNeuronasOcultas, 
                       IFuncionActivacion [] fnActOcultas, 
                       IFuncionActivacion fnActSalida) {
        this.capasOcultas = new ArrayList<>();
        this.entrada = new ArrayList<>(numeroEntradas);
        this.salida = new ArrayList<>(numeroSalidas);
        
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
     */
    public void calculosInternos() {
        this.capaEntrada.setEntrada(this.entrada);
        this.capaEntrada.calculosInternos(true);
        
        for(int i = 0 ; i < this.numeroCapasOcultas ; i++) {
            CapaOculta hl = this.capasOcultas.get(i);
            hl.setEntrada(hl.getCapaAnterior().getSalida());
            hl.calculosInternos(false);
        }
        
        this.capaSalida.setEntrada(this.capaSalida.getCapaAnterior().getSalida());
        this.capaSalida.calculosInternos(false);
        this.salida = this.capaSalida.getSalida();
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
        for(CapaNeuronas capa = (CapaEntrada)this.capaEntrada ; capa != null ; capa = capa.getCapaSiguiente()) {
            try {
                Number[][] matrizNumeros = new Number[capa.matrizPesos.getFilas()][capa.matrizPesos.getColumnas()];
                for(int i = 0 ; i < capa.matrizPesos.getFilas() ; i++) {
                    for(int j = 0 ; j < capa.matrizPesos.getColumnas() ; j++)
                        matrizNumeros[i][j] = capa.matrizPesos.getElemento(i, j).getElemento().getPeso();
                }
                Matriz<Number> matriz = new Matriz(matrizNumeros);
                System.out.println("\n");
                matriz.print();
            }
            catch(NullPointerException ipe) {}
        }
    }
    
    public void salidas(){
        int i = 0;
        for(Double d : this.salida){
            System.out.println("Salida " + i + ": " + d);
            i++;
        }
    }

    /**
     * 
     * @return la entrada
     */
    public List<Double> getEntrada() {
        return entrada;
    }

    /**
     * 
     * @param entrada la entrada a setear
     */
    public void setEntrada(List<Double> entrada) {
        this.entrada = entrada;
    }

    /**
     * 
     * @return la salida
     */
    public List<Double> getSalida() {
        return salida;
    }
}
