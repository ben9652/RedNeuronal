/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.interfaces;

import gui.matrices.modelos.Matriz;
import gui.numeros.modelos.Peso;

/**
 *
 * @author Benjamin
 */
public interface INeurona {

    public void inicializar(Matriz<Peso> matrizPesos);

    public void activar(boolean esEntrada);
}
