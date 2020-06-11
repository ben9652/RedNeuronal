/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.imagenes.modelos;

import gui.matrices.modelos.Elemento;
import gui.matrices.modelos.Matriz;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author Benjamin
 */
public class Imagen {
    private InputStream input;
    private ImageInputStream imageInput;
    private BufferedImage imageL;
    private Matriz<Integer[]> colores;
    
    private static final String SEPARADOR_COLUMNA = ",\t";
    
    private File ARCHIVO = new File("Colores de imagen.txt");
    
    public Imagen(String localizacion_imagen) {
        try {
            this.input = new FileInputStream(localizacion_imagen);
            this.imageInput = ImageIO.createImageInputStream(this.input);
            this.imageL = ImageIO.read(this.imageInput);
            this.colores = new Matriz<>();
        }
        catch(FileNotFoundException fnf) {
            System.out.println("No se encontró el archivo.");
        }
        catch(IOException ioe) {
            System.out.println("No se encontró el archivo.");
        }
        
        for(int x = 0 ; x < verAlto() ; x++) {
            List<Integer[]> pixeles = new ArrayList<>();
            for(int y = 0 ; y < verAncho() ; y++) {
                System.out.println(this.imageL.getRGB(x, y));
                Color c = new Color(this.imageL.getRGB(x, y));
                Integer[] pixel = new Integer[3];
                pixel[0] = c.getRed();
                pixel[1] = c.getGreen();
                pixel[2] = c.getBlue();
                pixeles.add(pixel);
            }
            this.colores.addRow(pixeles);
        }
        
        this.escribirColores();
    }
    
    public final int verAlto() {
        return this.imageL.getHeight();
    }
    
    public final int verAncho() {
        return this.imageL.getWidth();
    }
    
    public Matriz<Integer[]> getMatriz() {
        return this.colores;
    }
    
    public boolean esEscalaDeGrises() {
        boolean esEscalaDeGrises = true;
        for(int i = 0 ; i < this.verAlto() ; i++) {
            Elemento<Integer[]> pixel = this.colores.get(i, 0);
            for(int j = 0 ; j < this.verAncho() ; j++, pixel = pixel.getDerecha()) {
                Integer[] p = pixel.getElemento();
                if(!Objects.equals(p[0], p[1]) || !Objects.equals(p[1], p[2])) return false;
            }
        }
        
        return esEscalaDeGrises;
    }
    
    private void escribirColores() {
        boolean esEscalaDeGrises = this.esEscalaDeGrises();
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            Matriz<Integer[]> matriz = this.colores;
            if(!esEscalaDeGrises) {
                for(int j = 0 ; j < this.verAlto() ; j++) {
                    Elemento<Integer[]> pixel = matriz.get(0, j);
                    for(int i = 0 ; i < this.verAncho() ; i++, pixel = pixel.getAbajo()) {
                        bw.write("[" + pixel.getElemento()[0].toString() + "," + pixel.getElemento()[1].toString() + "," + pixel.getElemento()[2].toString() + "]" + (pixel.getAbajo() != null? SEPARADOR_COLUMNA : ""));
                        if(pixel.getAbajo() == null) {bw.newLine(); bw.newLine();}
                    }
                }
            }
            else {
                for(int j = 0 ; j < this.verAlto() ; j++) {
                    Elemento<Integer[]> pixel = matriz.get(0, j);
                    for(int i= 0 ; i < this.verAncho() ; i++, pixel = pixel.getAbajo()) {
                        String gris = pixel.getElemento()[0].toString();
                        String escritura = gris + (pixel.getAbajo() != null? SEPARADOR_COLUMNA : "");
                        bw.write(escritura);
                        if(pixel.getAbajo() == null) {bw.newLine(); bw.newLine();}
                    }
                }
            }
        }
        catch(IOException ioe) {
            
        }
    }
}
