/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.matrices.modelos;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que imita la estructura de un vector columna.
 * @author Benjamin
 * @param <Tipo>
 */
public class Vector<Tipo> extends Matriz<Tipo> {
    
    public Vector() {
        super();
    }
    
    public Vector(List<Tipo> lista) {
        for(Tipo t : lista)
            this.addRow(t);
    }
    
    public Vector(Tipo[] lista) {
        for(Tipo t : lista)
            this.addRow(t);
    }
    
    public final String addRow(Tipo elemento) {
        Elemento<Tipo> actual = super.primero;
        if(super.filas < 1) {
            actual = super.addElementRight(new Elemento(elemento), actual);
            super.columnas++;
        }
        else
            actual = super.addElementDown(new Elemento(elemento), actual);
        super.filas++;
        return EXITO_AGREGADO_FILA;
    }
    
    public final String addRowElement(Elemento<Tipo> elemento) {
        Elemento<Tipo> actual = super.primero;
        if(super.filas < 1) {
            actual = super.addElementRight(elemento, actual);
            super.columnas++;
        }
        else
            actual = super.addElementDown(elemento, actual);
        super.filas++;
        return EXITO_AGREGADO_FILA;
    }
    
    public List<Elemento<Tipo>> toList() {
        List<Elemento<Tipo>> lista = new ArrayList<>();
        for(Elemento<Tipo> t = this.primero ; t != null ; t = t.getAbajo()) {
            lista.add(t);
        }
        
        return lista;
    }
    
    public List<Tipo> toListTipo() {
        List<Tipo> lista = new ArrayList<>();
        for(Elemento<Tipo> t = this.primero ; t != null ; t = t.getAbajo()) {
            lista.add(t.getElemento());
        }
        
        return lista;
    }
    
    public Elemento<Tipo> getElemento(int posicion) {
        return super.getElemento(posicion, 0);
    }
}
