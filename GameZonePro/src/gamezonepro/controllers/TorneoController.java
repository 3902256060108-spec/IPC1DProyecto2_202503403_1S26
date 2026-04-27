package gamezonepro.controllers;

import gamezonepro.estructuras.Cola;
import gamezonepro.estructuras.ListaSimple;
import gamezonepro.model.Torneo;
import gamezonepro.util.ArchivoUtil;

public class TorneoController {

    private ListaSimple torneos;
    private Cola cola;
    private ListaSimple historialTickets;

    public TorneoController() {
        torneos = ArchivoUtil.cargarTorneos();
        cola = new Cola();

        // cargar historial
        historialTickets = ArchivoUtil.cargarTickets();
    }

    public ListaSimple getTorneos() {
        return torneos;
    }

    public Cola getCola() {
        return cola;
    }
    
    private int vendidos = 0;

public void incrementarVendidos() {
    vendidos++;
}

public int getVendidos() {
    return vendidos;
}

public void resetVendidos() {
    vendidos = 0;
}

    public void inscribir(String nombre) {
        cola.encolar(nombre);
    }

    public void registrarVenta(String usuario, Torneo torneo) {
        String registro = usuario + "|" + torneo.getNombre() + "|" + torneo.getJuego();
        historialTickets.agregar(registro);
    }

    public ListaSimple getHistorialTickets() {
        return historialTickets;
    }
}
