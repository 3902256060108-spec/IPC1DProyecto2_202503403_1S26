package gamezonepro.controllers;

import gamezonepro.estructuras.NodoMatriz;
import gamezonepro.model.Carta;
import gamezonepro.util.ArchivoUtil;

public class AlbumController {

    private NodoMatriz inicio;
    private NodoMatriz[][] matriz; //global
    private int filas = 4;
    private int columnas = 6;

    public AlbumController() {
        crearMatriz();
        
        ArchivoUtil.cargarAlbum(inicio, filas, columnas);
    }

    private void crearMatriz() {
        matriz = new NodoMatriz[filas][columnas]; //guardarla

        // crear nodos
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = new NodoMatriz(i, j);
            }
        }

        // enlazar nodos
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {

                if (i > 0) matriz[i][j].arriba = matriz[i - 1][j];
                if (i < filas - 1) matriz[i][j].abajo = matriz[i + 1][j];
                if (j > 0) matriz[i][j].izquierda = matriz[i][j - 1];
                if (j < columnas - 1) matriz[i][j].derecha = matriz[i][j + 1];
            }
        }

        inicio = matriz[0][0];
    }

    public NodoMatriz getInicio() {
        return inicio;
    }

    // agregar carta 
    public boolean agregarCarta(Carta carta) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {

                if (matriz[i][j].getDato() == null) {
                    matriz[i][j].setDato(carta);
                    return true;
                }
            }
        }
        return false;
    }

    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }
}
