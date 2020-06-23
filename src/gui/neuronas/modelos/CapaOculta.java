/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.neuronas.modelos;

import gui.interfaces.IFuncionActivacion;
import java.util.List;

/**
 *
 * @author Benjamin
 */
public class CapaOculta extends CapaNeuronas {
    
    public CapaOculta(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas) {
        super(numeroNeuronas, iaf, numeroEntradas);
    }
    
    public CapaOculta(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas, double[][] pesos, double[] biases) {
        super(numeroNeuronas, iaf, numeroEntradas, pesos, biases);
    }
    
    public CapaOculta(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas, double[][] pesos) {
        super(numeroNeuronas, iaf, numeroEntradas, pesos);
    }
    
    public CapaOculta(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas, double[] biases) {
        super(numeroNeuronas, iaf, numeroEntradas, biases);
    }
}
