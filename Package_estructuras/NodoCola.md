```java
package gamezonepro.estructuras;

public class NodoCola {

    public Object dato;
    public NodoCola siguiente;

    public NodoCola(Object dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
