/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.imagenes.modelos;

import gui.matrices.modelos.Matriz;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Benjamin
 */
public class Pruebas {
    public static void main(String[] args) {
        Matriz<Integer> matriz = new Matriz<>();
        
        List<Integer> numeros = new ArrayList<>();
        numeros.add(2);
        numeros.add(4);
        numeros.add(5);
        
        matriz.addRow(numeros);
        numeros.clear();
        
        numeros.add(8);
        numeros.add(10);
        numeros.add(11);
        
        matriz.addRow(numeros);
        
        Imagen digito = new Imagen("mnist/trainingSet/0/img_1.jpg");
    }
}
