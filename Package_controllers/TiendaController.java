package gamezonepro.controllers;

import gamezonepro.estructuras.ListaSimple;
import gamezonepro.util.ArchivoUtil;
import gamezonepro.model.Juego;

public class TiendaController {

    private ListaSimple historial;
    private ListaSimple carrito;
    private ListaSimple catalogo;

    public TiendaController() {
        carrito = new ListaSimple();
        catalogo = ArchivoUtil.cargarCatalogo("catalogo.txt");

        // cargar historial desde archivo
        historial = ArchivoUtil.cargarHistorial();
    }

    public void agregarAlCarrito(Object juego) {
        carrito.agregar(juego);
    }

    public void eliminarDelCarrito(int index) {
        carrito.eliminar(index);
    }

    public void confirmarCompra() {

        for (int i = 0; i < carrito.size(); i++) {
            Juego j = (Juego) carrito.obtener(i);

            // insertar al inicio (más reciente primero)
            historial.agregarInicio(j);
        }

        vaciarCarrito();

        // guardar en archivo automáticamente
        ArchivoUtil.guardarHistorial(historial);
    }

    public ListaSimple getCarrito() {
        return carrito;
    }

    public ListaSimple getCatalogo() {
        return catalogo;
    }

    public ListaSimple getHistorial() {
        return historial;
    }

    public void vaciarCarrito() {
        carrito = new ListaSimple();
    }
}
