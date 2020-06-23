/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.principal.controladores;

import gui.excepciones.BiasesIncompatiblesConRed;
import gui.excepciones.DimensionesIncompatibles;
import gui.excepciones.NoEsMatriz;
import gui.neuronas.modelos.CapaSinEntrada;
import gui.excepciones.PesosIncompatiblesConRed;
import gui.funciones.modelos.Sigmoidea;
import gui.interfaces.IFuncionActivacion;
import gui.neuronas.modelos.RedNeuronal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Benjamin
 */
public class Pruebas {
    public static void main(String[] args) throws CapaSinEntrada, PesosIncompatiblesConRed, BiasesIncompatiblesConRed, DimensionesIncompatibles, NoEsMatriz, NoEsMatriz, IOException {
//        double[] pesos = {0.5, 0.5};
//        double bias = 0.5;
//        IFuncionActivacion fnAct = new Sigmoidea(1.0);
//        Perceptron perceptron = new Perceptron(2, fnAct, pesos, bias);
//        
//        double[][] entradasPosibles = {
//            {1.0, 1.0},
//            {1.0, 0.0},
//            {0.0, 1.0}
//        };
//        
//        perceptron.setEntrada(entradasPosibles[0]);
//        
//        perceptron.calculoSalida();
//        perceptron.aprendizaje(1.0);
//        perceptron.printPesosYBiases();
//        
//        perceptron.setEntrada(entradasPosibles[1]);
//        
//        perceptron.calculoSalida();
//        perceptron.aprendizaje(1.0);
//        perceptron.printPesosYBiases();
//        
//        perceptron.setEntrada(entradasPosibles[2]);
//        
//        perceptron.calculoSalida();
//        perceptron.aprendizaje(0.0);
//        perceptron.printPesosYBiases();
        long startTime = System.currentTimeMillis();
        Random r = new Random();
        r.setSeed(0);
        
        int numeroDeEntradas = 2;
        int numeroDeSalidas = 1;
        int[] numeroDeNeuronasOcultas = {2};
        IFuncionActivacion[] fnActOcultas = {new Sigmoidea(1.0)};
        Sigmoidea fnActSalida = new Sigmoidea(1.0);
        System.out.println("Creando Red Neuronal...");
        
//Parámetros iniciales
//Pesos capa 1:
//0.8277383946257432		0.9073268411711306		
//
//0.49564764403967176		0.3083391088006223		
//
//
//Biases capa 1:
//-0.23788506515643681
//
//-0.92749191451472
//
//
//Pesos capa 2:
//0.5749745714348665		0.6487540169012812		
//
//
//Biases capa 2:
//-0.7023377689295509

//Parámetros después de entrenamiento
//Pesos capa 1:
//14.128577346310468		13.967831701173981		
//
//5.30678956821172		5.2389912066089925		
//
//
//Biases capa 1:
//-1.1367892829997928
//
//-10.06889335160328
//
//
//Pesos capa 2:
//12.518590065775136		-16.52955624152671		
//
//
//Biases capa 2:
//-6.64906841114244


//        double[][][] pesos = {
//            {
//                {20.0, 20.0},
//                {-20.0, -20.0}
//            },
//            {
//                {20, 20}
//            }
//        };
//        
//        double[][] biases = {
//            {-10, 30},
//            {-30}
//        };

        double[][][] pesos = {
            {
                {19.60905031229541, 20.682557306402018},
                {15.600930109027608, 15.491248438823199}
            },
            {
                {19.567578183111493, -32.8559338041145}
            }
        };
        
        double[][] biases = {
            {-3.3138589960405045,
             -23.029654308253185},
            
            {-7.354682072023129}
        };
        
        RedNeuronal redN = new RedNeuronal(numeroDeEntradas, numeroDeSalidas, numeroDeNeuronasOcultas, fnActOcultas, fnActSalida, true);
        
        aprenderXOR(redN, 0.001);
        
//        redN.determinarTolerancia(0.001);
//        testearRedXOR(redN);
        
        System.out.println("Tiempo transcurrido en el programa: " + (System.currentTimeMillis() - startTime) + " milisegundos");
    }
    
    public static List<Double> toList(Double[] coleccion){
        List<Double> lista = new ArrayList<>();
        lista.addAll(Arrays.asList(coleccion));
        return lista;
    }
    
    public static Integer numeroRandom(Integer min, Integer max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
    
    /**
     * @param a
     * @param b
      *@param precision number of decimal digits
     * @return 
      */
    public static boolean areEqualDouble(double a, double b, int precision) {
        Double valorAbsoluto = Math.abs(a - b);
        Double potencia = Math.pow(10, -precision);
        return valorAbsoluto <= potencia;
    }
    
    public static void aprenderXOR(RedNeuronal red, double tolerancia) throws CapaSinEntrada, DimensionesIncompatibles, NoEsMatriz {
        
        double[][] entradasPosibles = {
            {0.0,0.0},
            {0.0,1.0},
            {1.0,0.0},
            {1.0,1.0}
        };
        
        while(true) {
            int x = numeroRandom(0,4);
            double[] entradaNeuronal = entradasPosibles[x];
            double[] salidaDeseada = new double[1];
            switch(x) {
                case 0:
                    salidaDeseada[0] = 0.0;
                    break;
                case 1:
                    salidaDeseada[0] = 1.0;
                    break;
                case 2:
                    salidaDeseada[0] = 1.0;
                    break;
                case 3:
                    salidaDeseada[0] = 0.0;
                    break;
            }
            
            if(red.getCosto() < tolerancia) break;
            
            System.out.println(red.aprendizaje(entradaNeuronal, salidaDeseada, 0.5, 0.1));
            
            System.out.println("");
            red.printMatricesPesos();
        }
    }
    
    public static void testearRedXOR(RedNeuronal red) throws DimensionesIncompatibles, CapaSinEntrada, NoEsMatriz {
        
        double[][] entradasPosibles = {
            {0.0,0.0},
            {0.0,1.0},
            {1.0,0.0},
            {1.0,1.0}
        };
        
        while(true) {
            int x = numeroRandom(0,4);
            double[] entradaNeuronal = entradasPosibles[x];
            double[] salidaDeseada = new double[1];
            switch(x) {
                case 0:
                    salidaDeseada[0] = 0.0;
                    break;
                case 1:
                    salidaDeseada[0] = 1.0;
                    break;
                case 2:
                    salidaDeseada[0] = 1.0;
                    break;
                case 3:
                    salidaDeseada[0] = 0.0;
                    break;
            }
            
            System.out.println(red.test(entradaNeuronal, salidaDeseada));
        }
    }
}
