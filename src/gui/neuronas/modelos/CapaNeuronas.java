/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.neuronas.modelos;

import gui.funciones.modelos.Sigmoidea;
import gui.interfaces.IBackpropagation;
import gui.interfaces.IFuncionActivacion;
import gui.matrices.modelos.DimensionesIncompatibles;
import gui.matrices.modelos.Matriz;
import gui.matrices.modelos.Vector;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Benjamin
 */
public abstract class CapaNeuronas {
    private int numeroCapa;
    protected int numeroDeNeuronasEnCapa;
    
    protected Matriz<Double> matrizPesos;               //El primer índice indica a qué neurona (empezando desde 0) de esta capa
                                                        //está conectada la arista con su respectivo peso.
                                                        //El segundo índice indica desde qué neurona de la capa anterior se dirige
                                                        //la arista, es decir, dónde tiene origen la arista.
                                                        //Por ejemplo: matrizPesos[j][k] es el peso de la arista cuyo origen está
                                                        //en la k-ésima neurona de la capa anterior, y su destino en la j-ésima
                                                        //neurona de esta capa.
    
    protected Matriz<Double> matrizGradientePesos;      //Esta es la matriz con las derivadas calculadas que determinarán cómo es
                                                        //que se deberían cambiar los pesos de la matriz de los pesos.
                                                        //Es decir que contendrá dC/dw[j][k] = (1/n)*suma(0, n-1, dC[i]/dw[j][k]).
    
    protected Matriz<Double> matrizGradientePesosSuma;  //Esta es una matriz que llevará la suma de todas las derivadas de los costos
                                                        //individuales respecto a los pesos.
                                                        //Es decir que contendrá suma(0, n-1, dC[i]/dw[j][k])
    
    protected IFuncionActivacion funcionAct;
    protected CapaNeuronas capaAnterior;
    protected CapaNeuronas capaSiguiente;
    
    protected Vector<Double> entrada;                   //Es la entrada que tiene la capa, que realmente serían las activaciones
                                                        //de la capa anterior, para las capas ocultas y final; y la entrada de la
                                                        //red, para la capa de entrada.
                                                        //Todas las activaciones de la capa anterior estarían conectadas a cada una
                                                        //de las neuronas de esta capa.
                                                        //En el caso de que esta sea la capa de entrada, cada una de las entradas
                                                        //corresponderían a cada una de las neuronas. Es decir que cada neurona de
                                                        //la capa de entrada solo tiene UNA entrada.
    
    protected Vector<Double> salida;                    //Es el vector de salida de las neuronas. Cada uno de los elementos
                                                        //corresponde a la salida de cada una de las neuronas.
                                                        //Por ejemplo: salida[i] es la salida de la i-ésima neurona que hay en la capa.

    protected Vector<Double> salidaAntesDeActivacion;   //Es la salida que ofrece cada una de las neuronas antes de activarse. Es decir,
                                                        //es la suma ponderada de las entradas multiplicadas por los pesos, más el
                                                        //bias del final.
    
    protected Vector<Double> biases;                    //Son los biases que tiene cada una de las neuronas.
    protected Vector<Double> gradienteBiases;
    protected Vector<Double> gradienteBiasesSuma;
    
    protected int numeroEntradas;                       //Si esta es la capa de entrada, sería igual a la cantidad de neuronas que hay
                                                        //en esta capa. En otro caso, sería el número de activaciones que le llega a cada
                                                        //neurona de esta capa.
    
    public CapaNeuronas(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas){
        this.numeroDeNeuronasEnCapa = numeroNeuronas;
        this.matrizPesos = new Matriz();
        this.biases = new Vector();
        this.funcionAct = iaf;
        this.numeroEntradas = numeroEntradas;
        this.entrada = new Vector();
        this.salida = new Vector();
        inicializar();
    }
    
    public CapaNeuronas(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas, Double[][] pesos, Double[] biases){
        this.numeroDeNeuronasEnCapa = numeroNeuronas;
        this.matrizPesos = new Matriz(pesos);
        this.biases = new Vector(biases);
        this.funcionAct = iaf;
        this.numeroEntradas = numeroEntradas;
        this.entrada = new Vector();
        this.salida = new Vector();
        
        this.matrizGradientePesos = Matriz.crearMatrizDouble(this.matrizPesos.getFilas(), this.matrizPesos.getColumnas());
        this.matrizGradientePesosSuma = Matriz.crearMatrizDouble(this.matrizPesos.getFilas(), this.matrizPesos.getColumnas());
    
        this.gradienteBiases = Vector.crearVectorDouble(this.biases.size());
        this.gradienteBiasesSuma = Vector.crearVectorDouble(this.biases.size());
    }
    
    public CapaNeuronas(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas, Double[][] pesos){
        this.numeroDeNeuronasEnCapa = numeroNeuronas;
        this.matrizPesos = new Matriz(pesos);
        this.biases = new Vector();
        this.funcionAct = iaf;
        this.numeroEntradas = numeroEntradas;
        this.entrada = new Vector();
        this.salida = new Vector();
        inicializar(true);
    }
    
    public CapaNeuronas(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas, Double[] biases){
        this.numeroDeNeuronasEnCapa = numeroNeuronas;
        this.matrizPesos = new Matriz();
        this.biases = new Vector(biases);
        this.funcionAct = iaf;
        this.numeroEntradas = numeroEntradas;
        this.entrada = new Vector();
        this.salida = new Vector();
        inicializar(false);
    }
    
    public CapaNeuronas(int numeroEntradas){
        this.numeroEntradas = numeroEntradas;
        this.numeroDeNeuronasEnCapa = numeroEntradas;
        this.capaAnterior = null;
        this.capaSiguiente = null;
        this.entrada = new Vector();
        this.salida = new Vector();
        this.funcionAct = new Sigmoidea(1.0);
    }
    
    private void inicializar() {
        Random random = new Random();
        
        for(int i = 0 ; i < this.numeroDeNeuronasEnCapa ; i++) {
            Double[] pesos = new Double[this.numeroEntradas];
            
            this.biases.addRow((-1.0) * random.nextDouble());
            
            for(int j = 0 ; j < this.numeroEntradas ; j++)
                pesos[j] = random.nextDouble();
            
            this.matrizPesos.addRow(pesos);
        }
        
        this.matrizGradientePesos = Matriz.crearMatrizDouble(this.matrizPesos.getFilas(), this.matrizPesos.getColumnas());
        this.matrizGradientePesosSuma = Matriz.crearMatrizDouble(this.matrizPesos.getFilas(), this.matrizPesos.getColumnas());
    
        this.gradienteBiases = Vector.crearVectorDouble(this.biases.size());
        this.gradienteBiasesSuma = Vector.crearVectorDouble(this.biases.size());
    }
    
    private void inicializar(boolean tienePesosIngresados) {
        Random random = new Random();
        
        for(int i = 0 ; i < this.numeroDeNeuronasEnCapa ; i++) {
            Double[] pesos = new Double[this.numeroEntradas];
            
            if(tienePesosIngresados) 
                this.biases.addRow((-1.0) * random.nextDouble());
            else {
                for(int j = 0 ; j < this.numeroEntradas ; j++)
                    pesos[j] = random.nextDouble();
                
                this.matrizPesos.addRow(pesos);
            }
        }
        
        this.matrizGradientePesos = Matriz.crearMatrizDouble(this.matrizPesos.getFilas(), this.matrizPesos.getColumnas());
        this.matrizGradientePesosSuma = Matriz.crearMatrizDouble(this.matrizPesos.getFilas(), this.matrizPesos.getColumnas());
    
        this.gradienteBiases = Vector.crearVectorDouble(this.biases.size());
        this.gradienteBiasesSuma = Vector.crearVectorDouble(this.biases.size());
    }
    
    protected void calculoSalida(boolean esEntrada) throws CapaSinEntrada, DimensionesIncompatibles {
        if(this.numeroEntradas == 0) throw new CapaSinEntrada("¡Capa sin entradas!");

        if(!esEntrada) {
            //Se hace WX+B, donde W es la matriz de los pesos, X el vector de entrada, y B el vector de los biases;
            //y finalmente, se aplica la función de activación. Todo este resultado es la salida total de esta capa.
            Vector<Double> vectorProducto = Matriz.producto(this.matrizPesos, this.entrada);
            this.salidaAntesDeActivacion = Vector.suma(vectorProducto, this.biases);
            this.salida = f(this.getSalidaAntesDeActivacion());
        }
        else
            //Se le aplica a la entrada la función de activación, y esta resulta ser la salida de la capa.
            this.salida = f(this.entrada);
    }
    
    protected void aprendizaje(IBackpropagation bp, Double learningRate) throws DimensionesIncompatibles {
        bp.actualizarParametros(this.matrizGradientePesos, this.matrizGradientePesosSuma, this.gradienteBiases, this.gradienteBiasesSuma, this);

        this.matrizPesos = Matriz.resta(this.matrizPesos, Matriz.producto(learningRate, this.matrizGradientePesos));
        this.biases = Vector.resta(this.biases, Vector.producto(learningRate, this.gradienteBiases));
    }
    
    private Vector<Double> f(Vector<Double> vector) {
        Vector<Double> vectorFuncionAplicada = new Vector();
        
        for(int i = 0 ; i < vector.size() ; i++)
            vectorFuncionAplicada.addRow(this.funcionAct.calc(vector.get(i).getElemento()));
        
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
    public List<Double> getEntrada() {
        return entrada.toListTipo();
    }

    /**
     * @param entrada the entrada to set
     */
    public void setEntrada(Vector<Double> entrada) {
        this.entrada = entrada;
    }

    /**
     * @return the salida
     */
    public Vector<Double> getSalida() {
        return salida;
    }

    /**
     * @param salida the salida to set
     */
    public void setSalida(Vector<Double> salida) {
        this.salida = salida;
    }

    /**
     * @return the salidaAntesDeActivacion
     */
    public Vector<Double> getSalidaAntesDeActivacion() {
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
    public Double getElementoMatrizPesos(int fila, int columna) {
        return this.matrizPesos.get(fila, columna).getElemento();
    }
    
    public int getFilasMatrizPesos() {
        return this.matrizPesos.getFilas();
    }
    
    public int getColumnasMatrizPesos() {
        return this.matrizPesos.getColumnas();
    }
    
    public void mostrarMatrizPesos() {
        this.matrizPesos.print();
    }
    
    public void mostrarVectorBiases() {
        this.biases.print();
    }
}
