/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.redes.modelos;

import gui.neuronas.modelos.RedNeuronal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Benjamin
 */
public final class GestorRedes {
    private static GestorRedes gestor;
    
    private static List<RedNeuronal> redes = new ArrayList<>();
    
    public GestorRedes() {
        leerArchivo();
    }
    
    public static GestorRedes instanciar() {
        if(gestor == null)
            return new GestorRedes();
        else return gestor;
    }
    
    public void leerArchivo() {
        
    }
}
