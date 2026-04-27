package gamezonepro.gui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import gamezonepro.MainFrame;
import gamezonepro.controllers.AlbumController;
import gamezonepro.controllers.RecompensasController;
import gamezonepro.estructuras.ListaSimple;
import gamezonepro.estructuras.NodoMatriz;
import gamezonepro.model.Carta;
import gamezonepro.util.ArchivoUtil;

public class AlbumPanel extends JPanel {

    private JPanel panelDetalles;
    private JLabel lblNombre, lblTipo, lblRareza, lblAtaque, lblDefensa, lblVida;

    private AlbumController controller;
    private JPanel grid;

    private NodoMatriz seleccion1 = null;
    private NodoMatriz seleccion2 = null;

    private JTextField txtBuscar;
    private JComboBox<String> comboBuscar; // nuevo

    private boolean modoIntercambio = false;
    
    private RecompensasController recompensasController;


    public AlbumPanel(MainFrame frame, RecompensasController recompensasController) {
        
         this.recompensasController = recompensasController;

        controller = new AlbumController();

        setLayout(new BorderLayout());

        // buscador y criterio
        comboBuscar = new JComboBox<>(new String[]{
                "Nombre", "Tipo", "Rareza"
        });

        txtBuscar = new JTextField(15);

        JPanel panelBusqueda = new JPanel();
        panelBusqueda.add(new JLabel("Buscar por:"));
        panelBusqueda.add(comboBuscar);
        panelBusqueda.add(txtBuscar);

        add(panelBusqueda, BorderLayout.NORTH);

        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {

                if (txtBuscar.getText().isEmpty()) {
                    renderizar(); //  volver a normal
                } else {
                    renderizarBusqueda(
                            txtBuscar.getText().toLowerCase(),
                            comboBuscar.getSelectedItem().toString()
                    );
                }
            }
        });

        // grid
        grid = new JPanel();
        add(new JScrollPane(grid), BorderLayout.CENTER);

        // panel detalles
        panelDetalles = new JPanel(new GridLayout(6, 1));
        panelDetalles.setPreferredSize(new Dimension(200, 0));
        panelDetalles.setBorder(BorderFactory.createTitledBorder("Detalles"));

        lblNombre = new JLabel("Nombre:");
        lblTipo = new JLabel("Tipo:");
        lblRareza = new JLabel("Rareza:");
        lblAtaque = new JLabel("Ataque:");
        lblDefensa = new JLabel("Defensa:");
        lblVida = new JLabel("Vida:");

        panelDetalles.add(lblNombre);
        panelDetalles.add(lblTipo);
        panelDetalles.add(lblRareza);
        panelDetalles.add(lblAtaque);
        panelDetalles.add(lblDefensa);
        panelDetalles.add(lblVida);

        add(panelDetalles, BorderLayout.EAST);

        // botones
        JButton btnAgregar = new JButton("Agregar Carta");
        JButton btnGuardar = new JButton("Guardar Álbum");
        JButton btnIntercambiar = new JButton("Modo Intercambio: OFF");
        JButton btnMenu = new JButton("Menú");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnIntercambiar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnMenu);

        add(panelBotones, BorderLayout.SOUTH);

        // intercambio de on a off
        btnIntercambiar.addActionListener(e -> {
            modoIntercambio = !modoIntercambio;

            if (modoIntercambio) {
                btnIntercambiar.setText("Modo Intercambio: ON");
            } else {
                btnIntercambiar.setText("Modo Intercambio: OFF");
                seleccion1 = null;
                seleccion2 = null;
                renderizar();
            }
        });

        // agregar carta
        btnAgregar.addActionListener(e -> {

            ListaSimple cartas = ArchivoUtil.cargarCartas();

            String mensaje = "CATÁLOGO DE CARTAS:\n\n";

            for (int i = 0; i < cartas.size(); i++) {
                Carta c = (Carta) cartas.obtener(i);
                mensaje += c.getCodigo() + " - " +
                        c.getNombre() + " (" + c.getTipo() + ")\n";
            }

            JOptionPane.showMessageDialog(this, mensaje);

            String codigo = JOptionPane.showInputDialog("Ingrese el código de la carta:");
            if (codigo == null) return;

            Carta seleccionada = null;

            for (int i = 0; i < cartas.size(); i++) {
                Carta c = (Carta) cartas.obtener(i);

                if (c.getCodigo().equalsIgnoreCase(codigo)) {
                    seleccionada = c;
                    break;
                }
            }

            if (seleccionada == null) {
                JOptionPane.showMessageDialog(this, "Carta no encontrada");
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Deseas agregar " + seleccionada.getNombre() + " al álbum?",
                    "Confirmar compra",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion != JOptionPane.YES_OPTION) return;

            if (controller.agregarCarta(seleccionada)) {
                JOptionPane.showMessageDialog(this, "Carta comprada");
                
                 recompensasController.registrarCarta(seleccionada.getRareza());
                     
                renderizar();
            } else {
                JOptionPane.showMessageDialog(this, "Álbum lleno");
            }
        });

        // 💾 GUARDAR
        btnGuardar.addActionListener(e -> {
            ArchivoUtil.guardarAlbum(
                    controller.getInicio(),
                    controller.getFilas(),
                    controller.getColumnas()
            );

            JOptionPane.showMessageDialog(this, "Álbum guardado");
        });
        
        btnMenu.addActionListener(e -> {
             frame.mostrar("MENU");
         });

        renderizar();
    }

    // render normal
    private void renderizar() {

        grid.removeAll();

        grid.setLayout(new GridLayout(
                controller.getFilas(),
                controller.getColumnas()
        ));

        NodoMatriz actual = controller.getInicio();

        for (int i = 0; i < controller.getFilas(); i++) {

            NodoMatriz filaActual = actual;

            for (int j = 0; j < controller.getColumnas(); j++) {

                JPanel celda = new JPanel();
                celda.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                celda.setBackground(Color.LIGHT_GRAY);

                NodoMatriz nodo = filaActual;

                if (nodo.getDato() == null) {
                    celda.add(new JLabel("Vacía"));
                } else {
                    Carta c = nodo.getDato();

                    celda.setBackground(Color.WHITE);
                    celda.setLayout(new BorderLayout());

                    celda.add(new JLabel(c.getNombre(), JLabel.CENTER), BorderLayout.CENTER);
                    celda.add(new JLabel(c.getTipo(), JLabel.CENTER), BorderLayout.SOUTH);
                }

                celda.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {

                        if (nodo.getDato() == null) return;

                        Carta c = nodo.getDato();

                        lblNombre.setText("Nombre: " + c.getNombre());
                        lblTipo.setText("Tipo: " + c.getTipo());
                        lblRareza.setText("Rareza: " + c.getRareza());
                        lblAtaque.setText("Ataque: " + c.getAtaque());
                        lblDefensa.setText("Defensa: " + c.getDefensa());
                        lblVida.setText("Vida: " + c.getVida());

                        if (!modoIntercambio) return;

                        if (seleccion1 == null) {
                            seleccion1 = nodo;
                            celda.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                        } else {
                            seleccion2 = nodo;

                            Carta temp = seleccion1.getDato();
                            seleccion1.setDato(seleccion2.getDato());
                            seleccion2.setDato(temp);

                            seleccion1 = null;
                            seleccion2 = null;

                            renderizar();
                        }
                    }
                });

                grid.add(celda);
                filaActual = filaActual.derecha;
            }

            actual = actual.abajo;
        }

        grid.revalidate();
        grid.repaint();
    }

    // búsqueda con criterio
    private void renderizarBusqueda(String filtro, String criterio) {

        grid.removeAll();

        boolean encontrado = false;

        grid.setLayout(new GridLayout(
                controller.getFilas(),
                controller.getColumnas()
        ));

        NodoMatriz actual = controller.getInicio();

        for (int i = 0; i < controller.getFilas(); i++) {

            NodoMatriz fila = actual;

            for (int j = 0; j < controller.getColumnas(); j++) {

                JPanel celda = new JPanel();
                celda.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                celda.setBackground(Color.LIGHT_GRAY);

                if (fila.getDato() != null) {

                    Carta c = fila.getDato();

                    String valor = "";

                    if (criterio.equals("Nombre")) {
                        valor = c.getNombre().toLowerCase();
                    } else if (criterio.equals("Tipo")) {
                        valor = c.getTipo().toLowerCase();
                    } else if (criterio.equals("Rareza")) {
                        valor = c.getRareza().toLowerCase();
                    }

                    celda.setLayout(new BorderLayout());
                    celda.add(new JLabel(c.getNombre(), JLabel.CENTER), BorderLayout.CENTER);
                    celda.add(new JLabel(c.getTipo(), JLabel.CENTER), BorderLayout.SOUTH);

                    if (valor.contains(filtro)) {
                        celda.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
                        celda.setBackground(Color.YELLOW);
                        encontrado = true;
                    }

                } else {
                    celda.add(new JLabel("Vacía"));
                }

                grid.add(celda);
                fila = fila.derecha;
            }

            actual = actual.abajo;
        }

        grid.revalidate();
        grid.repaint();

        if (!encontrado && !filtro.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes esa carta en el álbum");
        }
    }
}