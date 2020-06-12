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
import gui.neuronas.modelos.CapaSinEntrada;
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
    public static void main(String[] args) throws CapaSinEntrada, DimensionesIncompatibles {
        
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
                {0.29,0.12},
                {0.95,0.54},
                {0.33,0.69}
            },
            {
                {0.37,0.16,0.44}
            },
        };
        
        Double[][] entradasPosibles = {
            {0.0,0.0},
            {0.0,1.0},
            {1.0,0.0},
            {1.0,1.0}
        };
        
        RedNeuronal redN = new RedNeuronal(numeroDeEntradas, numeroDeSalidas, numeroDeNeuronasOcultas, fnActOcultas, fnActSalida, pesos);
        System.out.println("¡Red Neuronal creada!");
        redN.print();
        
        while(true) {
            if(redN.getCosto() < 0.01) break;
            int x = numeroRandom(0,3);
            Double[] entradaNeuronal = entradasPosibles[x];
            redN.setEntrada(new Vector(entradaNeuronal));
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
            redN.calculoActivaciones();
            System.out.println(redN.calculoCosto(new Vector(salidaDeseada)));
            redN.aprendizaje(0.5);
            System.out.println("");
            redN.printMatricesPesos();
        }
        
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
}
