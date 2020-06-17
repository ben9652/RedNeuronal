/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.funciones.modelos;

import gui.interfaces.IFuncionActivacion;

/**
 *
 * @author Benjamin
 */
public class Hardlims implements IFuncionActivacion {
    private double a = 1.0;
    
    public Hardlims(double x) {
        this.a = x;
    }
    
    @Override
    public double calc(double x) {
        return x >= 0 ? 1.0 : -1.0;
    }
    
    @Override
    public double derivada(double x) {
        return 0.0;
    }
}
