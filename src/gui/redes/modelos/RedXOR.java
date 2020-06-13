/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.redes.modelos;

import gui.interfaces.IFuncionActivacion;
import gui.matrices.modelos.DimensionesIncompatibles;
import gui.matrices.modelos.Vector;
import gui.neuronas.modelos.BiasesIncompatiblesConRed;
import gui.neuronas.modelos.CapaSinEntrada;
import gui.neuronas.modelos.PesosIncompatiblesConRed;
import gui.neuronas.modelos.RedNeuronal;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Benjamin
 */
public class RedXOR extends RedNeuronal {
    
    public RedXOR(int numeroEntradas, int[] numeroNeuronasOcultas, IFuncionActivacion[] fnActOcultas, IFuncionActivacion fnActSalida, Double[][][] pesos, Double[][] biases) throws PesosIncompatiblesConRed, BiasesIncompatiblesConRed {
        super(numeroEntradas, 1, numeroNeuronasOcultas, fnActOcultas, fnActSalida, pesos, biases);
    }
    
    public RedXOR(int numeroEntradas, int[] numeroNeuronasOcultas, IFuncionActivacion[] fnActOcultas, IFuncionActivacion fnActSalida, Double[][][] pesos) throws PesosIncompatiblesConRed {
        super(numeroEntradas, 1, numeroNeuronasOcultas, fnActOcultas, fnActSalida, pesos);
    }
    
    public RedXOR(int numeroEntradas, int[] numeroNeuronasOcultas, IFuncionActivacion[] fnActOcultas, IFuncionActivacion fnActSalida) {
        super(numeroEntradas, 1, numeroNeuronasOcultas, fnActOcultas, fnActSalida);
    }
    
    public void entrenamiento(Double[][] entradasPosibles, Double tolerancia, Double factorAprendizaje) throws DimensionesIncompatibles, CapaSinEntrada {
        
        while(true) {
            int x = numeroRandom(0, entradasPosibles.length - 1);
            Double[] entradaNeuronal = entradasPosibles[x];
            this.setEntrada(new Vector(entradaNeuronal));
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
            this.calculoActivaciones();
            System.out.println(this.calculoCosto(new Vector(salidaDeseada)));
            
            if(this.getCosto() < 0.001) break;
            
            this.aprendizaje(0.5);
            System.out.println("");
            this.printMatricesPesos();
        }
    }
        
    /**
     *
     * @param min
     * @param max
     * @return
     */
    public static Integer numeroRandom(Integer min, Integer max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
