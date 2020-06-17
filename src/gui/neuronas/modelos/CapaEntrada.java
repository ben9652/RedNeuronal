/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.neuronas.modelos;

import gui.excepciones.DimensionesIncompatibles;
import gui.excepciones.NoEsMatriz;

/**
 *
 * @author Benjamin
 */
public class CapaEntrada extends CapaNeuronas {

    public CapaEntrada(int numeroEntradas) {
        super(numeroEntradas);
    }
    
    @Override
    public void calculoSalida(boolean esEntrada) throws CapaSinEntrada, DimensionesIncompatibles, NoEsMatriz {
        super.calculoSalida(esEntrada);
    }
    
    @Override
    public void setEntrada(double[] entrada) {
        super.setEntrada(entrada);
    }
}
