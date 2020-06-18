/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.redes.modelos;

import gui.excepciones.DimensionesIncompatibles;
import gui.excepciones.NoEsMatriz;
import gui.interfaces.Arreglos;
import gui.interfaces.IFuncionActivacion;
import java.util.Random;

/**
 *
 * @author Benjamin
 */
public final class Perceptron {
    private int numeroEntradas;
    private double[] entrada;
    private double salida;
    private double[][] matrizPesos;
    private double bias;
    private IFuncionActivacion fnAct;
    private double[] intersecciones;
    private int iteraciones = 0;

    public Perceptron(int numeroEntradas, IFuncionActivacion fnActSalida) {
        this.entrada = new double[numeroEntradas];
        this.intersecciones = new double[numeroEntradas];
        this.fnAct = fnActSalida;
        this.numeroEntradas = numeroEntradas;
        this.matrizPesos = new double[1][numeroEntradas];
        this.inicializar();
        
        System.out.println("¡Perceptrón creado!");
        this.print();
        System.out.println("Estado inicial del perceptrón:");
        this.printPesosYBiases();
    }
    
    public Perceptron(int numeroEntradas, IFuncionActivacion fnActSalida, double[] pesos) {
        this.entrada = new double[numeroEntradas];
        this.intersecciones = new double[numeroEntradas];
        this.fnAct = fnActSalida;
        this.numeroEntradas = numeroEntradas;
        this.matrizPesos = conversionVectorPesos(pesos);
        this.inicializar(true);
        
        System.out.println("¡Perceptrón creado!");
        this.print();
        System.out.println("Estado inicial del perceptrón:");
        this.printPesosYBiases();
    }
    
    public Perceptron(int numeroEntradas, IFuncionActivacion fnActSalida, double[] pesos, double bias) {
        this.entrada = new double[numeroEntradas];
        this.intersecciones = new double[numeroEntradas];
        this.fnAct = fnActSalida;
        this.numeroEntradas = numeroEntradas;
        this.matrizPesos = conversionVectorPesos(pesos);
        this.bias = bias;
        
        System.out.println("¡Perceptrón creado!");
        this.print();
        System.out.println("Estado inicial del perceptrón:");
        this.printPesosYBiases();
    }
    
    private void inicializar() {
        Random random = new Random();
        
        this.bias = (-1.0) * random.nextDouble();
        
        for(int i = 0 ; i < this.numeroEntradas ; i++)
            this.matrizPesos[0][i] = random.nextDouble();
    }
    
    private void inicializar(boolean tienePesosIngresados) {
        Random random = new Random();
        
        if(tienePesosIngresados) this.bias = (-1.0) * random.nextDouble();
        else {
            for(int i = 0 ; i < this.numeroEntradas ; i++)
                this.matrizPesos[0][i] = random.nextDouble();
        }
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
    
    
    public void calculoSalida() throws DimensionesIncompatibles, NoEsMatriz {
        double[] sumaPonderada = Arreglos.producto(this.matrizPesos, this.entrada);
        double salidaAntesDeActivacion = sumaPonderada[0] + this.bias;
        this.salida = this.fnAct.calc(salidaAntesDeActivacion);
    }
    
    public void aprendizaje(double salidaDeseada) {
        double error = salidaDeseada - this.salida;
        
        for(int i = 0 ; i < this.matrizPesos[0].length ; i++) {
            this.matrizPesos[0][i] += error * this.entrada[i];
            this.intersecciones[i] = - (this.bias / this.matrizPesos[0][i]);
        }
        
        this.bias += error;
        this.iteraciones++;
        
        String vectorEntrada = "[";
        for(int i = 0 ; i < this.entrada.length ; i++) {
            vectorEntrada += String.format("%.0f", this.entrada[i]);
            if(i != this.entrada.length - 1)
                vectorEntrada += ", ";
            else
                vectorEntrada += "]";
        }
        
        System.out.println("\n\t\t--------------------O--------------------\n\nÉpoca: " + this.iteraciones + "\nEntrada: " + vectorEntrada + "\nSalida esperada: " + salidaDeseada + "\nSalida: " + this.salida + "\nCosto: " + error);
    }
    
    public void setEntrada(double[] entrada) {
        this.entrada = entrada;
    }
    
    public void print() {
        System.out.println("\n\tNúmero de entradas: " + this.numeroEntradas);
    }
    
    public void printPesosYBiases() {
        System.out.println("Pesos: ");
        Arreglos.print(this.matrizPesos);

        System.out.println("\nBias: ");
        System.out.println(this.bias);

        System.out.println("");
    }
    
    public double[] getEntrada() {
        return this.entrada;
    }
    
    public double[] getPesos() {
        return this.matrizPesos[0];
    }
    
    public double getBias() {
        return this.bias;
    }
}
