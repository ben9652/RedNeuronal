/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.redes.modelos;

import gui.excepciones.BiasesIncompatiblesConRed;
import gui.excepciones.DimensionesIncompatibles;
import gui.excepciones.NoEsMatriz;
import gui.excepciones.PesosIncompatiblesConRed;
import gui.interfaces.Arreglos;
import gui.interfaces.IFuncionActivacion;
import gui.neuronas.modelos.CapaSalida;
import gui.neuronas.modelos.CapaSinEntrada;

/**
 *
 * @author Benjamin
 */
public final class Perceptron extends CapaSalida {
    private double[] intersecciones;
    private int iteraciones = 0;

    public Perceptron(int numeroEntradas, IFuncionActivacion fnActSalida) {
        super(1, fnActSalida, numeroEntradas);
        this.intersecciones = new double[numeroEntradas];
        
        System.out.println("¡Perceptrón creado!");
        this.print();
        System.out.println("Estado inicial del perceptrón:");
        this.printPesosYBiases();
    }
    
    public Perceptron(int numeroEntradas, IFuncionActivacion fnActSalida, double[] pesos) throws PesosIncompatiblesConRed {
        super(1, fnActSalida, numeroEntradas, conversionVectorPesos(pesos));
        this.intersecciones = new double[numeroEntradas];
        
        System.out.println("¡Perceptrón creado!");
        this.print();
        System.out.println("Estado inicial del perceptrón:");
        this.printPesosYBiases();
    }
    
    public Perceptron(int numeroEntradas, IFuncionActivacion fnActSalida, double[] pesos, double bias) throws PesosIncompatiblesConRed, BiasesIncompatiblesConRed {
        super(1, fnActSalida, numeroEntradas, conversionVectorPesos(pesos), conversionBias(bias));
        this.intersecciones = new double[numeroEntradas];
        
        System.out.println("¡Perceptrón creado!");
        this.print();
        System.out.println("Estado inicial del perceptrón:");
        this.printPesosYBiases();
    }
    
    private static double[][] conversionVectorPesos(double[] pesos) {
        double[][] matriz = new double[1][pesos.length];
        
        for(int i = 0 ; i < pesos.length ; i++)
            matriz[0][i] = pesos[i];
        
        return matriz;
    }
    
    private static double[] conversionBias(double bias) {
        double[] vector = new double[1];
        
        vector[0] = bias;
        
        return vector;
    }
    
    
    public void calculoSalida() throws CapaSinEntrada, DimensionesIncompatibles, NoEsMatriz {
        super.calculoSalida(false);
    }
    
    public void aprendizaje(double salidaDeseada) {
        double error = salidaDeseada - super.salida[0];
        
        for(int i = 0 ; i < super.matrizPesos[0].length ; i++) {
            super.matrizPesos[0][i] += error * super.entrada[i];
            this.intersecciones[i] = - (super.biases[0] / super.matrizPesos[0][i]);
        }
        
        super.biases[0] += error;
        this.iteraciones++;
        
        String vectorEntrada = "[";
        for(int i = 0 ; i < this.entrada.length ; i++) {
            vectorEntrada += String.format("%.0f", this.entrada[i]);
            if(i != this.entrada.length - 1)
                vectorEntrada += ", ";
            else
                vectorEntrada += "]";
        }
        
        System.out.println("\n\t\t--------------------O--------------------\n\nÉpoca: " + this.iteraciones + "\nEntrada: " + vectorEntrada + "\nSalida esperada: " + salidaDeseada + "\nSalida: " + super.salida[0] + "\nCosto: " + error);
    }
    
    @Override
    public void setEntrada(double[] entrada) {
        super.setEntrada(entrada);
    }
    
    public void print() {
        System.out.println("\n\tNúmero de entradas: " + super.numeroEntradas);
    }
    
    public void printPesosYBiases() {
        System.out.println("Pesos última capa" + ":");
        Arreglos.print(super.matrizPesos);

        System.out.println("\nBiases última capa" + ":");
        System.out.println(super.biases[0]);

        System.out.println("");
    }
    
    @Override
    public double[] getEntrada() {
        return super.entrada;
    }
    
    public double[] getPesos() {
        return super.matrizPesos[0];
    }
    
    public double getBias() {
        return super.biases[0];
    }
}
