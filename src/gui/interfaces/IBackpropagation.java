/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.interfaces;

import gui.neuronas.modelos.CapaNeuronas;

/**
 *
 * @author Benjamin
 */
public interface IBackpropagation {

    public Double actualizarCosto(double[] salida, double[] salidaDeseada, double costoTotal);
    
    public int obtenerIteracion();
    
    public double[][][] actualizarParametros(double[][] matriz, double[][] matrizSuma, double[] vector, double[] vectorSuma, CapaNeuronas capa);
}
