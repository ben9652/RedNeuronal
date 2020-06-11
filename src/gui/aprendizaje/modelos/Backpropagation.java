/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.aprendizaje.modelos;

import gui.interfaces.IBackpropagation;
import gui.matrices.modelos.Elemento;
import gui.matrices.modelos.Matriz;
import gui.matrices.modelos.Vector;
import gui.neuronas.modelos.CapaNeuronas;
import gui.neuronas.modelos.CapaSalida;

/**
 * 
 * @author Benjamin
 */
public class Backpropagation implements IBackpropagation {
    private int iteracion;
    private Double costo;                           //Este es el error que presenta la red respecto a los resultados deseados
    private Vector<Double> salidaDeseada;
    private Double learningRate;
    
    public Backpropagation(Vector<Double> salida, Vector<Double> salidaDeseada, Double learningRate) {
        this.iteracion = 0;
        this.costo = 0.0;
        this.learningRate = learningRate;
        this.actualizarCosto(salida, salidaDeseada);
    }
    
    @Override
    public final void actualizarCosto(Vector<Double> salida, Vector<Double> salidaDeseada) {        
        for(int i = 0 ; i < salida.size() ; i++)
            this.costo += Math.pow(salida.get(i).getElemento() - salidaDeseada.get(i).getElemento(), 2);
        this.iteracion++;
        this.salidaDeseada = salidaDeseada;
    }
    
    public Double getCosto() {
        return this.costo;
    }
    
    public int getIteracion() {
        return this.iteracion;
    }
    
    public Double getLearningRate() {
        return this.learningRate;
    }

    @Override
    public Double dC_dw(int j, int k, CapaNeuronas capa) {
        return  dC_da(j, capa)*
                capa.getFuncionAct().derivada(capa.getSalidaAntesDeActivacion().get(j).getElemento())*
                capa.getCapaAnterior().getSalida().get(k).getElemento();
    }

    @Override
    public Double dC_db(int j, CapaNeuronas capa) {
        return  dC_da(j, capa)*
                capa.getFuncionAct().derivada(capa.getSalidaAntesDeActivacion().get(j).getElemento());
    }
    
    private Double dC_da(int neurona, CapaNeuronas capa) {
        if(capa instanceof CapaSalida)
            return 2*(capa.getSalida().get(neurona).getElemento() - this.salidaDeseada.get(neurona).getElemento());
        else {
            CapaNeuronas sig = capa.getCapaSiguiente();
            int neuronasSigCapa = sig.getNumeroDeNeuronasEnCapa();
            Double suma = 0.0;
            for(int j = 0 ; j < neuronasSigCapa ; j++)
                suma += dC_da(j, sig)*
                        sig.getFuncionAct().derivada(sig.getSalidaAntesDeActivacion().get(neurona).getElemento())*
                        sig.getElementoMatrizPesos(j, neurona).getElemento();
            return suma;
        }
    }
    
    public void actualizarGradiente(Matriz<Double> grad, Matriz<Double> gradNeto, CapaNeuronas capa) {
        int j, k;
        
        k = 0;
        for(Elemento<Double> primerElemGrad = grad.get(0, 0), primerElemGradNeto = gradNeto.get(0, 0);
            primerElemGrad != null || primerElemGradNeto != null;
            primerElemGrad = primerElemGrad.getDerecha(), primerElemGradNeto = primerElemGradNeto.getDerecha(), k++) {
            
            j = 0;
            for(Elemento<Double> indiceGrad = primerElemGrad, indiceGradNeto = primerElemGradNeto;
                indiceGrad != null || indiceGradNeto != null;
                indiceGrad = indiceGrad.getAbajo(), indiceGradNeto = indiceGradNeto.getAbajo(), j++) {
                
                indiceGradNeto.setElemento(indiceGradNeto.getElemento() + this.dC_dw(j, k, capa));
                indiceGrad.setElemento((1/this.iteracion)*indiceGradNeto.getElemento());
            }
        }
            
    }
}
