/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.interfaces;

/**
 *
 * @author Benjamin
 */
public interface IFuncionActivacion {
    double calc(double x);
    
    double derivada(double x);
    
    public enum FuncionActivacion {
      STEP, LINEAR, SIGMOID, HYPERTAN
    }
}
