/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.matrices.modelos;

import gui.interfaces.IMatriz;
import static gui.interfaces.IMatriz.ERROR_SIZE_FILA;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Benjamin
 * @param <Tipo>
 */
public class Matriz<Tipo> implements IMatriz {
    private Elemento<Tipo> primero;
    private Elemento<Tipo> filaFinal;
    private Elemento<Tipo> columnaFinal;
    private Integer filas;
    private Integer columnas;
    
    public Matriz(int filas, int columnas){
        for(int i = 0 ; i < filas ; i++) 
            addRow(new ArrayList<>(columnas));
    }

    public Matriz() {
        this.filas = 0;
        this.columnas = 0;
    }
    
    public final String addRow(List<Tipo> fila){
        Elemento<Tipo> actual = this.primero;
        if(this.filas < 1){
            for(Tipo t : fila){
                actual = addElementRight(new Elemento(t), actual);
                this.columnas++;
            }
        }
        else{
            if(fila.size() != this.columnas) return ERROR_SIZE_FILA;
            for(Tipo t : fila){
                if(t.equals(fila.get(0)))
                    actual = addElementDown(new Elemento(t), actual);
                else
                    actual = addElementRight(new Elemento(t), actual);
            }
        }
        this.filas++;
        return EXITO_AGREGADO_FILA;
    }
    
    public String addColumn(List<Tipo> columna){
        Elemento<Tipo> actual = this.primero;
        if(this.columnas < 1){
            for(Tipo t : columna){
                addElementDown(new Elemento(t), actual);
                this.filas++;
            }
        }
        else{
            if(columna.size() != this.columnas) return ERROR_SIZE_COLUMNA;
            for(Tipo t : columna){
                if(t.equals(columna.get(0))){
                    addElementRight(new Elemento(t), actual);
                    actual = finalDerecha(actual);
                }
                else
                    addElementDown(new Elemento(t), actual);
            }
        }
        this.columnas++;
        return EXITO_AGREGADO_COLUMNA;
    }
    
    /**
     *
     * @param fila
     * @param columna
     * @return
     */
    public Elemento<Tipo> getElemento(int fila, int columna) {
        if((fila < 0 || fila >= this.filas-1) && (columna < 0 || columna >= this.columnas-1)) throw new IndiceFueraDeRango("El índice ingresado está fuera de la dimensión de la matriz");
        
        Elemento<Tipo> elementoBuscado;
        if(this.filas.intValue() == this.columnas.intValue() || this.filas > this.columnas) {
            elementoBuscado = seleccionFila(fila);
            for(int i = 0 ; i < columna ; i++, elementoBuscado = elementoBuscado.getDerecha());
        }
        else {
            elementoBuscado = seleccionColumna(columna);
            for(int i = 0 ; i < fila ; i++, elementoBuscado = elementoBuscado.getAbajo());
        }
        
        return elementoBuscado;
    }
    
    private Elemento<Tipo> seleccionFila(int fila) {
        Elemento<Tipo> elementoBuscado = this.primero;
        if(this.filas % 2 == 0) {
            if(this.filas / 2 <= fila)
                for(int i = 0 ; i < fila ; i++, elementoBuscado = elementoBuscado.getAbajo());
            else {
                elementoBuscado = this.filaFinal;
                for(int i = this.filas-1 ; i > fila ; i--, elementoBuscado = elementoBuscado.getArriba());
            }
        }
        else {
            if((this.filas + 1) / 2 <= fila + 1)
                for(int i = 0 ; i < fila ; i++, elementoBuscado = elementoBuscado.getAbajo());
            else {
                elementoBuscado = this.filaFinal;
                for(int i = this.filas-1 ; i > fila ; i--, elementoBuscado = elementoBuscado.getArriba());
            }
        }
        
        return elementoBuscado;
    }
    
    private Elemento<Tipo> seleccionColumna(int columna) {
        Elemento<Tipo> elementoBuscado = this.primero;
        if(this.columnas % 2 == 0) {
            if(this.columnas / 2 <= columna)
                for(int i = 0 ; i < columna ; i++, elementoBuscado = elementoBuscado.getDerecha());
            else {
                elementoBuscado = this.columnaFinal;
                for(int i = this.columnas-1 ; i > columna ; i--, elementoBuscado = elementoBuscado.getIzquierda());
            }
        }
        else {
            if((this.columnas + 1) / 2 <= columna + 1)
                for(int i = 0 ; i < columna ; i++, elementoBuscado = elementoBuscado.getDerecha());
            else {
                elementoBuscado = this.columnaFinal;
                for(int i = this.columnas-1 ; i > columna ; i--, elementoBuscado = elementoBuscado.getIzquierda());
            }
        }
        
        return elementoBuscado;
    }
    
    /**
     * Agrega un elemento al final de la derecha del elemento actual que se indique.
     * @param nuevo
     * @param actual 
     */
    private Elemento<Tipo> addElementRight(Elemento<Tipo> nuevo, Elemento<Tipo> actual){
        if(this.primero == null || actual == null){
            this.primero = nuevo;
            this.filaFinal = nuevo;
            this.columnaFinal = nuevo;
            actual = nuevo;
            Integer[] indice = new Integer[2];
            indice[0] = 0;
            indice[1] = 0;
            nuevo.setIndice(indice);
        }
        else{
            //Si el elemento actual está en la primera fila
            if(actual.getIndice(0) == 0){
                nuevo.setIzquierda(this.columnaFinal);
                this.columnaFinal.setDerecha(nuevo);
                Integer[] indice = new Integer[2];
                indice[0] = 0;
                indice[1] = this.columnaFinal.getIndice(1) + 1;
                nuevo.setIndice(indice);
                this.columnaFinal = nuevo;
                actual = nuevo;
            }
            else{
                nuevo.setIzquierda(actual);
                actual.setDerecha(nuevo);
                actual = actual.getArriba();
                actual = actual.getDerecha();
                nuevo.setArriba(actual);
                actual.setAbajo(nuevo);
                Integer[] indice = new Integer[2];
                indice[0] = actual.getIndice(0) + 1;
                indice[1] = actual.getIndice(1);
                nuevo.setIndice(indice);
                actual = nuevo;
            }
        }
        
        return actual;
    }
    
    private Elemento<Tipo> addElementDown(Elemento<Tipo> nuevo, Elemento<Tipo> actual){
        if(this.primero == null || actual == null){
            this.primero = nuevo;
            this.filaFinal = nuevo;
            this.columnaFinal = nuevo;
            Integer[] indice = new Integer[2];
            indice[0] = 0;
            indice[1] = 0;
            nuevo.setIndice(indice);
            actual = nuevo;
        }
        else{
            if(actual.getIndice(1) == 0){
                nuevo.setArriba(this.filaFinal);
                this.filaFinal.setAbajo(nuevo);
                Integer[] indice = new Integer[2];
                indice[0] = this.filaFinal.getIndice(0) + 1;
                indice[1] = 0;
                nuevo.setIndice(indice);
                this.filaFinal = nuevo;
                actual = nuevo;
            }
            else{
                actual = finalAbajo(actual);
                nuevo.setArriba(actual);
                actual.setAbajo(nuevo);
                actual = actual.getIzquierda();
                actual = actual.getAbajo();
                nuevo.setIzquierda(actual);
                actual.setDerecha(nuevo);
                @SuppressWarnings("MismatchedReadAndWriteOfArray")
                Integer[] indice = new Integer[2];
                indice[0] = actual.getIndice(0);
                indice[1] = actual.getIndice(1) + 1;
            }
        }
        
        return actual;
    }
    
    private Elemento<Tipo> finalDerecha(Elemento<Tipo> elemento){
        for( ; elemento.getDerecha() != null ; elemento = elemento.getDerecha());
        return elemento;
    }
    
    private Elemento<Tipo> finalAbajo(Elemento<Tipo> elemento){
        for( ; elemento.getAbajo() != null ; elemento = elemento.getAbajo());
        return elemento;
    }
}