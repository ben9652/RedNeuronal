/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.redes.modelos;

import gui.interfaces.IFuncionActivacion;
import gui.excepciones.BiasesIncompatiblesConRed;
import gui.excepciones.DimensionesIncompatibles;
import gui.excepciones.NoEsMatriz;
import gui.neuronas.modelos.CapaSinEntrada;
import gui.excepciones.PesosIncompatiblesConRed;
import gui.neuronas.modelos.RedNeuronal;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Benjamin
 */
public class RedXOR extends RedNeuronal {
    
    public RedXOR(int numeroEntradas, int[] numeroNeuronasOcultas, IFuncionActivacion[] fnActOcultas, IFuncionActivacion fnActSalida, double[][][] pesos, double[][] biases) throws PesosIncompatiblesConRed, BiasesIncompatiblesConRed {
        super(numeroEntradas, 1, numeroNeuronasOcultas, fnActOcultas, fnActSalida, pesos, biases);
    }
    
    public RedXOR(int numeroEntradas, int[] numeroNeuronasOcultas, IFuncionActivacion[] fnActOcultas, IFuncionActivacion fnActSalida, double[][][] pesos) throws PesosIncompatiblesConRed {
        super(numeroEntradas, 1, numeroNeuronasOcultas, fnActOcultas, fnActSalida, pesos);
    }
    
    public RedXOR(int numeroEntradas, int[] numeroNeuronasOcultas, IFuncionActivacion[] fnActOcultas, IFuncionActivacion fnActSalida) {
        super(numeroEntradas, 1, numeroNeuronasOcultas, fnActOcultas, fnActSalida);
    }
    
    public void entrenamiento(double[][] entradasPosibles, double tolerancia, double factorAprendizaje) throws CapaSinEntrada, DimensionesIncompatibles, NoEsMatriz {
        
        while(true) {
            int x = numeroRandom(0, entradasPosibles.length - 1);
            double[] entradaNeuronal = entradasPosibles[x];
            this.setEntrada(entradaNeuronal);
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
            this.calculoActivaciones();
            System.out.println(this.calculoCosto(salidaDeseada));
            
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
    private static Integer numeroRandom(Integer min, Integer max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
