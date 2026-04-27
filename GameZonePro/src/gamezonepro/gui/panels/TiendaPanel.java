
package gamezonepro.gui.panels;

import gamezonepro.controllers.TiendaController;
import gamezonepro.controllers.RecompensasController;
import gamezonepro.MainFrame;
import gamezonepro.estructuras.ListaSimple;
import gamezonepro.model.Juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TiendaPanel extends JPanel {

    private CarritoPanel carritoPanel;
    private TiendaController controller;
    private RecompensasController recompensasController;
    private MainFrame frame;

    private JComboBox<String> comboGenero;
    private JComboBox<String> comboPlataforma;
    private JTextField txtBuscar;

    private JPanel panelJuegos;

    public TiendaPanel(MainFrame frame, TiendaController controller, RecompensasController recompensasController) {
        this.frame = frame;
        this.controller = controller;
        this.recompensasController = recompensasController;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 230, 255));

        // filtros
        comboGenero = new JComboBox<>(new String[]{"Todos", "Accion", "Aventura", "Deportes"});
        comboPlataforma = new JComboBox<>(new String[]{"Todas", "PC", "PlayStation", "Xbox", "Nintendo"});
        txtBuscar = new JTextField(15);

        JPanel panelFiltros = new JPanel();
        panelFiltros.setBackground(new Color(245, 230, 255));
        panelFiltros.add(new JLabel("Genero:"));
        panelFiltros.add(comboGenero);
        panelFiltros.add(new JLabel("Plataforma:"));
        panelFiltros.add(comboPlataforma);
        panelFiltros.add(new JLabel("Buscar:"));
        panelFiltros.add(txtBuscar);

        add(panelFiltros, BorderLayout.NORTH);

        // izquierda
        panelJuegos = new JPanel(new GridLayout(0, 3, 15, 15));
        panelJuegos.setBackground(new Color(245, 230, 255));
        JScrollPane scrollJuegos = new JScrollPane(panelJuegos);

        // derecha
        carritoPanel = new CarritoPanel(frame, controller, recompensasController);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollJuegos, carritoPanel);
        split.setDividerLocation(650);

        add(split, BorderLayout.CENTER);

        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                cargarTarjetas();
            }
        });

        comboGenero.addActionListener(e -> cargarTarjetas());
        comboPlataforma.addActionListener(e -> cargarTarjetas());

        cargarTarjetas();
    }

    private void cargarTarjetas() {
        panelJuegos.removeAll();

        String texto = txtBuscar.getText().toLowerCase();
        String generoFiltro = comboGenero.getSelectedItem().toString();
        String plataformaFiltro = comboPlataforma.getSelectedItem().toString();

        ListaSimple lista = controller.getCatalogo();

        for (int i = 0; i < lista.size(); i++) {
            Juego j = (Juego) lista.obtener(i);

            boolean coincideBusqueda =
                    j.getNombre().toLowerCase().contains(texto) ||
                    j.getId().toLowerCase().contains(texto);

            boolean coincideGenero =
                    generoFiltro.equals("Todos") ||
                    j.getCategoria().equalsIgnoreCase(generoFiltro);

            boolean coincidePlataforma =
                    plataformaFiltro.equals("Todas") ||
                    j.getPlataforma().equalsIgnoreCase(plataformaFiltro);

            if (coincideBusqueda && coincideGenero && coincidePlataforma) {

                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(new Color(255, 240, 255));
                card.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

                JPanel info = new JPanel(new GridLayout(5,1));
                info.setBackground(new Color(255, 240, 255));

                info.add(new JLabel(j.getNombre(), JLabel.CENTER));
                info.add(new JLabel("Genero: " + j.getCategoria(), JLabel.CENTER));
                info.add(new JLabel("Precio: Q" + j.getPrecio(), JLabel.CENTER));
                info.add(new JLabel("Plataforma: " + j.getPlataforma(), JLabel.CENTER));
                info.add(new JLabel("Stock: " + j.getStock(), JLabel.CENTER));

                JButton btn = crearBoton("Ver / Agregar");
                btn.addActionListener(e -> mostrarDetalle(j));

                card.add(info, BorderLayout.CENTER);
                card.add(btn, BorderLayout.SOUTH);

                panelJuegos.add(card);
            }
        }

        panelJuegos.revalidate();
        panelJuegos.repaint();
    }

    private void mostrarDetalle(Juego j) {

        int opcion = JOptionPane.showConfirmDialog(this,
                "Nombre: " + j.getNombre() +
                "\nGenero: " + j.getCategoria() +
                "\nPrecio: Q" + j.getPrecio() +
                "\nStock: " + j.getStock() +
                "\nDescripcion: " + j.getDescripcion() +
                "\n\n¿Agregar al carrito?",
                "Detalle",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            controller.agregarAlCarrito(j);
            carritoPanel.actualizar();
        }
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

