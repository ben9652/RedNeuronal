/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.neuronas.modelos;

import gui.funciones.modelos.Sigmoidea;
import gui.interfaces.IFuncionActivacion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Benjamin
 */
public abstract class CapaNeuronas {
    private int numeroCapa;
    protected int numeroDeNeuronasEnCapa;
    protected List<Neurona> neuronas;
    protected IFuncionActivacion funcionAct;
    protected CapaNeuronas capaAnterior;
    protected CapaNeuronas capaSiguiente;
    protected List<Double> entrada;
    protected List<Double> salida;
    protected int numeroEntradas;
    
    public CapaNeuronas(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas){
        this.numeroDeNeuronasEnCapa = numeroNeuronas;
        this.neuronas = new ArrayList<>(numeroNeuronas);
        this.funcionAct = iaf;
        this.numeroEntradas = numeroEntradas;
        this.entrada = new ArrayList<>(numeroEntradas);
        this.salida = new ArrayList<>(numeroNeuronas);
        inicializar(false);
    }
    
    public CapaNeuronas(int numeroEntradas){
        this.numeroEntradas = numeroEntradas;
        this.neuronas = new ArrayList<>(numeroEntradas);
        this.numeroDeNeuronasEnCapa = numeroEntradas;
        this.capaAnterior = null;
        this.capaSiguiente = null;
        this.entrada = new ArrayList<>(numeroEntradas);
        this.salida = new ArrayList<>(numeroEntradas);
        this.funcionAct = new Sigmoidea(1.0);
        inicializar(true);
    }
    
    /**
     * Este método agrega las neuronas que le corresponden a la capa
     * @param esEntrada
     */
    protected void inicializar(boolean esEntrada) {
        if(!esEntrada) {
            for(int i = 0 ; i < this.numeroDeNeuronasEnCapa ; i++) {
                try {
                    this.neuronas.get(i).setFuncionActivacion(this.funcionAct);
                }
                catch(IndexOutOfBoundsException iobe) {
                    this.neuronas.add(new Neurona(this.numeroEntradas, this.funcionAct));
                }
                
                this.neuronas.get(i).inicializar();
            }
        }
        else {
            for(int i = 0 ; i < this.numeroDeNeuronasEnCapa ; i++)
                this.neuronas.add(new Neurona(1, this.funcionAct));
        }
    }
    
    /**
     * Realiza los cálculos correspondientes en las neuronas para ofrecer
     * una salida en las mismas activándolas.
     * @param esEntrada
     */
    protected void calculosInternos(boolean esEntrada) {
        for(int i = 0 ; i < this.numeroDeNeuronasEnCapa ; i++) {
            if(!esEntrada) this.neuronas.get(i).setEntrada(this.entrada);
            else {
                this.neuronas.get(i).setEntrada(this.entrada.get(i));
            }
            this.neuronas.get(i).activar(esEntrada);
            try {
                this.salida.set(i, this.neuronas.get(i).getSalida());
            }
            catch(IndexOutOfBoundsException iobe) {
                this.salida.add(this.neuronas.get(i).getSalida());
            }
        }
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
     * @return the neuronas
     */
    public List<Neurona> getNeuronas() {
        return neuronas;
    }

    /**
     * @param neuronas the neuronas to set
     */
    public void setNeuronas(List<Neurona> neuronas) {
        this.neuronas = neuronas;
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
        return entrada;
    }

    /**
     * @param entrada the entrada to set
     */
    public void setEntrada(List<Double> entrada) {
        this.entrada = entrada;
    }

    /**
     * @return the salida
     */
    public List<Double> getSalida() {
        return salida;
    }

    /**
     * @param salida the salida to set
     */
    public void setSalida(List<Double> salida) {
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
        return this.neuronas.size();
    }
}
