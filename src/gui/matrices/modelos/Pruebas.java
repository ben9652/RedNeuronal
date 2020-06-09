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
//        Number[][] a = {
//            {9, 5, 1},
//            {0, 7, 8},
//            {4, 2, 6},
//            {1, 3, 19},
//            {9, 5, 1},
//            {6, 1, 6},
//            {13, 53, 67},
//            {100, 23, 41},
//            {51, 3, 56}
//        };
//        Number[][] b = {
//            {1, 4, 6, 8, 13, 2, 51, 8, 910, 2, 3, 5, 0, 2},
//            {7, 0, 1, 2, 6, 6, 25, 64, 71, 12, 53, 65, 2, 8},
//            {1, 5, 6, 1, 6, 1, 3, 5, 78, 12, 43, 53, 33, 44}
//        };
//        Number[][] c = {
//            {1, 1, 1},
//            {1, 1, 1},
//            {1, 1, 1}
//        };
//        Number[][] d = {
//            {1, 1, 1},
//            {1, 1, 1},
//            {1, 1, 1},
//        };
//        Matriz<Number> matriz1 = new Matriz(a);
//        Matriz<Number> matriz2 = new Matriz(b);
//        long startTime = System.nanoTime();
//        Matriz<Number> matrizProducto = Matriz.producto(matriz1, matriz2);
//        long elapsedTime = System.nanoTime() - startTime;
//        
//        matrizProducto.print();
//        System.out.println("\n\nTiempo transcurrido en el producto de matrices: " + elapsedTime + "\n\n");
//        
//        Matriz<Number> matriz3 = new Matriz(c);
//        Matriz<Number> matriz4 = new Matriz(d);
//        startTime = System.nanoTime();
//        Matriz<Number> matrizSuma = Matriz.suma(matriz3, matriz4);
//        elapsedTime = System.nanoTime() - startTime;
//        
//        matrizSuma.print();
//        System.out.println("\n\nTiempo transcurrido en la suma de matrices: " + elapsedTime);

        Elemento<Double> a = new Elemento(2);
        Elemento<Double> b = new Elemento(5);
        Elemento<Double> c = new Elemento(1);
        
        Vector<Double> vector = new Vector();
        vector.addRowElement(a);
        vector.addRowElement(b);
        vector.addRowElement(c);
        
        a.setElemento(Double.valueOf(6));
    }
}
