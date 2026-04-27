package gamezonepro.threads;

import gamezonepro.estructuras.Cola;
import gamezonepro.model.Torneo;
import gamezonepro.controllers.TorneoController;
import gamezonepro.gui.panels.TorneosPanel;

import javax.swing.SwingUtilities;

public class Taquilla extends Thread {

    private String nombreTaquilla;
    private Cola cola;
    private Torneo torneo;
    private TorneosPanel panel;
    private TorneoController controller;

    public Taquilla(String nombreTaquilla, Cola cola, Torneo torneo,
                    TorneosPanel panel, TorneoController controller) {

        this.nombreTaquilla = nombreTaquilla;
        this.cola = cola;
        this.torneo = torneo;
        this.panel = panel;
        this.controller = controller;
    }

    @Override
    public void run() {

        while (torneo.getTicketsDisponibles() > 0) {

            String usuario = (String) cola.desencolar();

            if (usuario == null) break;

            // actualizar UI
            SwingUtilities.invokeLater(() -> {
                panel.actualizarEstado(nombreTaquilla + " atendiendo a " + usuario);
                panel.actualizarCola();
            });

            try {
                Thread.sleep(800 + (int)(Math.random() * 1200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (torneo) {

                if (torneo.getTicketsDisponibles() > 0) {

                    torneo.setTicketsDisponibles(
                            torneo.getTicketsDisponibles() - 1
                    );

                    // registrar venta
                    controller.registrarVenta(usuario, torneo);

                    // contador correcto
                    controller.incrementarVendidos();

                    SwingUtilities.invokeLater(() -> {
                        panel.actualizarVendidos(controller.getVendidos());
                    });

                    // log
                    SwingUtilities.invokeLater(() -> {
                        panel.agregarLog("Ticket vendido a " + usuario);
                    });

                } else {
                    break;
                }
            }
        }

        // finalización
        SwingUtilities.invokeLater(() -> {
            panel.actualizarEstado(nombreTaquilla + ": Libre");
            panel.agregarLog(nombreTaquilla + " finalizó.");
        });
    }
}