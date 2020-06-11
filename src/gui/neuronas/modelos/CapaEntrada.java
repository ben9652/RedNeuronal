/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.neuronas.modelos;

import gui.matrices.modelos.Vector;

/**
 *
 * @author Benjamin
 */
public class CapaEntrada extends CapaNeuronas {

    public CapaEntrada(int numeroEntradas) {
        super(numeroEntradas);
    }
    
    @Override
    public void calculoSalida(boolean esEntrada) {
        super.calculoSalida(esEntrada);
    }
    
    @Override
    public void setEntrada(Vector<Double> entrada) {
        super.setEntrada(entrada);
    }
}
