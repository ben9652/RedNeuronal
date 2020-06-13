/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.principal.controladores;

import gui.funciones.modelos.Sigmoidea;
import gui.interfaces.IFuncionActivacion;
import gui.matrices.modelos.DimensionesIncompatibles;
import gui.matrices.modelos.Vector;
import gui.neuronas.modelos.BiasesIncompatiblesConRed;
import gui.neuronas.modelos.CapaSinEntrada;
import gui.neuronas.modelos.PesosIncompatiblesConRed;
import gui.neuronas.modelos.RedNeuronal;
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
    public static void main(String[] args) throws CapaSinEntrada, DimensionesIncompatibles, PesosIncompatiblesConRed, BiasesIncompatiblesConRed {
        Double a = 0.10224;
        Double b = 0.1;
        
        Boolean bool = areEqualDouble(a, b, 3);
        
        long startTime = System.currentTimeMillis();
        Random r = new Random();
        r.setSeed(0);
        
        List<Double> n = new ArrayList<>();
        
        int numeroDeEntradas = 2;
        int numeroDeSalidas = 1;
        int[] numeroDeNeuronasOcultas = {3};
        IFuncionActivacion[] fnActOcultas = {new Sigmoidea(1.0)};
        Sigmoidea fnActSalida = new Sigmoidea(1.0);
        System.out.println("Creando Red Neuronal...");

        Double[][][] pesos = {
            {
                {-157.66313191200098, 91.29279331349758},
                {-19.769050366789994, 43.93294782793237},
                {-140.99507649260585, 123.48340575834389}
            },
            {
                {74.4526860129564, -21.387733693878122, -131.53584105822563}
            },
        };
        
        Double[][] biases = {
            {33.49553799944952, 33.61173070777371, 34.21010065750269},
            
            {91.11082474328853}
        };
        
        RedNeuronal redN = new RedNeuronal(numeroDeEntradas, numeroDeSalidas, numeroDeNeuronasOcultas, fnActOcultas, fnActSalida, pesos, biases);
        
        aprenderXOR(redN);
        
        
        
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
    
    public static void aprenderXOR(RedNeuronal red) throws CapaSinEntrada, DimensionesIncompatibles {
        
        Double[][] entradasPosibles = {
            {0.0,0.0},
            {0.0,1.0},
            {1.0,0.0},
            {1.0,1.0}
        };
        
        while(true) {
            int x = numeroRandom(0,4);
            Double[] entradaNeuronal = entradasPosibles[x];
            red.setEntrada(new Vector(entradaNeuronal));
            Double[] salidaDeseada = new Double[1];
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
            red.calculoActivaciones();
            System.out.println(red.calculoCosto(new Vector(salidaDeseada)));
            
            if(red.getCosto() < 0.001) break;
            
            red.aprendizaje(0.1);
            System.out.println("");
            red.printMatricesPesos();
        }
    }
    
    public static void testearRedXOR(RedNeuronal red) {
        
        Double[][] entradasPosibles = {
            {0.0,0.0},
            {0.0,1.0},
            {1.0,0.0},
            {1.0,1.0}
        };
        
        
    }
}
