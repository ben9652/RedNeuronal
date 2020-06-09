/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.neuronas.modelos;

import gui.interfaces.IFuncionActivacion;
import gui.interfaces.INeurona;
import gui.matrices.modelos.Elemento;
import gui.matrices.modelos.Matriz;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Benjamin
 */
public class Neurona implements INeurona {
    private List<Elemento<Double>> pesos;
    private List<Elemento<Double>> entrada;
    private Double salida;
    private Double salidaAntesDeActivacion;
    private int numeroEntradas = 0;
    private Double bias = -1.0;
    private IFuncionActivacion funcionActivacion;

    Neurona(int numeroDeEntradas, IFuncionActivacion iaf) {
        this.numeroEntradas = numeroDeEntradas;
        this.pesos = new ArrayList<>(numeroDeEntradas);
        this.entrada = new ArrayList<>(numeroDeEntradas);
        this.funcionActivacion = iaf;
    }

    @Override
    public void inicializar(Matriz<Double> matrizPesos) {
        Random random = new Random();
        
        List<Elemento<Double>> pesosHaciaNeurona = new ArrayList<>();
        for(int i = 0 ; i < this.numeroEntradas ; i++) {
            Double nuevoPeso = random.nextDouble();
            try {
                this.pesos.set(i, new Elemento(nuevoPeso));
            }
            catch(IndexOutOfBoundsException iobe) {
                this.pesos.add(new Elemento(nuevoPeso));
            }
            pesosHaciaNeurona.add(new Elemento(nuevoPeso));
        }
        matrizPesos.addRowElements(pesosHaciaNeurona);
    }
    
    /**
     * Este método realiza la suma ponderada de las entradas de la neurona
     * (que son las activaciones de la capa anterior) y aplica a esta suma
     * la función de activación seleccionada.
     * @param esEntrada
     */
    @Override
    public void activar(boolean esEntrada) {
        this.salidaAntesDeActivacion = 0.0;
        if(this.numeroEntradas > 0) {
            if(!esEntrada) {
                if(this.entrada != null && this.pesos != null) {
                    for(int i = 0 ; i < this.numeroEntradas ; i++)
                        this.salidaAntesDeActivacion += this.entrada.get(i).getElemento() * this.pesos.get(i).getElemento();
                    this.salidaAntesDeActivacion += this.bias;
                }
                this.salida = this.funcionActivacion.calc(this.salidaAntesDeActivacion);
            }
            else {
                if(this.entrada != null) {
                    for(int i = 0 ; i < this.numeroEntradas ; i++)
                        this.salidaAntesDeActivacion += this.entrada.get(i).getElemento();
                }
                this.salida = this.funcionActivacion.calc(this.salidaAntesDeActivacion);
            }
        }
    }

    /**
     * @return the entrada
     */
    public List<Elemento<Double>> getEntrada() {
        return entrada;
    }

    /**
     * @param entrada the entrada to set
     */
    public void setEntrada(List<Elemento<Double>> entrada) {
        this.entrada = entrada;
    }
    
    public void setEntrada(Elemento<Double> entrada) {
        try {
            this.entrada.set(0, entrada);
        }
        catch(IndexOutOfBoundsException ioobe) {
            this.entrada.add(entrada);
        }
    }

    /**
     * @return the salida
     */
    public Double getSalida() {
        return salida;
    }

    /**
     * @param salida the salida to set
     */
    public void setSalida(Double salida) {
        this.salida = salida;
    }

    /**
     * @return the salidaAntesDeActivacion
     */
    public Double getSalidaAntesDeActivacion() {
        return salidaAntesDeActivacion;
    }

    /**
     * @param salidaAntesDeActivacion the salidaAntesDeActivacion to set
     */
    public void setSalidaAntesDeActivacion(Double salidaAntesDeActivacion) {
        this.salidaAntesDeActivacion = salidaAntesDeActivacion;
    }

    /**
     * @return the numeroEntradas
     */
    public int getNumeroEntradas() {
        return numeroEntradas;
    }

    /**
     * @param numeroEntradas the numeroEntradas to set
     */
    public void setNumeroEntradas(int numeroEntradas) {
        this.numeroEntradas = numeroEntradas;
    }

    /**
     * @return the bias
     */
    public Double getBias() {
        return bias;
    }

    /**
     * @param bias the bias to set
     */
    public void setBias(Double bias) {
        this.bias = bias;
    }

    /**
     * @return the funcionActivacion
     */
    public IFuncionActivacion getFuncionActivacion() {
        return funcionActivacion;
    }

    /**
     * @param funcionActivacion the funcionActivacion to set
     */
    public void setFuncionActivacion(IFuncionActivacion funcionActivacion) {
        this.funcionActivacion = funcionActivacion;
    }
}
