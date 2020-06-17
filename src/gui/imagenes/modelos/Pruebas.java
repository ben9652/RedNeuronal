/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.imagenes.modelos;

import java.io.File;

/**
 *
 * @author Benjamin
 */
public class Pruebas {
    public static void main(String[] args) {
        for(int i = 0, renombre = 0 ; i < 42000 ; i++) {
            String nombre = "mnist/trainingSet/9/";
            File imagen = new File(nombre + "img_" + i + ".jpg");
            if(imagen.renameTo(new File(nombre + renombre + ".jpg"))) {
                renombre++;
                System.out.println("El archivo fue renombrado correctamente.");
            }
            else
                System.out.println("El renombrado no se ha podido realizar.");
        }
//        String cadena = "hola";
//        cadena = cadena.substring(0, cadena.length() - 1);
//        File imagen = new File("mnist/trainingSet/0/img_1.jpg");
//        boolean nombradoCorrecto = imagen.renameTo(new File("mnist/trainingSet/0/0.jpg"));
//        if(nombradoCorrecto) System.out.println("El archivo fue nombrado correctamente.");
//        else System.out.println("El renombrado no se ha podido realizar.");
//        Imagen digito = new Imagen("mnist/trainingSet/0/img_1.jpg");
    }
}
