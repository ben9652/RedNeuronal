/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.matrices.modelos;

import java.util.Arrays;
import java.util.Objects;

/**
 * Esta es una clase usada exclusivamente como item genérico de una matriz
 * @author Benjamin
 * @param <Tipo> 
 */
public class Elemento<Tipo> {
    private Tipo elemento;
    private Elemento<Tipo> abajo;
    private Elemento<Tipo> arriba;
    private Elemento<Tipo> izquierda;
    private Elemento<Tipo> derecha;
    private Integer[] indice = new Integer[2];

    public Elemento(Tipo elemento){
        this.elemento = elemento;
    }

    public Elemento(Elemento<Tipo> objeto){
        this.elemento = objeto.elemento;
        this.abajo = objeto.abajo;
        this.arriba = objeto.arriba;
        this.izquierda = objeto.izquierda;
        this.derecha = objeto.derecha;
        this.indice = objeto.indice;
    }

    /**
     * @return the elemento
     */
    public Tipo getElemento() {
        return elemento;
    }

    /**
     * @return the abajo
     */
    public Elemento<Tipo> getAbajo() {
        return abajo;
    }

    /**
     * @param abajo the abajo to set
     */
    public void setAbajo(Elemento<Tipo> abajo) {
        this.abajo = abajo;
    }

    /**
     * @return the arriba
     */
    public Elemento<Tipo> getArriba() {
        return arriba;
    }

    /**
     * @param arriba the arriba to set
     */
    public void setArriba(Elemento<Tipo> arriba) {
        this.arriba = arriba;
    }

    /**
     * @return the izquierda
     */
    public Elemento<Tipo> getIzquierda() {
        return izquierda;
    }

    /**
     * @param izquierda the izquierda to set
     */
    public void setIzquierda(Elemento<Tipo> izquierda) {
        this.izquierda = izquierda;
    }

    /**
     * @return the derecha
     */
    public Elemento<Tipo> getDerecha() {
        return derecha;
    }

    /**
     * @param derecha the derecha to set
     */
    public void setDerecha(Elemento<Tipo> derecha) {
        this.derecha = derecha;
    }

    /**
     * @param indiceMatriz
     * @return the indice
     */
    public Integer getIndice(int indiceMatriz) {
        return this.indice[indiceMatriz];
    }

    /**
     * @param indice the indice to set
     */
    public void setIndice(Integer[] indice) {
        this.indice = indice;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.elemento);
        hash = 11 * hash + Arrays.deepHashCode(this.indice);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Elemento<?> other = (Elemento<?>) obj;
        if (!Objects.equals(this.elemento, other.elemento)) {
            return false;
        }
        return Arrays.deepEquals(this.indice, other.indice);
    }


}