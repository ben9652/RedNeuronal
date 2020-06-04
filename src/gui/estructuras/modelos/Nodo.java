/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.estructuras.modelos;

import java.util.LinkedList;

/**
 *
 * @author Benjamin
 * @param <Tipo>
 */
public class Nodo<Tipo> {
    private Tipo objeto;
    private LinkedList<Arista> aristas = new LinkedList<>();

    /**
     * @return the objeto
     */
    public Tipo getObjeto() {
        return objeto;
    }

    /**
     * @param objeto the objeto to set
     */
    public void setObjeto(Tipo objeto) {
        this.objeto = objeto;
    }

    /**
     * @return the aristas
     */
    public LinkedList<Arista> getAristas() {
        return aristas;
    }

    public void addArista(Nodo destino, Double costo) {
        Arista nueva = new Arista(this, destino, costo);
        this.aristas.addFirst(nueva);
    }
    
    public void addArista(Nodo destino) {
        Arista nueva = new Arista(this, destino);
        this.aristas.addFirst(nueva);
    }
}
