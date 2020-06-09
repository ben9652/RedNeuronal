/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.neuronas.modelos;

import gui.funciones.modelos.Sigmoidea;
import gui.interfaces.IFuncionActivacion;
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
    
    protected int numeroEntradas;                       //Si esta es la capa de entrada, sería igual a la cantidad de neuronas que hay
                                                        //en esta capa. En otro caso, sería el número de activaciones que le llega a cada
                                                        //neurona de esta capa.
    
    public CapaNeuronas(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas){
        this.numeroDeNeuronasEnCapa = numeroNeuronas;
        this.matrizPesos = new Matriz();
        this.funcionAct = iaf;
        this.numeroEntradas = numeroEntradas;
        this.entrada = new Vector();
        this.salida = new Vector();
        this.biases = new Vector();
        inicializar();
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
    
    /**
     * Este método agrega las neuronas que le corresponden a la capa
     * @param esEntrada
     */
//    protected void inicializar(boolean esEntrada) {
//        if(!esEntrada) {
//            for(int i = 0 ; i < this.numeroDeNeuronasEnCapa ; i++) {
//                try {
//                    this.neuronas.get(i).setFuncionActivacion(this.funcionAct);
//                }
//                catch(IndexOutOfBoundsException iobe) {
//                    this.neuronas.add(new Neurona(this.numeroEntradas, this.funcionAct));
//                }
//                
//                this.neuronas.get(i).inicializar(this.matrizPesos);
//            }
//        }
//        else {
//            for(int i = 0 ; i < this.numeroDeNeuronasEnCapa ; i++)
//                this.neuronas.add(new Neurona(1, this.funcionAct));
//        }
//    }
    
    private void inicializar() {
        Random random = new Random();
        
        for(int i = 0 ; i < this.numeroDeNeuronasEnCapa ; i++) {
            Double[] pesos = new Double[this.numeroEntradas];
            
            for(int j = 0 ; j < this.numeroEntradas ; j++)
                pesos[j] = random.nextDouble();
            
            this.matrizPesos.addRow(pesos);
        }
        
        Double[] biasesList = new Double[this.numeroDeNeuronasEnCapa];
        for(int i = 0 ; i < this.numeroDeNeuronasEnCapa ; i++)
            biasesList[i] = -1.0;
        
        this.biases = new Vector(biasesList);
    }
    
    /**
     * Realiza los cálculos correspondientes en las neuronas para ofrecer
     * una salida en las mismas activándolas.
     * @param esEntrada
     */
//    protected void calculosInternos(boolean esEntrada) {
//        for(int i = 0 ; i < this.numeroDeNeuronasEnCapa ; i++) {
//            if(!esEntrada) this.neuronas.get(i).setEntrada(this.entrada.toList());
//            else 
//                this.neuronas.get(i).setEntrada(this.entrada.getElemento(i));
//            this.neuronas.get(i).activar(esEntrada);
//            this.salida.addRow(this.neuronas.get(i).getSalida());
//        }
//    }
    
    protected void calculoSalida(boolean esEntrada) {
        try {
            if(this.numeroEntradas == 0) throw new CapaSinEntrada("¡Capa sin entradas!");
            
            if(!esEntrada) {
                //Se hace WX+B, donde W es la matriz de los pesos, X el vector de entrada, y B el vector de los biases;
                //y finalmente, se aplica la función de activación. Todo este resultado es la salida total de esta capa.
                Vector<Double> vectorProducto = Matriz.producto(this.matrizPesos, this.entrada);
                this.salidaAntesDeActivacion = Vector.suma(vectorProducto, this.biases);
                this.salida = f(this.salidaAntesDeActivacion);
            }
            else
                //Se le aplica a la entrada la función de activación, y esta resulta ser la salida de la capa.
                this.salida = f(this.entrada);
        }
        catch(CapaSinEntrada cse) {
            System.out.println(cse.getMessage());
        }
    }
    
    private Vector<Double> f(Vector<Double> vector) {
        Vector<Double> vectorFuncionAplicada = new Vector();
        
        for(int i = 0 ; i < vector.size() ; i++)
            vectorFuncionAplicada.addRow(this.funcionAct.calc(vector.getElemento(i).getElemento()));
        
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
    public void setEntrada(List<Double> entrada) {
        this.entrada = new Vector(entrada);
    }

    /**
     * @return the salida
     */
    public List<Double> getSalida() {
        return salida.toListTipo();
    }

    /**
     * @param salida the salida to set
     */
    public void setSalida(Vector<Double> salida) {
        this.salida = salida;
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
}
