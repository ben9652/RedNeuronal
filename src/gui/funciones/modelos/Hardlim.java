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
public class Hardlim implements IFuncionActivacion {
    private double a = 1.0;
    
    public Hardlim(double a) {
        this.a = a;
    }

    @Override
    public double calc(double x) {
        if(a * x >= 0) return 1.0;
        else return 0.0;
    }

    @Override
    public double derivada(double x) {
        return 0.0;
    }
    
}
