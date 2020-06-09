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
//        
//        Matriz<Double> matriz1 = new Matriz(numberToDouble(a));
//        Matriz<Double> matriz2 = new Matriz(numberToDouble(b));
//        long startTime = System.nanoTime();
//        Matriz<Double> matrizProducto = Matriz.producto(matriz1, matriz2);
//        long elapsedTime = System.nanoTime() - startTime;
//        
//        matrizProducto.print();
//        System.out.println("\n\nTiempo transcurrido en el producto de matrices: " + elapsedTime + "\n\n");
//        
//        Matriz<Double> matriz3 = new Matriz(numberToDouble(c));
//        Matriz<Double> matriz4 = new Matriz(numberToDouble(d));
//        startTime = System.nanoTime();
//        Matriz<Double> matrizSuma = Matriz.suma(matriz3, matriz4);
//        elapsedTime = System.nanoTime() - startTime;
//        
//        matrizSuma.print();
//        System.out.println("\n\nTiempo transcurrido en la suma de matrices: " + elapsedTime);
//
        Double[] e = {2.0, 5.0, 1.0};
        Double[] f = {5.0, 6.0, 2.0};
        
        Vector<Double> vector1 = new Vector(e);
        Vector<Double> vector2 = new Vector(f);
        
        Vector.suma(vector1, vector2).print();
    }
    
    public static Double[][] numberToDouble(Number[][] n) {
        Double[][] numeros = new Double[n.length][n[0].length];
        
        for(int i = 0 ; i < n.length ; i++) {
            for(int j = 0 ; j < n[0].length ; j++) {
                numeros[i][j] = n[i][j].doubleValue();
            }
        }
        return numeros;
    }
}
