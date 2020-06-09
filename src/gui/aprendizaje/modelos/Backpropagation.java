/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.aprendizaje.modelos;

import gui.matrices.modelos.Matriz;
import gui.neuronas.modelos.CapaNeuronas;

/**
 * 
 * @author Benjamin
 */
public class Backpropagation {
    private Matriz<Double> matrizPesosGradiente;
    private CapaNeuronas capa;
    //Los subíndices se representarán entre corchetes, y los superíndices, mediante paréntesis
    //
    private Double activacionOrigen;    //Sería dz[j](l)/dw[jk](l)
    private Double derivadaActivacion;  //Sería da[j](l)/dz[j](l)
    private Double 
}
