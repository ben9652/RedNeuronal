/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.interfaces;

import gui.excepciones.DimensionesIncompatibles;
import gui.excepciones.NoEsMatriz;


/**
 *
 * @author Benjamin
 */
public interface Arreglos {
    String EXITO_AGREGADO_FILA = "Fila agregada con éxito";
    String ERROR_SIZE_FILA = "El tamaño de la fila que se quiere insertar debe ser igual al número de columnas de la matriz";
    
    String EXITO_AGREGADO_COLUMNA = "Columna agregada con éxito";
    String ERROR_SIZE_COLUMNA = "El tamaño de la columna que se quiere insertar debe ser igual al número de filas de la matriz";
    
    String EXITO_PRODUCTO = "Se realizó correctamente el producto de matrices.";
    String ERROR_PRODUCTO = "La primera matriz debe ser de dimensión m x p, y la segunda, p x n.";
    
    String EXITO_SUMA_MATRICES = "Se realizó correctamente la suma de matrices.";
    String ERROR_SUMA_MATRICES = "Las dos matrices deben ser de igual dimensión.";
    
    String ERROR_SUMA_VECTORES = "Los dos vectores deben ser de igual dimensión.";
    
    public static double[][] producto(double[][] mat1, double[][] mat2) throws DimensionesIncompatibles, NoEsMatriz {
        if(!esMatriz(mat1)) throw new NoEsMatriz("No es una matriz válida.");
        if(!esMatriz(mat2)) throw new NoEsMatriz("No es una matriz válida.");
        if(mat1[0].length != mat2.length) throw new DimensionesIncompatibles(ERROR_PRODUCTO);
        
        double[][] matrizProducto = new double[mat1.length][mat2[0].length];
        
        for(int i = 0 ; i < mat1.length ; i++) {
            for(int j = 0 ; j < mat2[0].length ; j++) {
                double numero = 0.0;
                for(int indiceMatColumna1 = 0, indiceMatFila2 = 0 ; indiceMatColumna1 < mat1[0].length ; indiceMatColumna1++, indiceMatFila2++)
                    numero += mat1[i][indiceMatColumna1] * mat2[indiceMatFila2][j];
                matrizProducto[i][j] = numero;
            }
        }
        
        return matrizProducto;
    }
    
    public static double[][] suma(double[][] mat1, double[][] mat2) throws NoEsMatriz, DimensionesIncompatibles {
        if(!esMatriz(mat1)) throw new NoEsMatriz("No es una matriz válida.");
        if(!esMatriz(mat2)) throw new NoEsMatriz("No es una matriz válida.");
        if(mat1.length != mat2.length || mat1[0].length != mat2[0].length) throw new DimensionesIncompatibles(ERROR_SUMA_MATRICES);
        
        double[][] matrizSuma = new double[mat1.length][mat1[0].length];
        
        for(int i = 0 ; i < mat1.length ; i++) {
            for(int j = 0 ; j < mat1[0].length ; j++)
                matrizSuma[i][j] = mat1[i][j] + mat2[i][j];
        }
        
        return matrizSuma;
    }
    
    public static double[][] resta(double[][] mat1, double[][] mat2) throws NoEsMatriz, DimensionesIncompatibles {
        if(!esMatriz(mat1)) throw new NoEsMatriz("No es una matriz válida.");
        if(!esMatriz(mat2)) throw new NoEsMatriz("No es una matriz válida.");
        if(mat1.length != mat2.length || mat1[0].length != mat2[0].length) throw new DimensionesIncompatibles(ERROR_SUMA_MATRICES);
        
        double[][] matrizSuma = new double[mat1.length][mat1[0].length];
        
        for(int i = 0 ; i < mat1.length ; i++) {
            for(int j = 0 ; j < mat1[0].length ; j++)
                matrizSuma[i][j] = mat1[i][j] - mat2[i][j];
        }
        
        return matrizSuma;
    }
    
    public static double[] suma(double[] v1, double[] v2) throws DimensionesIncompatibles {
        if(v1.length != v2.length) throw new DimensionesIncompatibles(ERROR_SUMA_VECTORES);
        
        double[] vectorSuma = new double[v1.length];
        
        for(int i = 0 ; i < v1.length ; i++)
            vectorSuma[i] = v1[i] + v2[i];
        
        return vectorSuma;
    }
    
    public static double[] resta(double[] v1, double[] v2) throws DimensionesIncompatibles {
        if(v1.length != v2.length) throw new DimensionesIncompatibles(ERROR_SUMA_VECTORES);
        
        double[] vectorResta = new double[v1.length];
        
        for(int i = 0 ; i < v1.length ; i++)
            vectorResta[i] = v1[i] - v2[i];
        
        return vectorResta;
    }
    
    public static double[] producto(double escalar, double[] v) throws DimensionesIncompatibles {
        
        double[] vectorSuma = new double[v.length];
        
        for(int i = 0 ; i < v.length ; i++)
            vectorSuma[i] = escalar * v[i];
        
        return vectorSuma;
    }
    
    public static double[][] producto(double escalar, double[][] matriz) throws NoEsMatriz {
        if(!esMatriz(matriz)) throw new NoEsMatriz("No es una matriz válida.");
        
        double[][] matrizProducto = new double[matriz.length][matriz[0].length];
        
        for(int i = 0 ; i < matriz.length ; i++) {
            for(int j = 0 ; j < matriz[0].length ; j++)
                matrizProducto[i][j] = escalar * matriz[i][j];
        }
        
        return matrizProducto;
    }
    
    public static double[] producto(double[][] matriz, double[] vector) throws DimensionesIncompatibles, NoEsMatriz {
        if(!esMatriz(matriz)) throw new NoEsMatriz("No es una matriz válida.");
        if(matriz[0].length != vector.length) throw new DimensionesIncompatibles(ERROR_PRODUCTO);
        
        double[] vectorProducto = new double[matriz.length];
        
        for(int i = 0 ; i < matriz.length ; i++) {
            double numero = 0.0;
            
            for(int indiceColumnaMatriz = 0 ; indiceColumnaMatriz < vector.length ; indiceColumnaMatriz++) 
                numero += matriz[i][indiceColumnaMatriz] * vector[indiceColumnaMatriz];
            vectorProducto[i] = numero;
        }
        
        return vectorProducto;
    }
    
    public static boolean esMatriz(double[][] mat) {
        int columnas = mat[0].length;
        
        for (double[] fila : mat) {
            if(fila.length != columnas) return false;
        }
        
        return true;
    }
    
    public static void print(double[][] matriz) {
        for (double[] fila : matriz) {
            for (int columna = 0; columna < fila.length; columna++) {
                System.out.print(fila[columna] + "\t\t");
            }
            System.out.println("\n");
        }
    }
    
    public static void print(double[] vector) {
        for(double d : vector)
            System.out.print(d + "\n\n");
    }
}
