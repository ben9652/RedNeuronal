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
public class Tanh implements IFuncionActivacion {
    private double a = 1.0;
    
    public Tanh(double a) {this.a = a;}

    @Override
    public double calc(double x) {
        return Math.tanh(this.a * x);
    }
}
