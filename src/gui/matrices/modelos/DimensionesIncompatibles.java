/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.matrices.modelos;

/**
 *
 * @author Benjamin
 */
public class DimensionesIncompatibles extends Exception {
    
    public DimensionesIncompatibles() {
        
    }
    
    public DimensionesIncompatibles(String msg) {
        super(msg);
    }
    
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
