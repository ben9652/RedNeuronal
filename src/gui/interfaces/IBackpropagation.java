/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.interfaces;

import gui.matrices.modelos.Matriz;
import gui.matrices.modelos.Vector;
import gui.neuronas.modelos.CapaNeuronas;

/**
 *
 * @author Benjamin
 */
public interface IBackpropagation {

    public Double actualizarCosto(Vector<Double> salida, Vector<Double> salidaDeseada, Double costoTotal);

    public Double dC_dw(int j, int k, CapaNeuronas capa);

    public Double dC_db(int j, CapaNeuronas capa);
    
    public int obtenerIteracion();
    
    public void actualizarParametros(Matriz<Double> matriz, Matriz<Double> matrizSuma, Vector<Double> vector, Vector<Double> vectorSuma, CapaNeuronas capa);
}
