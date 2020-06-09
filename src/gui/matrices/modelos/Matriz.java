/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.matrices.modelos;

import gui.interfaces.IMatriz;
import static gui.interfaces.IMatriz.ERROR_SIZE_FILA;
import java.math.BigDecimal;
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
    
    public Matriz(Tipo[][] matriz) {
        this.filas = 0;
        this.columnas = 0;
        for(Tipo[] i : matriz)
            System.out.println(addRow(i));
    }
    
    public Matriz(int filas, int columnas){
        this.filas = filas;
        this.columnas = columnas;
        for(int i = 0 ; i < filas ; i++) 
            System.out.println(addRow(new ArrayList<>(columnas)));
    }

    public Matriz() {
        this.filas = 0;
        this.columnas = 0;
    }
    
    public Integer getFilas() {
        return this.filas;
    }
    
    public Integer getColumnas() {
        return this.columnas;
    }
    
    /**
     * Se realiza el producto de la matriz que llama al método con el argumento
     * del mismo.
     * @param mat1
     * @param mat2
     * @return La matriz producto.
     */
    public static Matriz<Number> producto(Matriz<Number> mat1, Matriz<Number> mat2) {
        try {
            if(!mat1.columnas.equals(mat2.filas)) throw new DimensionesIncompatibles(ERROR_PRODUCTO);
        }
        catch(DimensionesIncompatibles di) {
            System.out.println(di.getMessage());
            return null;
        }
        
        List<Number> fila_a_agregar = new ArrayList<>();
        Matriz<Number> matrizProducto = new Matriz<>();
        Elemento<Number> primerElementoFila = mat1.primero;
        Elemento<Number> primerElementoColumna = mat2.primero;
        Elemento<Number> indiceMat1;
        Elemento<Number> indiceMat2;
        
        for(int indiceFila = 0 ; indiceFila < mat1.filas ; indiceFila++) {
            for(int indiceColumna = 0 ; indiceColumna < mat2.columnas ; indiceColumna++) {
                Number numero = 0;
                indiceMat1 = primerElementoFila;
                indiceMat2 = primerElementoColumna;
                while(indiceMat2 != null) {
                    //Hago el equivalente a numero += indiceMat1.getElemento() * indiceMat2.getElemento()
                    numero = new BigDecimal(numero.toString()).add(new BigDecimal(indiceMat1.getElemento().toString()).multiply(new BigDecimal(indiceMat2.getElemento().toString())));
                    indiceMat1 = indiceMat1.getDerecha();
                    indiceMat2 = indiceMat2.getAbajo();
                }
                fila_a_agregar.add(numero);
                primerElementoColumna = primerElementoColumna.getDerecha();
            }
            matrizProducto.addRow(fila_a_agregar);
            fila_a_agregar.clear();
            primerElementoFila = primerElementoFila.getAbajo();
            primerElementoColumna = mat2.primero;
        }
        
        System.out.println(EXITO_PRODUCTO);
        return matrizProducto;
    }
    
    public static Matriz<Number> suma(Matriz<Number> mat1, Matriz<Number> mat2) {
        try {
            if(!mat1.filas.equals(mat2.filas) || !mat1.columnas.equals(mat2.columnas)) throw new DimensionesIncompatibles(ERROR_SUMA);
        }
        catch(DimensionesIncompatibles di) {
            System.out.println(di.getMessage());
            return null;
        }
        
        Matriz<Number> matrizSuma = new Matriz<>();
        
        for(Elemento<Number> primerElementoFila1 = mat1.primero, primerElementoFila2 = mat2.primero;
            primerElementoFila1 != null || primerElementoFila2 != null;
            primerElementoFila1 = primerElementoFila1.getAbajo(), primerElementoFila2 = primerElementoFila2.getAbajo()) {
            
            List<Number> filaNumeros = new ArrayList<>();
            for(Elemento<Number> indiceMat1 = primerElementoFila1, indiceMat2 = primerElementoFila2;
                indiceMat1 != null || indiceMat2 != null;
                indiceMat1 = indiceMat1.getDerecha(), indiceMat2 = indiceMat2.getDerecha()) {
                
                Number numero = new BigDecimal(indiceMat1.getElemento().toString()).add(new BigDecimal(indiceMat2.getElemento().toString()));
                filaNumeros.add(numero);
            }
            
            matrizSuma.addRow(filaNumeros);
        }
        
        return matrizSuma;
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
            Tipo t;
            for(int i = 0 ; i < fila.size() ; i++) {
                t = fila.get(i);
                if(i == 0)
                    actual = addElementDown(new Elemento(t), actual);
                else
                    actual = addElementRight(new Elemento(t), actual);
            }
        }
        this.filas++;
        return EXITO_AGREGADO_FILA;
    }
    
    public final String addRow(Tipo[] fila) {
        Elemento<Tipo> actual = this.primero;
        if(this.filas < 1) {
            for(Tipo t : fila) {
                actual = addElementRight(new Elemento(t), actual);
                this.columnas++;
            }
        }
        else {
            if(fila.length != this.columnas) return ERROR_SIZE_FILA;
            Tipo t;
            for(int i = 0 ; i < fila.length ; i++) {
                t = fila[i];
                if(i == 0)
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
        try {
            if((fila < 0 || fila > this.filas-1) && (columna < 0 || columna > this.columnas-1)) throw new IndiceFueraDeRango("El índice ingresado está fuera de la dimensión de la matriz.");
        }
        catch(IndiceFueraDeRango ifdg) {
            System.out.println(ifdg.getMessage());
            return null;
        }
        
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
    
    public static Number[][] toArray(Matriz<Number> matriz) {
        Number[][] array = new Number[matriz.filas][matriz.columnas];
        for(int i = 0 ; i < matriz.filas ; i++) {
            for(int j = 0 ; j < matriz.columnas ; j++) {
                array[i][j] = matriz.getElemento(i, j).getElemento();
            }
        }
        return array;
    }
    
    public void print() {
        for(int i = 0 ; i < this.filas ; i++) {
            for(int j = 0 ; j < this.columnas ; j++) 
                System.out.print(this.getElemento(i, j).getElemento() + "\t\t");
            System.out.println("\n");
        }
    }
}