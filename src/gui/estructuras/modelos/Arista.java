/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.estructuras.modelos;

/**
 *
 * @author Benjamin
 */
public class Arista {
    private Nodo origen;
    private Nodo destino;
    private Double costo;
    
    public Arista(Nodo origen, Nodo destino, Double costo) {
        this.origen = origen;
        this.destino = destino;
        this.costo = costo;
    }
    
    public Arista(Nodo origen, Nodo destino) {
        this.origen = origen;
        this.destino = destino;
        this.costo = null;
    }

    /**
     * @return the origen
     */
    public Nodo getOrigen() {
        return origen;
    }

    /**
     * @param origen the origen to set
     */
    public void setOrigen(Nodo origen) {
        this.origen = origen;
    }

    /**
     * @return the destino
     */
    public Nodo getDestino() {
        return destino;
    }

    /**
     * @param destino the destino to set
     */
    public void setDestino(Nodo destino) {
        this.destino = destino;
    }

    /**
     * @return the costo
     */
    public Double getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(Double costo) {
        this.costo = costo;
    }
}
