/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.matrices.modelos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Benjamin
 */
public class Pruebas {
    public static void main(String[] args) {
        Number[][] a = {
            {9, 5, 1},
            {0, 7, 8},
            {4, 2, 6},
            {1, 3, 19},
            {9, 5, 1},
            {6, 1, 6},
            {13, 53, 67},
            {100, 23, 41},
            {51, 3, 56}
        };
        Number[][] b = {
            {1, 4, 6, 8, 13, 2, 51, 8, 910, 2, 3, 5, 0, 2},
            {7, 0, 1, 2, 6, 6, 25, 64, 71, 12, 53, 65, 2, 8},
            {1, 5, 6, 1, 6, 1, 3, 5, 78, 12, 43, 53, 33, 44}
        };
        Number[][] c = {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
        };
        Number[][] d = {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1},
        };
        Matriz<Number> matriz1 = new Matriz(a);
        Matriz<Number> matriz2 = new Matriz(b);
        long startTime = System.nanoTime();
        Matriz<Number> matrizProducto = Matriz.producto(matriz1, matriz2);
        long elapsedTime = System.nanoTime() - startTime;
        
        matrizProducto.print();
        System.out.println("\n\nTiempo transcurrido en el producto de matrices: " + elapsedTime + "\n\n");
        
        Matriz<Number> matriz3 = new Matriz(c);
        Matriz<Number> matriz4 = new Matriz(d);
        startTime = System.nanoTime();
        Matriz<Number> matrizSuma = Matriz.suma(matriz3, matriz4);
        elapsedTime = System.nanoTime() - startTime;
        
        matrizSuma.getElemento(1, 1).set
        
        matrizSuma.print();
        System.out.println("\n\nTiempo transcurrido en la suma de matrices: " + elapsedTime);
//        Integer a = 9;
//        Integer b = 5;
//        Integer c = 1;
//        Integer d = 0;
//        Integer e = 7;
//        Integer f = 8;
//        Integer g = 4;
//        Integer h = 2;
//        Integer i = 6;
//        
//        List<Integer> fila1 = new ArrayList<>();
//        fila1.add(a);
//        fila1.add(b);
//        fila1.add(c);
//        List<Integer> fila2 = new ArrayList<>();
//        fila2.add(d);
//        fila2.add(e);
//        fila2.add(f);
//        List<Integer> fila3 = new ArrayList<>();
//        fila3.add(g);
//        fila3.add(h);
//        fila3.add(i);
//        
//        matriz.addRow(fila1);
//        matriz.addRow(fila2);
//        matriz.addRow(fila3);
    }
}
