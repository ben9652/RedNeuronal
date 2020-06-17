/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.excepciones;

/**
 *
 * @author Benjamin
 */
public class IndiceFueraDeRango extends IndexOutOfBoundsException {
    
    public IndiceFueraDeRango() {
        
    }
    
    public IndiceFueraDeRango(String msg) {
        super(msg);
    }
    
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
