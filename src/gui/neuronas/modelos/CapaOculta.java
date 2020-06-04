/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.neuronas.modelos;

import gui.interfaces.IFuncionActivacion;

/**
 *
 * @author Benjamin
 */
public class CapaOculta extends CapaNeuronas {
    
    public CapaOculta(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas) {
        super(numeroNeuronas, iaf, numeroEntradas);
    }
    
    @Override
    public void inicializar(boolean esEntrada) {
        super.inicializar(esEntrada);
    }
    
    @Override
    public void calculosInternos(boolean esEntrada) {
        super.calculosInternos(esEntrada);
    }
}
