/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.principal.controladores;

import gui.funciones.modelos.Sigmoidea;
import gui.interfaces.IFuncionActivacion;
import gui.neuronas.modelos.RedNeuronal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Benjamin
 */
public class Pruebas {
    public static void main(String[] args) {
        Random r = new Random();
        r.setSeed(0);
        
        List<Double> n = new ArrayList<>();
        
        int numeroDeEntradas = 2;
        int numeroDeSalidas = 1;
        int[] numeroDeNeuronasOcultas = {3};
        IFuncionActivacion[] fnActOcultas = {new Sigmoidea(1.0)};
        Sigmoidea fnActSalida = new Sigmoidea(1.0);
        System.out.println("Creando Red Neuronal...");
        RedNeuronal redN = new RedNeuronal(numeroDeEntradas, numeroDeSalidas, numeroDeNeuronasOcultas, fnActOcultas, fnActSalida);
        System.out.println("¡Red Neuronal creada!");
        redN.print();
        
        Double[] entradaNeuronal = {1.5, 0.5};
        List<Double> salidaNeuronal = new ArrayList<>();
        
        System.out.println("Feeding the values [" + String.valueOf(entradaNeuronal[0]) + " ; " +
                            String.valueOf(entradaNeuronal[1]) + "] to the neural network");
        
        redN.setEntrada(toList(entradaNeuronal));
        redN.calculosInternos();
        salidaNeuronal = redN.getSalida();
        
        redN.salidas();
        
        entradaNeuronal[0] = 1.0;
        entradaNeuronal[1] = 2.1;
        
        System.out.println("Feeding the values [" + String.valueOf(entradaNeuronal[0]) + " ; " +
                            String.valueOf(entradaNeuronal[1]) + "] to the neural network");
        
        redN.setEntrada(toList(entradaNeuronal));
        redN.calculosInternos();
        salidaNeuronal = redN.getSalida();
        
        redN.salidas();
    }
    
    public static List<Double> toList(Double[] coleccion){
        List<Double> lista = new ArrayList<>();
        lista.addAll(Arrays.asList(coleccion));
        return lista;
    }
}
