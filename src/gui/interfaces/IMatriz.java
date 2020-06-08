/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.interfaces;

/**
 *
 * @author Benjamin
 */
public interface IMatriz {
    String EXITO_AGREGADO_FILA = "Fila agregada con éxito";
    String ERROR_SIZE_FILA = "El tamaño de la fila que se quiere insertar debe ser igual al número de columnas de la matriz";
    
    String EXITO_AGREGADO_COLUMNA = "Columna agregada con éxito";
    String ERROR_SIZE_COLUMNA = "El tamaño de la columna que se quiere insertar debe ser igual al número de filas de la matriz";
    
    String EXITO_PRODUCTO = "Se realizó correctamente el producto de matrices.";
    String ERROR_PRODUCTO = "La primera matriz debe ser de dimensión m x p, y la segunda, p x n.";
    
    String EXITO_SUMA = "Se realizó correctamente la suma de matrices.";
    String ERROR_SUMA = "Las dos matrices deben ser de igual dimensión.";
}
