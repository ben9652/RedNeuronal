/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.neuronas.modelos;

import gui.excepciones.DimensionesIncompatibles;
import gui.excepciones.NoEsMatriz;
import gui.excepciones.PesosIncompatiblesConRed;
import gui.interfaces.IFuncionActivacion;

/**
 *
 * @author Benjamin
 */
public class CapaSalida extends CapaNeuronas {
    
    public CapaSalida(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas) {
        super(numeroNeuronas, iaf, numeroEntradas);
    }
    
    public CapaSalida(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas, double[][] pesos, double[] biases) {
        super(numeroNeuronas, iaf, numeroEntradas, pesos, biases);
    }
    
    public CapaSalida(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas, double[][] pesos) throws PesosIncompatiblesConRed {
        super(numeroNeuronas, iaf, numeroEntradas, pesos);
    }
    
    public CapaSalida(int numeroNeuronas, IFuncionActivacion iaf, int numeroEntradas, double[] biases) {
        super(numeroNeuronas, iaf, numeroEntradas, biases);
    }
    
    @Override
    public void calculoSalida(boolean esEntrada) throws CapaSinEntrada, DimensionesIncompatibles, NoEsMatriz {
        super.calculoSalida(esEntrada);
    }
}
