/*
    La idea principal para armar la estructura de una red neuronal es comenzando
    a armar las capas: inicial, ocultas, y final.
    Lo que se debe tener en cuenta es que al comienzo se inicializan los pesos
    y biases de manera aleatoria.
    
*/
package gui.neuronas.modelos;

import gui.excepciones.BiasesIncompatiblesConRed;
import gui.excepciones.PesosIncompatiblesConRed;
import gui.aprendizaje.modelos.Backpropagation;
import gui.excepciones.DimensionesIncompatibles;
import gui.excepciones.NoEsMatriz;
import gui.interfaces.IBackpropagation;
import gui.interfaces.IFuncionActivacion;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Benjamin
 */
public class RedNeuronal {
    private CapaEntrada capaEntrada;
    private List<CapaOculta> capasOcultas;
    protected CapaSalida capaSalida;
    private CapaNeuronas ultimaCapa;
    private int numeroCapasOcultas = 0;
    private int numeroEntradas;
    private int numeroSalidas;
    protected double[] entrada;
    protected double[] salida;
    protected double rendimiento;
    protected double testsExitosos;
    protected double numeroTests;
    protected double tolerancia;
    private IBackpropagation bp;
    protected double costoPromedio;
    protected double costoTotal;
    
    public RedNeuronal(int numeroEntradas, 
                       int numeroSalidas, 
                       int [] numeroNeuronasOcultas, 
                       IFuncionActivacion [] fnActOcultas, 
                       IFuncionActivacion fnActSalida) {
        this.capasOcultas = new ArrayList<>();
        this.entrada = new double[numeroEntradas];
        this.salida = new double[numeroSalidas];
        this.costoPromedio = 10.0;
        this.numeroTests = 0;
        this.testsExitosos = 0;
        
        this.numeroEntradas = numeroEntradas;
        addCapa(new CapaEntrada(numeroEntradas));
        
        try {
            for(int i = 0 ; i < numeroNeuronasOcultas.length ; i++) {
                int entradas = this.ultimaCapa.getNumeroSalidas();
                addCapa(new CapaOculta(numeroNeuronasOcultas[i], fnActOcultas[i], entradas));
                this.capasOcultas.add((CapaOculta) this.ultimaCapa);
            }
        }
        catch(NullPointerException npe) {
            
        }
        
        int entradas = this.ultimaCapa.getNumeroSalidas();
        addCapaFinal(new CapaSalida(numeroSalidas, fnActSalida, entradas));
        this.numeroSalidas = numeroSalidas;
        
        this.bp = new Backpropagation();
        
        System.out.println("¡Red Neuronal creada!");
        this.print();
        System.out.println("\nEstado inicial de la red:");
        this.printMatricesPesos();
    }
    
    public RedNeuronal(int numeroEntradas, 
                       int numeroSalidas, 
                       int [] numeroNeuronasOcultas, 
                       IFuncionActivacion [] fnActOcultas, 
                       IFuncionActivacion fnActSalida,
                       double[][][] pesos,
                       double[][] biases) throws PesosIncompatiblesConRed, BiasesIncompatiblesConRed {
        
        if(pesos.length != numeroNeuronasOcultas.length + 1) 
            throw new PesosIncompatiblesConRed("Cantidad de matrices no compatible con red.");
        if(biases.length != numeroNeuronasOcultas.length + 1) 
            throw new BiasesIncompatiblesConRed("Cantidad de vectores no compatible con red.");

        for(int i = 0 ; i <= numeroNeuronasOcultas.length ; i++) {            
            for(int j = 0 ; j < pesos[i].length ; j++) {

                String errorColumnas = "La matriz de la capa " + (i + 1) + " tiene una cantidad de columnas distinta al número de neuronas de la capa " + i + ".";
                if(i == 0) {
                    if(pesos[i][j].length != numeroEntradas)
                        throw new PesosIncompatiblesConRed(errorColumnas);
                }
                else if(pesos[i][j].length != numeroNeuronasOcultas[i-1])
                    throw new PesosIncompatiblesConRed(errorColumnas);

                String errorFilas = "La matriz de la capa " + (i + 1) + " tiene una cantidad de filas distinta al número de neuronas que le corresponde.";
                if(i != numeroNeuronasOcultas.length) {
                    if(pesos[i].length != numeroNeuronasOcultas[i])
                        throw new PesosIncompatiblesConRed(errorFilas);
                }
                else {
                    if(pesos[i].length != numeroSalidas)
                        throw new PesosIncompatiblesConRed(errorFilas);
                }

                String errorVector = "El vector de biases de la capa " + (i + 1) + " tiene una dimensión distinta al número de neuronas que le corresponde.";
                if(i != numeroNeuronasOcultas.length) {
                    if(biases[i].length != numeroNeuronasOcultas[i])
                        throw new BiasesIncompatiblesConRed(errorVector);
                }
                else {
                    if(biases[i].length != numeroSalidas)
                        throw new BiasesIncompatiblesConRed(errorVector);
                }
            }
        }
        
        this.capasOcultas = new ArrayList<>();
        this.numeroEntradas = numeroEntradas;
        addCapa(new CapaEntrada(numeroEntradas));
        
        int i;
        for(i = 0 ; i < numeroNeuronasOcultas.length ; i++) {
            int entradas = this.ultimaCapa.getNumeroSalidas();
            addCapa(new CapaOculta(numeroNeuronasOcultas[i], fnActOcultas[i], entradas, pesos[i], biases[i]));
            this.capasOcultas.add((CapaOculta) this.ultimaCapa);
        }
        
        this.entrada = new double[numeroEntradas];
        this.salida = new double[numeroSalidas];
        this.costoPromedio = 10.0;
        this.costoTotal = 0.0;
        
        int entradas = this.ultimaCapa.getNumeroSalidas();
        addCapaFinal(new CapaSalida(numeroSalidas, fnActSalida, entradas, pesos[i], biases[i]));
        this.numeroSalidas = numeroSalidas;
        
        this.bp = new Backpropagation();
        
        System.out.println("¡Red Neuronal creada!");
        this.print();
        System.out.println("\nEstado inicial de la red:");
        this.printMatricesPesos();
    }
    
    public RedNeuronal(int numeroEntradas, 
                       int numeroSalidas, 
                       int [] numeroNeuronasOcultas, 
                       IFuncionActivacion [] fnActOcultas, 
                       IFuncionActivacion fnActSalida,
                       double[][][] pesos) throws PesosIncompatiblesConRed {
        
        if(pesos.length != numeroNeuronasOcultas.length + 1) 
            throw new PesosIncompatiblesConRed("Cantidad de matrices no compatible con red.");
        
        for(int i = 0 ; i <= numeroNeuronasOcultas.length ; i++) {            
            for(int j = 0 ; j < pesos[i].length ; j++) {

                String errorColumnas = "La matriz de la capa " + (i + 1) + " tiene una cantidad de columnas distinta al número de neuronas de la capa " + i + ".";
                if(i == 0) {
                    if(pesos[i][j].length != numeroEntradas)
                        throw new PesosIncompatiblesConRed(errorColumnas);
                }
                else if(pesos[i][j].length != numeroNeuronasOcultas[i-1])
                        throw new PesosIncompatiblesConRed(errorColumnas);

                String errorFilas = "La matriz de la capa " + i + " tiene una cantidad de filas distinta al número de neuronas que le corresponde.";
                if(i != numeroNeuronasOcultas.length) {
                    if(pesos[i].length != numeroNeuronasOcultas[i])
                        throw new PesosIncompatiblesConRed(errorFilas);
                }
                else {
                    if(pesos[i].length != numeroSalidas)
                        throw new PesosIncompatiblesConRed(errorFilas);
                }
            }
        }
        
        this.capasOcultas = new ArrayList<>();
        this.entrada = new double[numeroEntradas];
        this.salida = new double[numeroSalidas];
        this.costoPromedio = 10.0;
        this.costoTotal = 0.0;
        
        this.numeroEntradas = numeroEntradas;
        addCapa(new CapaEntrada(numeroEntradas));
        
        int i;
        try {
            for(i = 0 ; i < numeroNeuronasOcultas.length ; i++) {
                int entradas = this.ultimaCapa.getNumeroSalidas();
                addCapa(new CapaOculta(numeroNeuronasOcultas[i], fnActOcultas[i], entradas));
                this.capasOcultas.add((CapaOculta) this.ultimaCapa);
            }
        }
        catch(NullPointerException npe) {
            i = 0;
        }
        
        int entradas = this.ultimaCapa.getNumeroSalidas();
        addCapaFinal(new CapaSalida(numeroSalidas, fnActSalida, entradas, pesos[i]));
        this.numeroSalidas = numeroSalidas;
        
        this.bp = new Backpropagation();
        
        System.out.println("¡Red Neuronal creada!");
        this.print();
        System.out.println("\nEstado inicial de la red:");
        this.printMatricesPesos();
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
     * @throws gui.excepciones.DimensionesIncompatibles
     * @throws gui.excepciones.NoEsMatriz
     */
    private void calculoActivaciones() throws CapaSinEntrada, DimensionesIncompatibles, NoEsMatriz {
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
    private String calculoCosto(double[] salidaDeseada) {
        if(salidaDeseada.length != this.salida.length) 
            return "El tamaño de la salida deseada debe ser igual al de la salida de la red.\nIntente con otro conjunto de salidas, pero con el tamaño adecuado.";
        
        this.costoTotal = this.bp.actualizarCosto(this.salida, salidaDeseada, this.costoTotal);
        this.costoPromedio = (1.0 / this.bp.obtenerIteracion()) * this.costoTotal;
        
        String vectorEntrada = "[";
        for(int i = 0 ; i < this.entrada.length ; i++) {
            vectorEntrada += String.format("%.0f", this.entrada[i]);
            if(i != this.entrada.length - 1)
                vectorEntrada += ", ";
            else
                vectorEntrada += "]";
        }
        
        DecimalFormat df = new DecimalFormat("#.##");
        String vectorSalida = "[";
        for(int i = 0 ; i < this.salida.length ; i++) {
            vectorSalida += this.salida[i];
            if(i != this.salida.length - 1)
                vectorSalida += ", ";
            else
                vectorSalida += "]";
        }
        
        String vectorSalidaDeseada = "[";
        for(int i = 0 ; i < salidaDeseada.length ; i++) {
            vectorSalidaDeseada += salidaDeseada[i];
            if(i != this.salida.length - 1)
                vectorSalidaDeseada += ", ";
            else
                vectorSalidaDeseada += "]";
        }
        
        Double costoIndividual = 0.0;
        for(int i = 0 ; i < this.salida.length ; i++)
            costoIndividual += Math.pow(this.salida[i] - salidaDeseada[i], 2);
        
        return "\n\t\t--------------------O--------------------\n\nÉpoca: " + this.bp.obtenerIteracion() + "\nEntrada: " + vectorEntrada + "\nSalida esperada: " + vectorSalidaDeseada + "\nSalida: " + vectorSalida + "\nCosto: " + this.costoPromedio + "\nCosto individual: " + costoIndividual;
    }
    
    public String aprendizaje(double[] entrada, double[] salidaDeseada, double learningRate, double momento) throws DimensionesIncompatibles, NoEsMatriz, CapaSinEntrada {
        this.setEntrada(entrada);
        this.calculoActivaciones();
        
        String datos_entrenamiento = this.calculoCosto(salidaDeseada);
        
        for(CapaNeuronas capa = this.capaSalida ; !(capa instanceof CapaEntrada) ; capa = capa.getCapaAnterior())
            capa.aprendizaje(this.bp, learningRate, momento);
        
        return datos_entrenamiento;
    }
    
    public String test(double[] entrada, double[] salidaDeseada) throws DimensionesIncompatibles, CapaSinEntrada, NoEsMatriz {
        this.setEntrada(entrada);
        this.calculoActivaciones();
        
        this.calculoCosto(salidaDeseada);
        double costo = this.getCosto();
        this.numeroTests++;
        if(costo < this.tolerancia)
            this.testsExitosos++;
        
        this.rendimiento = this.testsExitosos / this.numeroTests;
        
        Double costoIndividual = 0.0;
        for(int i = 0 ; i < this.salida.length ; i++)
            costoIndividual += Math.pow(this.salida[i] - salidaDeseada[i], 2);
        
        return "\nCosto individual: " + costoIndividual + "\nRendimiento: " + this.rendimiento * 100 + " %";
    }
    
    public void determinarTolerancia(double tolerancia) {
        this.tolerancia = tolerancia;
    }
    
    public final void print(){
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
    
    public final void printMatricesPesos() {
        for(CapaNeuronas capa = this.capaEntrada.getCapaSiguiente() ; capa != null ; capa = capa.getCapaSiguiente()) {
            System.out.println("Pesos capa " + capa.getNumeroCapa() + ":");
            capa.mostrarMatrizPesos();

            System.out.println("\nBiases capa " + capa.getNumeroCapa() + ":");
            capa.mostrarVectorBiases();

            System.out.println("");
        }
    }
    
    public void salidas(){
        for(int i = 0 ; i < this.salida.length ; i++)
            System.out.println("Salida " + i + ": " + this.salida[i]);
    }

    /**
     * 
     * @return la entrada
     */
    public double[] getEntrada() {
        return entrada;
    }

    /**
     * 
     * @param entrada la entrada a setear
     * @throws gui.excepciones.DimensionesIncompatibles
     */
    private void setEntrada(double[] entrada) throws DimensionesIncompatibles {
        if(entrada.length != this.numeroEntradas) throw new DimensionesIncompatibles("La entrada propuesta tiene un tamaño distinto al de la entrada de la red.");
        this.entrada = entrada;
    }

    /**
     * 
     * @return la salida
     */
    public double[] getSalida() {
        return salida;
    }
    
    public double getCosto() {
        return this.costoPromedio;
    }
}
