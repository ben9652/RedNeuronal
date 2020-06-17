/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.neuronas.modelos;

import gui.excepciones.DimensionesIncompatibles;
import gui.excepciones.NoEsMatriz;
import gui.funciones.modelos.Lineal;
import gui.interfaces.Arreglos;
import gui.interfaces.IBackpropagation;
import gui.interfaces.IFuncionActivacion;
import java.util.Random;

/**
 *
 * @author Benjamin
 */
public abstract class CapaNeuronas {
    private int numeroCapa;
    protected int numeroDeNeuronasEnCapa;
    
    protected double[][] matrizPesos;               //El primer índice indica a qué neurona (empezando desde 0) de esta capa
                                                        //está conectada la arista con su respectivo peso.
                                                        //El segundo índice indica desde qué neurona de la capa anterior se dirige
                                                        //la arista, es decir, dónde tiene origen la arista.
                                                        //Por ejemplo: matrizPesos[j][k] es el peso de la arista cuyo origen está
                                                        //en la k-ésima neurona de la capa anterior, y su destino en la j-ésima
                                                        //neurona de esta capa.
    
    protected double[][] gradientePesos;      //Esta es la matriz con las derivadas calculadas que determinarán cómo es
                                                        //que se deberían cambiar los pesos de la matriz de los pesos.
                                                        //Es decir que contendrá dC/dw[j][k] = (1/n)*suma(0, n-1, dC[i]/dw[j][k]).
    
    protected double[][] gradientePesosSuma;  //Esta es una matriz que llevará la suma de todas las derivadas de los costos
                                                        //individuales respecto a los pesos.
                                                        //Es decir que contendrá suma(0, n-1, dC[i]/dw[j][k])
    
    protected IFuncionActivacion funcionAct;
    protected CapaNeuronas capaAnterior;
    protected CapaNeuronas capaSiguiente;
    
    protected double[] entrada;                   //Es la entrada que tiene la capa, que realmente serían las activaciones
                                                        //de la capa anterior, para las capas ocultas y final; y la entrada de la
                                                        //red, para la capa de entrada.
                                                        //Todas las activaciones de la capa anterior estarían conectadas a cada una
                                                        //de las neuronas de esta capa.
                                                        //En el caso de que esta sea la capa de entrada, cada una de las entradas
                                                        //corresponderían a cada una de las neuronas. Es decir que cada neurona de
                                                        //la capa de entrada solo tiene UNA entrada.
    
    protected double[] salida;                    //Es el vector de salida de las neuronas. Cada uno de los elementos
                                                        //corresponde a la salida de cada una de las neuronas.
                                                        //Por ejemplo: salida[i] es la salida de la i-ésima neurona que hay en la capa.

    protected double[] salidaAntesDeActivacion;   //Es la salida que ofrece cada una de las neuronas antes de activarse. Es decir,
                                                        //es la suma ponderada de las entradas multiplicadas por los pesos, más el
                                                        //bias del final.
    
    protected double[] biases;                    //Son los biases que tiene cada una de las neuronas.
    protected double[] gradienteBiases;
    protected double[] gradienteBiasesSuma;
    
    protected int numeroEntradas;                       //Si esta es la capa de entrada, sería igual a la cantidad de neuronas que hay
                                                        //en esta capa. En otro caso, sería el número de activaciones que le llega a cada
                                                        //neurona de esta capa.
    
    public CapaNeuronas(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas){
        this.numeroDeNeuronasEnCapa = numeroNeuronas;
        this.matrizPesos = new double[numeroNeuronas][numeroEntradas];
        this.biases = new double[numeroNeuronas];
        this.funcionAct = iaf;
        this.numeroEntradas = numeroEntradas;
        this.entrada = new double[numeroEntradas];
        this.salida = new double[numeroNeuronas];
        inicializar();
    }
    
    public CapaNeuronas(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas, double[][] pesos, double[] biases){
        this.numeroDeNeuronasEnCapa = numeroNeuronas;
        this.matrizPesos = pesos;
        this.biases = biases;
        this.funcionAct = iaf;
        this.numeroEntradas = numeroEntradas;
        this.entrada = new double[numeroEntradas];
        this.salida = new double[numeroNeuronas];
        
        this.gradientePesos = new double[pesos.length][pesos[0].length];
        this.gradientePesosSuma = new double[pesos.length][pesos[0].length];
    
        this.gradienteBiases = new double[biases.length];
        this.gradienteBiasesSuma = new double[biases.length];
    }
    
    public CapaNeuronas(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas, double[][] pesos) {
        this.numeroDeNeuronasEnCapa = numeroNeuronas;
        this.matrizPesos = pesos;
        this.biases = new double[numeroNeuronas];
        this.funcionAct = iaf;
        this.numeroEntradas = numeroEntradas;
        this.entrada = new double[numeroEntradas];
        this.salida = new double[numeroNeuronas];
        inicializar(true);
    }
    
    public CapaNeuronas(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas, double[] biases){
        this.numeroDeNeuronasEnCapa = numeroNeuronas;
        this.matrizPesos = new double[numeroNeuronas][numeroEntradas];
        this.biases = biases;
        this.funcionAct = iaf;
        this.numeroEntradas = numeroEntradas;
        this.entrada = new double[numeroEntradas];
        this.salida = new double[numeroNeuronas];
        inicializar(false);
    }
    
    public CapaNeuronas(int numeroEntradas){
        this.numeroEntradas = numeroEntradas;
        this.numeroDeNeuronasEnCapa = numeroEntradas;
        this.capaAnterior = null;
        this.capaSiguiente = null;
        this.entrada = new double[numeroEntradas];
        this.salida = new double[numeroEntradas];
        this.funcionAct = new Lineal(1.0);
    }
    
    private void inicializar() {
        Random random = new Random();
        
        for(int i = 0 ; i < this.numeroDeNeuronasEnCapa ; i++) {
            
            this.biases[i] = (-1.0) * random.nextDouble();
            
            for(int j = 0 ; j < this.numeroEntradas ; j++)
                this.matrizPesos[i][j] = random.nextDouble();
        }
        
        this.gradientePesos = new double[this.matrizPesos.length][this.matrizPesos[0].length];
        this.gradientePesosSuma = new double[this.matrizPesos.length][this.matrizPesos[0].length];
    
        this.gradienteBiases = new double[this.biases.length];
        this.gradienteBiasesSuma = new double[this.biases.length];
    }
    
    private void inicializar(boolean tienePesosIngresados) {
        Random random = new Random();
        
        for(int i = 0 ; i < this.numeroDeNeuronasEnCapa ; i++) {
            
            if(tienePesosIngresados) 
                this.biases[i] = (-1.0) * random.nextDouble();
            else {
                for(int j = 0 ; j < this.numeroEntradas ; j++)
                    this.matrizPesos[i][j] = random.nextDouble();
            }
        }
        
        this.gradientePesos = new double[this.matrizPesos.length][this.matrizPesos[0].length];
        this.gradientePesosSuma = new double[this.matrizPesos.length][this.matrizPesos[0].length];
    
        this.gradienteBiases = new double[this.biases.length];
        this.gradienteBiasesSuma = new double[this.biases.length];
    }
    
    protected void calculoSalida(boolean esEntrada) throws CapaSinEntrada, DimensionesIncompatibles, NoEsMatriz {
        if(this.numeroEntradas == 0) throw new CapaSinEntrada("¡Capa sin entradas!");

        if(!esEntrada) {
            //Se hace WX+B, donde W es la matriz de los pesos, X el vector de entrada, y B el vector de los biases;
            //y finalmente, se aplica la función de activación. Todo este resultado es la salida total de esta capa.
            double[] vectorProducto = Arreglos.producto(this.matrizPesos, this.entrada);
            this.salidaAntesDeActivacion = Arreglos.suma(vectorProducto, this.biases);
            this.salida = f(this.getSalidaAntesDeActivacion());
        }
        else
            //Se le aplica a la entrada la función de activación, y esta resulta ser la salida de la capa.
            this.salida = f(this.entrada);
    }
    
    protected void aprendizaje(IBackpropagation bp, double learningRate, double momento) throws DimensionesIncompatibles, NoEsMatriz {
        double[][][] gradientes = bp.actualizarParametros(this.gradientePesos, this.gradientePesosSuma, this.gradienteBiases, this.gradienteBiasesSuma, this);

        //Se realiza w(n) = w(n-1) - learningRate*dC/dw + momento*dw(n-1)
        this.matrizPesos = Arreglos.suma(Arreglos.resta(this.matrizPesos, Arreglos.producto(learningRate, this.gradientePesos)), Arreglos.producto(momento, gradientes[0]));
        //Se realiza b(n) = b(n-1) - learningRate*dC/db + momento*db(n-1)
        this.biases = Arreglos.suma(Arreglos.resta(this.biases, Arreglos.producto(learningRate, this.gradienteBiases)), Arreglos.producto(momento, gradientes[1][0]));
    }
    
    private double[] f(double[] vector) {
        double[] vectorFuncionAplicada = new double[vector.length];
        
        for(int i = 0 ; i < vector.length ; i++)
            vectorFuncionAplicada[i] = this.funcionAct.calc(vector[i]);
        
        return vectorFuncionAplicada;
    }
    
    public int getNumeroCapa() {
        return this.numeroCapa;
    }
    
    public void setNumeroCapa(int numeroCapa) {
        this.numeroCapa = numeroCapa;
    }

    /**
     * @return the numeroDeNeuronasEnCapa
     */
    public int getNumeroDeNeuronasEnCapa() {
        return numeroDeNeuronasEnCapa;
    }

    /**
     * @param numeroDeNeuronasEnCapa the numeroDeNeuronasEnCapa to set
     */
    public void setNumeroDeNeuronasEnCapa(int numeroDeNeuronasEnCapa) {
        this.numeroDeNeuronasEnCapa = numeroDeNeuronasEnCapa;
    }

    /**
     * @return the funcionAct
     */
    public IFuncionActivacion getFuncionAct() {
        return funcionAct;
    }

    /**
     * @param funcionAct the funcionAct to set
     */
    public void setFuncionAct(IFuncionActivacion funcionAct) {
        this.funcionAct = funcionAct;
    }

    /**
     * @return the capaAnterior
     */
    public CapaNeuronas getCapaAnterior() {
        return capaAnterior;
    }

    /**
     * @param capaAnterior the capaAnterior to set
     */
    public void setCapaAnterior(CapaNeuronas capaAnterior) {
        this.capaAnterior = capaAnterior;
    }

    /**
     * @return the capaSiguiente
     */
    public CapaNeuronas getCapaSiguiente() {
        return capaSiguiente;
    }

    /**
     * @param capaSiguiente the capaSiguiente to set
     */
    public void setCapaSiguiente(CapaNeuronas capaSiguiente) {
        this.capaSiguiente = capaSiguiente;
    }

    /**
     * @return the entrada
     */
    public double[] getEntrada() {
        return entrada;
    }

    /**
     * @param entrada the entrada to set
     */
    public void setEntrada(double[] entrada) {
        this.entrada = entrada;
    }

    /**
     * @return the salida
     */
    public double[] getSalida() {
        return salida;
    }

    /**
     * @param salida the salida to set
     */
    public void setSalida(double[] salida) {
        this.salida = salida;
    }

    /**
     * @return the salidaAntesDeActivacion
     */
    public double[] getSalidaAntesDeActivacion() {
        return salidaAntesDeActivacion;
    }

    /**
     * @return the numeroEntradas
     */
    public int getNumeroEntradas() {
        return numeroEntradas;
    }

    /**
     * @param numeroEntradas the numeroEntradas to set
     */
    public void setNumeroEntradas(int numeroEntradas) {
        this.numeroEntradas = numeroEntradas;
    }
    
    public int getNumeroSalidas() {
        return this.numeroDeNeuronasEnCapa;
    }

    /**
     * @param fila
     * @param columna
     * @return El número del elemento de la fila y columna indicada
     */
    public double getElementoMatrizPesos(int fila, int columna) {
        return this.matrizPesos[fila][columna];
    }
    
    public int getFilasMatrizPesos() {
        return this.matrizPesos.length;
    }
    
    public int getColumnasMatrizPesos() {
        return this.matrizPesos[0].length;
    }
    
    public double getElementoVectorBiases(int posicion) {
        return this.biases[posicion];
    }
    
    public void mostrarMatrizPesos() {
        Arreglos.print(this.matrizPesos);
    }
    
    public void mostrarVectorBiases() {
        Arreglos.print(this.biases);
    }
}
