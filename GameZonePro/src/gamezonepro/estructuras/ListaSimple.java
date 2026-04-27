package gamezonepro.estructuras;

public class ListaSimple {
  
    private NodoSimple cabeza;
    private int size;

    public ListaSimple() {
        cabeza = null;
    }

    // agregar al final
    public void agregar(Object dato) {
        NodoSimple nuevo = new NodoSimple(dato);

        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoSimple aux = cabeza;
            while (aux.siguiente != null) {
                aux = aux.siguiente;
            }
            aux.siguiente = nuevo;
        }
    }
    
    public void agregarInicio(Object dato) {
    NodoSimple nuevo = new NodoSimple(dato);
    nuevo.siguiente = cabeza;
    cabeza = nuevo;
}

    // eliminar por posición
    public void eliminar(int index) {
        if (cabeza == null) return;

        if (index == 0) {
            cabeza = cabeza.siguiente;
            return;
        }

        NodoSimple aux = cabeza;
        for (int i = 0; i < index - 1; i++) {
            if (aux.siguiente == null) return;
            aux = aux.siguiente;
        }

        if (aux.siguiente != null) {
            aux.siguiente = aux.siguiente.siguiente;
        }
    }
    
    public void set(int index, Object valor) {

    NodoSimple actual = cabeza;
    int i = 0;

    while (actual != null) {
        if (i == index) {
            actual.dato = valor;
            return;
        }
        actual = actual.siguiente;
        i++;
    }
}

    // obtener dato
    public Object obtener(int index) {
        NodoSimple aux = cabeza;
        int i = 0;

        while (aux != null) {
            if (i == index) return aux.dato;
            aux = aux.siguiente;
            i++;
        }

        return null;
    }

    // tamaño
    public int size() {
        int count = 0;
        NodoSimple aux = cabeza;

        while (aux != null) {
            count++;
            aux = aux.siguiente;
        }

        return count;
    }
}