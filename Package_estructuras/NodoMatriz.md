```java
package gamezonepro.estructuras;

import gamezonepro.model.Carta;

public class NodoMatriz {

    private Carta dato; // privado

    public NodoMatriz arriba;
    public NodoMatriz abajo;
    public NodoMatriz izquierda;
    public NodoMatriz derecha;

    public int fila;
    public int columna;

    public NodoMatriz(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.dato = null;
    }

    // get
    public Carta getDato() {
        return dato;
    }

    // set
    public void setDato(Carta dato) {
        this.dato = dato;
    }
}
