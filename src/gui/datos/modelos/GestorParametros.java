/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.datos.modelos;

import gui.neuronas.modelos.CapaEntrada;
import gui.neuronas.modelos.CapaNeuronas;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Benjamin
 */
public final class GestorParametros {
    private CapaNeuronas capaEntrada;
    
    private File parametros;
    
    private String nombreArchivo;
    
    public GestorParametros(String nombreRed, CapaEntrada capaEntrada) {
        this.nombreArchivo = nombreRed;
        this.parametros = new File(nombreRed + " - Pesos y biases.txt");
        this.parametros.setReadOnly();
        this.capaEntrada = capaEntrada;
    }
    
    public void leerArchivo(List<double[][]> matricesPesos, List<double[]> vectoresBiases) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(parametros));
        String cadena;
        boolean sonPesos = false;
        boolean sonBiases = false;
        int indiceFila = 0;
        while((cadena = br.readLine()) != null) {
            if(cadena.isEmpty()) {
                sonPesos = false;
                sonBiases = false;
                continue;
            }
            if(cadena.contains("Pesos")) {
                sonBiases = false;
                sonPesos = true;
                continue;
            }
            if(cadena.contains("Biases")) {
                sonPesos = false;
                sonBiases = true;
                continue;
            }

            if(sonPesos) {
                List<double[]> filas = new ArrayList<>();
                while(!cadena.isEmpty()) {
                    String[] pesos = cadena.split(",");
                    double[] fila = new double[pesos.length];
                    for(int i = 0 ; i < pesos.length ; i++)
                        fila[i] = Double.parseDouble(pesos[i]);
                    filas.add(fila);
                    cadena = br.readLine();
                }
                double[][] matrizPesos = new double[filas.size()][filas.get(0).length];
                for(int i = 0 ; i < filas.size() ; i++)
                    matrizPesos[i] = filas.get(i);
                matricesPesos.add(matrizPesos);
            }

            if(sonBiases) {
                String[] biases = cadena.split(",");
                double[] vector = new double[biases.length];
                for(int i = 0 ; i < biases.length ; i++)
                    vector[i] = Double.parseDouble(biases[i]);
                vectoresBiases.add(vector);
            }
        }
    }
    
    public void escribirArchivo() {
        CapaNeuronas capa = this.capaEntrada.getCapaSiguiente();
        this.parametros.setWritable(true);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(parametros))){
            for( ; capa != null ; capa = capa.getCapaSiguiente()){
                bw.write("Pesos capa " + capa.getNumeroCapa() + ":");
                bw.newLine();
                //Escritura de la matriz de pesos
                for(int j = 0 ; j < capa.getFilasMatrizPesos() ; j++) {
                    for(int k = 0 ; k < capa.getColumnasMatrizPesos() ; k++) {
                        if(k != capa.getColumnasMatrizPesos() - 1)
                            bw.write(capa.getElementoMatrizPesos(j, k) + ",");
                        else
                            bw.write(capa.getElementoMatrizPesos(j, k) + "");
                    }
                    bw.newLine();
                    bw.flush();
                }
                bw.newLine();
                //Escritura del vector de biases
                bw.write("Biases capa " + capa.getNumeroCapa() + ":");
                bw.newLine();
                for(int j = 0 ; j < capa.getFilasMatrizPesos() ; j++) {
                    if(j != capa.getFilasMatrizPesos() - 1)
                        bw.write(capa.getElementoVectorBiases(j) + ",");
                    else
                        bw.write(capa.getElementoVectorBiases(j) + "");
                }
                bw.newLine();
                bw.newLine();
            }
            bw.close();
            this.parametros.setReadOnly();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void escribirArchivo(String nombreArchivo) {
        File archivoAuxiliar = new File(nombreArchivo);
        CapaNeuronas capa = this.capaEntrada.getCapaSiguiente();
        archivoAuxiliar.setWritable(true);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(archivoAuxiliar))){
            for( ; capa != null ; capa = capa.getCapaSiguiente()){
                bw.write("Pesos capa " + capa.getNumeroCapa() + ":");
                bw.newLine();
                //Escritura de la matriz de pesos
                for(int j = 0 ; j < capa.getFilasMatrizPesos() ; j++) {
                    for(int k = 0 ; k < capa.getColumnasMatrizPesos() ; k++) {
                        if(k != capa.getColumnasMatrizPesos() - 1)
                            bw.write(capa.getElementoMatrizPesos(j, k) + ",");
                        else
                            bw.write(capa.getElementoMatrizPesos(j, k) + "");
                    }
                    bw.newLine();
                    bw.flush();
                }
                bw.newLine();
                //Escritura del vector de biases
                bw.write("Biases capa " + capa.getNumeroCapa() + ":");
                bw.newLine();
                for(int j = 0 ; j < capa.getFilasMatrizPesos() ; j++) {
                    if(j != capa.getFilasMatrizPesos() - 1)
                        bw.write(capa.getElementoVectorBiases(j) + ",");
                    else
                        bw.write(capa.getElementoVectorBiases(j) + "");
                }
                bw.newLine();
                bw.newLine();
            }
            bw.close();
            archivoAuxiliar.setReadOnly();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
