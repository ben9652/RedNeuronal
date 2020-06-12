/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.aprendizaje.modelos;

import gui.interfaces.IBackpropagation;
import gui.matrices.modelos.Elemento;
import gui.matrices.modelos.IndiceFueraDeRango;
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
    private Vector<Double> salidaDeseada;
    
    //Estas tres variables de instancia serán usadas para no tener que recalcular tantas veces dC_da
    private Double derivadaCostoRespectoActivacion;
    private Integer j;
    private Integer numeroCapa;
    
    public Backpropagation() {
        this.iteracion = 0;
    }
    
    @Override
    public final Double actualizarCosto(Vector<Double> salida, Vector<Double> salidaDeseada, Double costoTotal) { 
        Double costoRed = 0.0;
        for(int i = 0 ; i < salida.size() ; i++)
            costoRed += Math.pow(salida.get(i).getElemento() - salidaDeseada.get(i).getElemento(), 2);
        this.iteracion++;
        this.salidaDeseada = salidaDeseada;
        
        costoTotal += costoRed;
        
        return costoTotal;
    }
    
    @Override
    public int obtenerIteracion() {
        return this.iteracion;
    }

    @Override
    public Double dC_dw(int j, int k, CapaNeuronas capa) {
        return  this.derivadaCostoRespectoActivacion*
                capa.getFuncionAct().derivada(capa.getSalidaAntesDeActivacion().get(j).getElemento())*
                capa.getCapaAnterior().getSalida().get(k).getElemento();
    }

    @Override
    public Double dC_db(int j, CapaNeuronas capa) {
        return  this.derivadaCostoRespectoActivacion*
                capa.getFuncionAct().derivada(capa.getSalidaAntesDeActivacion().get(j).getElemento());
    }
    
    private Double dC_da(int neurona, CapaNeuronas capa) throws IndiceFueraDeRango {
        if(capa instanceof CapaSalida)
            return 2*(capa.getSalida().get(neurona).getElemento() - this.salidaDeseada.get(neurona).getElemento());
        else {
            CapaNeuronas sig = capa.getCapaSiguiente();
            int neuronasSigCapa = sig.getNumeroDeNeuronasEnCapa();
            Double suma = 0.0;
            for(int indice_j = 0 ; indice_j < neuronasSigCapa ; indice_j++) {
                suma += dC_da(indice_j, sig)*
                        sig.getFuncionAct().derivada(sig.getSalidaAntesDeActivacion().get(indice_j).getElemento())*
                        sig.getElementoMatrizPesos(indice_j, neurona);
            }
            return suma;
        }
    }
    
    @Override
    public void actualizarParametros(Matriz<Double> matriz, Matriz<Double> matrizSuma, Vector<Double> vector, Vector<Double> vectorSuma, CapaNeuronas capa) {
        int indice_j, indice_k;
        
        indice_j = 0;
        for(Elemento<Double> primerElemMatriz = matriz.get(0,0), primerElemMatrizSuma = matrizSuma.get(0,0),
            indiceVector = vector.get(0), indiceVectorSuma = vectorSuma.get(0);
            primerElemMatriz != null;
            primerElemMatriz = primerElemMatriz.getAbajo(), primerElemMatrizSuma = primerElemMatrizSuma.getAbajo(),
            indiceVector = indiceVector.getAbajo(), indiceVectorSuma.getAbajo(), indice_j++) {
            
            this.derivadaCostoRespectoActivacion = this.dC_da(indice_j, capa);
            
            indiceVectorSuma.setElemento(indiceVectorSuma.getElemento() + this.dC_db(indice_j, capa));
            indiceVector.setElemento((1.0/this.iteracion) * indiceVectorSuma.getElemento());
            
            indice_k = 0;
            for(Elemento<Double> indiceMatriz = primerElemMatriz, indiceMatrizSuma = primerElemMatrizSuma;
                indiceMatriz != null;
                indiceMatriz = indiceMatriz.getDerecha(), indiceMatrizSuma = indiceMatrizSuma.getDerecha(), indice_k++) {
                
                indiceMatrizSuma.setElemento(indiceMatrizSuma.getElemento() + this.dC_dw(indice_j, indice_k, capa));
                indiceMatriz.setElemento((1.0/this.iteracion) * indiceMatrizSuma.getElemento());
            }
        }
    }
}
