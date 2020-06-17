/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.imagenes.modelos;

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
    private Integer[][][] colores;
    
    private static final String SEPARADOR_COLUMNA = ",\t";
    
    private File ARCHIVO = new File("Colores de imagen.txt");
    
    public Imagen(String localizacion_imagen) {
        try {
            this.input = new FileInputStream(localizacion_imagen);
            this.imageInput = ImageIO.createImageInputStream(this.input);
            this.imageL = ImageIO.read(this.imageInput);
            this.colores = new Integer[this.verAlto()][this.verAncho()][3];
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
                Color c = new Color(this.imageL.getRGB(x, y));
                Integer[] pixel = new Integer[3];
                pixel[0] = c.getRed();
                pixel[1] = c.getGreen();
                pixel[2] = c.getBlue();
                this.colores[x][y] = pixel;
            }
        }
        
        this.escribirColores();
    }
    
    public final int verAlto() {
        return this.imageL.getHeight();
    }
    
    public final int verAncho() {
        return this.imageL.getWidth();
    }
    
    public Integer[][][] getMatriz() {
        return this.colores;
    }
    
    public boolean esEscalaDeGrises() {
        for(int i = 0 ; i < this.verAlto() ; i++) {
            for(int j = 0 ; j < this.verAncho() ; j++) {
                Integer[] pixel = this.colores[i][j];
                if(!Objects.equals(pixel[0], pixel[1]) || !Objects.equals(pixel[1], pixel[2])) return false;
            }
        }
        
        return true;
    }
    
    private void escribirColores() {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            try {
                if(!this.esEscalaDeGrises()) {
                    for(int j = 0 ; j < this.verAlto() ; j++) {
                        for(int i = 0 ; i < this.verAncho() ; i++)
                            bw.write("[" + this.colores[i][j][0].toString() + "," + this.colores[i][j][1].toString() + "," + this.colores[i][j][2].toString() + "]" + (this.colores[i][j][i+1] != null? SEPARADOR_COLUMNA : ""));
                    }
                }
                else {
                    for(int j = 0 ; j < this.verAlto() ; j++) {
                        for(int i= 0 ; i < this.verAncho() ; i++) {
                            String gris = this.colores[i][j][0].toString();
                            bw.write(gris + (this.colores[i+1][j] != null? SEPARADOR_COLUMNA : ""));
                        }
                    }
                }
            }
            catch(ArrayIndexOutOfBoundsException aioobe) {
                bw.newLine(); bw.newLine();
            }
        }
        catch(IOException ioe) {
            
        }
    }
}
