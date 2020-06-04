/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.neuronas.modelos;

import java.util.List;

/**
 *
 * @author Benjamin
 */
public class CapaEntrada extends CapaNeuronas {

    public CapaEntrada(int numeroEntradas) {
        super(numeroEntradas);
    }
    
    @Override
    public void inicializar(boolean esEntrada) {
        super.inicializar(esEntrada);
    }
    
    @Override
    public void calculosInternos(boolean esEntrada) {
        super.calculosInternos(esEntrada);
    }
    
    @Override
    public void setEntrada(List<Double> entrada) {
        super.setEntrada(entrada);
//        
//        int indice = 0;
//        for(Neurona neurona : super.neuronas) {
//            neurona.setEntrada(super.entrada.get(indice));
//        }
    }
}
