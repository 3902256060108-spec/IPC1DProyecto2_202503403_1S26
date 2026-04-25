```java

package gamezonepro.gui.panels;

import javax.swing.*;
import java.awt.*;

import gamezonepro.MainFrame;
import gamezonepro.util.ArchivoUtil;
import gamezonepro.estructuras.ListaSimple;
import gamezonepro.controllers.TiendaController;
import gamezonepro.controllers.TorneoController;
import gamezonepro.controllers.AlbumController;

public class ReportesPanel extends JPanel {

    private MainFrame frame;

    private TiendaController tiendaController;
    private TorneoController torneoController;
    private AlbumController albumController;

    public ReportesPanel(MainFrame frame,
                         TiendaController tiendaController,
                         TorneoController torneoController,
                         AlbumController albumController) {

        this.frame = frame;
        this.tiendaController = tiendaController;
        this.torneoController = torneoController;
        this.albumController = albumController;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 230, 255));

        // titulo
        JLabel titulo = new JLabel("REPORTES", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(120, 70, 180));

        add(titulo, BorderLayout.NORTH);

        // panel botones
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(40, 200, 40, 200));
        panelBotones.setBackground(new Color(245, 230, 255));

        JButton btnInventario = crearBoton("Inventario");
        JButton btnVentas = crearBoton("Ventas");
        JButton btnAlbum = crearBoton("Album");
        JButton btnTorneos = crearBoton("Torneos");
        JButton btnMenu = crearBoton("Menu");

        panelBotones.add(btnInventario);
        panelBotones.add(btnVentas);
        panelBotones.add(btnAlbum);
        panelBotones.add(btnTorneos);
        panelBotones.add(btnMenu);

        add(panelBotones, BorderLayout.CENTER);

        // eventos

        btnInventario.addActionListener(e -> {
            ListaSimple juegos = tiendaController.getCatalogo();
            ArchivoUtil.generarReporteInventario(juegos);
        });

        btnVentas.addActionListener(e -> {
            ListaSimple historial = ArchivoUtil.cargarHistorial();
            ArchivoUtil.generarReporteVentas(historial);
        });

        btnAlbum.addActionListener(e -> {
            ArchivoUtil.generarReporteAlbum(
                    albumController.getInicio(),
                    albumController.getFilas(),
                    albumController.getColumnas()
            );
        });

        btnTorneos.addActionListener(e -> {
            ListaSimple torneos = torneoController.getTorneos();
            ListaSimple tickets = ArchivoUtil.cargarTickets();
            ArchivoUtil.generarReporteTorneos(torneos, tickets);
        });

        btnMenu.addActionListener(e -> {
            frame.mostrar("MENU");
        });
    }

    // boton 
    private JButton crearBoton(String texto) {

        JButton btn = new JButton(texto);

        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(new Color(200, 150, 255));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(170, 120, 240));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(200, 150, 255));
            }
        });

        return btn;
    }
}

