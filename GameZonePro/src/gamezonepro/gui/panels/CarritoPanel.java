
package gamezonepro.gui.panels;

import gamezonepro.controllers.TiendaController;
import javax.swing.*;
import java.awt.*;
import gamezonepro.MainFrame;
import gamezonepro.controllers.RecompensasController;
import gamezonepro.estructuras.ListaSimple;
import gamezonepro.model.Juego;
import javax.swing.table.DefaultTableModel;

public class CarritoPanel extends JPanel {

    private TiendaController controller;
    private JTable tabla;
    private JLabel totalLabel;
    private DefaultTableModel modelo;
    private MainFrame frame;

    private JTable tablaHistorial;
    private DefaultTableModel modeloHistorial;
    private RecompensasController recompensasController;

    public CarritoPanel(MainFrame frame, TiendaController controller, RecompensasController recompensasController) {
        this.recompensasController = recompensasController;
        this.frame = frame;
        this.controller = controller;

        setLayout(new GridLayout(2, 1));
        setBackground(new Color(245, 230, 255));

        // carrito
        JPanel panelCarrito = new JPanel(new BorderLayout());
        panelCarrito.setBackground(new Color(245, 230, 255));

        JLabel tituloCarrito = new JLabel("CARRITO", JLabel.CENTER);
        tituloCarrito.setFont(new Font("Arial", Font.BOLD, 18));

        String[] columnas = {"ID", "Nombre", "Precio"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);

        tabla.getTableHeader().setBackground(new Color(200,150,255));

        totalLabel = new JLabel("Total: Q0", JLabel.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));

        panelCarrito.add(tituloCarrito, BorderLayout.NORTH);
        panelCarrito.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panelCarrito.add(totalLabel, BorderLayout.SOUTH);

        // historial
        JPanel panelHistorial = new JPanel(new BorderLayout());
        panelHistorial.setBackground(new Color(245, 230, 255));

        JLabel tituloHistorial = new JLabel("HISTORIAL", JLabel.CENTER);
        tituloHistorial.setFont(new Font("Arial", Font.BOLD, 16));

        String[] columnasHist = {"ID", "Nombre", "Precio", "Estado"};
        modeloHistorial = new DefaultTableModel(columnasHist, 0);
        tablaHistorial = new JTable(modeloHistorial);

        tablaHistorial.getTableHeader().setBackground(new Color(200,150,255));

        JButton btnEliminar = crearBoton("Eliminar");
        JButton btnComprar = crearBoton("Comprar");
        JButton btnMenu = crearBoton("Menu");

        JPanel botones = new JPanel();
        botones.setBackground(new Color(245, 230, 255));
        botones.add(btnEliminar);
        botones.add(btnComprar);
        botones.add(btnMenu);

        panelHistorial.add(tituloHistorial, BorderLayout.NORTH);
        panelHistorial.add(new JScrollPane(tablaHistorial), BorderLayout.CENTER);
        panelHistorial.add(botones, BorderLayout.SOUTH);

        add(panelCarrito);
        add(panelHistorial);

        btnEliminar.addActionListener(e -> eliminar());
        btnComprar.addActionListener(e -> comprar());
        btnMenu.addActionListener(e -> frame.mostrar("MENU"));

        cargarCarrito();
        cargarHistorial();
    }

    public void cargarCarrito() {
        modelo.setRowCount(0);
        ListaSimple carrito = controller.getCarrito();
        double total = 0;

        for (int i = 0; i < carrito.size(); i++) {
            Juego j = (Juego) carrito.obtener(i);
            total += j.getPrecio();

            modelo.addRow(new Object[]{j.getId(), j.getNombre(), j.getPrecio()});
        }

        totalLabel.setText("Total: Q" + total);
    }

    public void cargarHistorial() {
        modeloHistorial.setRowCount(0);

        ListaSimple historial = controller.getHistorial();

        for (int i = 0; i < historial.size(); i++) {
            Juego j = (Juego) historial.obtener(i);

            modeloHistorial.addRow(new Object[]{
                    j.getId(), j.getNombre(), j.getPrecio(), "COMPRADO"
            });
        }
    }

    public void actualizar() {
        cargarCarrito();
        cargarHistorial();
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            controller.eliminarDelCarrito(fila);
            cargarCarrito();
        }
    }

    private void comprar() {
        ListaSimple carrito = controller.getCarrito();

        if (carrito.size() == 0) {
            JOptionPane.showMessageDialog(this, "Carrito vacío");
            return;
        }

        controller.confirmarCompra();

        for (int i = 0; i < carrito.size(); i++) {
            Juego juego = (Juego) carrito.obtener(i);
            recompensasController.registrarCompra(juego.getPrecio());
        }

        JOptionPane.showMessageDialog(this, "Compra realizada");
        actualizar();
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);

        btn.setBackground(new Color(200,150,255));
        btn.setForeground(Color.WHITE);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(170,120,240));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(200,150,255));
            }
        });

        return btn;
    }
}

