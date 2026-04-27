package gamezonepro.estructuras;

public class Cola {

    private NodoCola frente;
    private NodoCola fin;
    private int tamanio;

    public Cola() {
        frente = null;
        fin = null;
        tamanio = 0;
    }

    // encolar
    public void encolar(Object dato) {
        NodoCola nuevo = new NodoCola(dato);

        if (estaVacia()) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            fin = nuevo;
        }

        tamanio++;
    }

    // desencolar
    public synchronized Object desencolar() {

        if (estaVacia()) return null;

        Object dato = frente.dato;
        frente = frente.siguiente;

        if (frente == null) {
            fin = null;
        }

        tamanio--;
        return dato;
    }

    // ver frente
    public Object peek() {
        if (estaVacia()) return null;
        return frente.dato;
    }

    // vacia
    public boolean estaVacia() {
        return frente == null;
    }

    // tamaño
    public int tamanio() {
        return tamanio;
    }
    
    
    public ListaSimple toLista() {
    ListaSimple lista = new ListaSimple();
    NodoCola aux = frente;

    while (aux != null) {
        lista.agregar(aux.dato);
        aux = aux.siguiente;
    }

    return lista;
}
    
}
