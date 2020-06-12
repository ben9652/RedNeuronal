/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.neuronas.modelos;

import gui.interfaces.IFuncionActivacion;
import gui.matrices.modelos.DimensionesIncompatibles;

/**
 *
 * @author Benjamin
 */
public class CapaOculta extends CapaNeuronas {
    
    public CapaOculta(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas) {
        super(numeroNeuronas, iaf, numeroEntradas);
    }
    
//    public CapaOculta(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas, Double[][] pesos) {
//        super(numeroNeuronas, iaf, numeroEntradas, pesos);
//    }
    
    /**
     *
     * @param esEntrada
     * @throws gui.neuronas.modelos.CapaSinEntrada
     * @throws gui.matrices.modelos.DimensionesIncompatibles
     */
    @Override
    public void calculoSalida(boolean esEntrada) throws CapaSinEntrada, DimensionesIncompatibles {
        super.calculoSalida(esEntrada);
    }
}
