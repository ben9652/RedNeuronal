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
    
    public static Vector<Double> crearVectorDouble(int elementos) {
        Vector<Double> vector = new Vector();
        
        for(int i = 0 ; i < elementos ; i++) vector.addRow(0.0);
        
        return vector;
    }
    
    public static Vector<Double> suma(Vector<Double> v1, Vector<Double> v2) throws DimensionesIncompatibles{
        if(!v1.filas.equals(v2.filas)) throw new DimensionesIncompatibles(ERROR_SUMA);
        
        Vector<Double> vectorSuma = new Vector();
        
        Elemento<Double> indiceV1 = v1.primero;
        Elemento<Double> indiceV2 = v2.primero;
        
        for(int i = 0 ; i < v1.size() ; i++, indiceV1 = indiceV1.getAbajo(), indiceV2 = indiceV2.getAbajo())
            vectorSuma.addRow(indiceV1.getElemento() + indiceV2.getElemento());
        
        return vectorSuma;
    }
    
    public static Vector<Double> resta(Vector<Double> v1, Vector<Double> v2) throws DimensionesIncompatibles{
        if(!v1.filas.equals(v2.filas)) throw new DimensionesIncompatibles(ERROR_SUMA);
        
        Vector<Double> vectorSuma = new Vector();
        
        Elemento<Double> indiceV1 = v1.primero;
        Elemento<Double> indiceV2 = v2.primero;
        
        for(int i = 0 ; i < v1.size() ; i++, indiceV1 = indiceV1.getAbajo(), indiceV2 = indiceV2.getAbajo())
            vectorSuma.addRow(indiceV1.getElemento() - indiceV2.getElemento());
        
        return vectorSuma;
    }
    
    public static Vector<Double> producto(Double escalar, Vector<Double> vector) {
        Vector<Double> vectorProducto = new Vector();
        
        for(Elemento<Double> indiceVec = vector.primero ; indiceVec != null ; indiceVec = indiceVec.getAbajo())
            vectorProducto.addRow(escalar * indiceVec.getElemento());
        
        return vectorProducto;
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
    
    public Elemento<Tipo> get(int posicion) throws IndiceFueraDeRango {
        if(posicion < 0 || posicion > super.filas - 1) throw new IndiceFueraDeRango("El índice ingresado está fuera de la dimensión del vector.");
        
        return super.seleccionFila(posicion);
    }
    
    public int size() {
        return super.getFilas();
    }
}
