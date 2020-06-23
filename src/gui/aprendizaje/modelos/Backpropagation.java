/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.aprendizaje.modelos;

import gui.excepciones.IndiceFueraDeRango;
import gui.interfaces.IBackpropagation;
import gui.neuronas.modelos.CapaNeuronas;
import gui.neuronas.modelos.CapaSalida;

/**
 * 
 * @author Benjamin
 */
public class Backpropagation implements IBackpropagation {
    private int iteracion;
    private double[] salidaDeseada;
    
    //Estas tres variables de instancia serán usadas para no tener que recalcular tantas veces dC_da
    private Double derivadaCostoRespectoActivacion;
    
    public Backpropagation() {
        this.iteracion = 0;
    }
    
    @Override
    public final Double actualizarCosto(double[] salida, double[] salidaDeseada, double costoTotal) {
        Double costoIteracion = 0.0;
        for(int i = 0 ; i < salida.length ; i++)
            costoIteracion += Math.pow(salida[i] - salidaDeseada[i], 2);
        this.iteracion++;
        this.salidaDeseada = salidaDeseada;
        
        costoTotal += (1.0/salida.length) * costoIteracion;
        
        return costoTotal;
    }
    
    @Override
    public int obtenerIteracion() {
        return this.iteracion;
    }
    
    private Double dC_dw(int j, int k, CapaNeuronas capa) {
        return  this.derivadaCostoRespectoActivacion *
                capa.getFuncionAct().derivada(capa.getSalidaAntesDeActivacion()[j]) *
                capa.getCapaAnterior().getSalida()[k];
    }
    
    private Double dC_db(int j, CapaNeuronas capa) {
        return  this.derivadaCostoRespectoActivacion *
                capa.getFuncionAct().derivada(capa.getSalidaAntesDeActivacion()[j]);
    }
    
    private Double dC_da(int neurona, CapaNeuronas capa) throws IndiceFueraDeRango {
        if(capa instanceof CapaSalida)
            return (1.0/capa.getNumeroDeNeuronasEnCapa()) * 2 * (capa.getSalida()[neurona] - this.salidaDeseada[neurona]);
        else {
            CapaNeuronas sig = capa.getCapaSiguiente();
            int neuronasSigCapa = sig.getNumeroDeNeuronasEnCapa();
            Double suma = 0.0;
            for(int indice_j = 0 ; indice_j < neuronasSigCapa ; indice_j++) {
                suma += dC_da(indice_j, sig)*
                        sig.getFuncionAct().derivada(sig.getSalidaAntesDeActivacion()[indice_j])*
                        sig.getElementoMatrizPesos(indice_j, neurona);
            }
            return suma;
        }
    }
    
    @Override
    public double[][][] actualizarParametros(double[][] gradientePesos, double[][] gradientePesosSuma, double[] gradienteBiases, double[] gradienteBiasesSuma, CapaNeuronas capa) {
        //Aquí guardo los gradientes antes de actualizarlos. Esto servirá para el descenso del gradiente con momento.
        double[][] gradientePesosAnterior = new double[gradientePesos.length][gradientePesos[0].length];
        double[] gradienteBiasesAnterior = new double[gradientePesos.length];
        
        for(int i = 0 ; i < gradientePesos.length ; i++) {
            for(int j = 0 ; j < gradientePesos[0].length ; j++) {
                gradientePesosAnterior[i][j] = gradientePesos[i][j];
                gradienteBiasesAnterior[i] = gradienteBiases[i];
            }
        }
        
        double[][][] gradientes = {
            gradientePesosAnterior,
            {
                gradienteBiasesAnterior
            }
        };
        
        for(int j = 0 ; j < gradientePesos.length ; j++) {
            this.derivadaCostoRespectoActivacion = dC_da(j, capa);
            
            gradienteBiasesSuma[j] = gradienteBiasesSuma[j] + dC_db(j, capa);
            gradienteBiases[j] = (1.0 / this.iteracion) * gradienteBiasesSuma[j];
            
            for(int k = 0 ; k < gradientePesos[0].length ; k++) {
                gradientePesosSuma[j][k] = gradientePesosSuma[j][k] + dC_dw(j, k, capa);
                gradientePesos[j][k] = (1.0 / this.iteracion) * gradientePesosSuma[j][k];
            }
        }
        
        return gradientes;
    }
}
