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
public class CapaSalida extends CapaNeuronas {
    
    public CapaSalida(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas) {
        super(numeroNeuronas, iaf, numeroEntradas);
    }
    
//    public CapaSalida(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas, Double[][] pesos) {
//        super(numeroNeuronas, iaf, numeroEntradas, pesos);
//    }
    
    @Override
    public void calculoSalida(boolean esEntrada) throws CapaSinEntrada, DimensionesIncompatibles {
        super.calculoSalida(esEntrada);
    }
}
