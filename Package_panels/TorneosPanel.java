package gamezonepro.gui.panels;

import javax.swing.*;
import java.awt.*;

import gamezonepro.MainFrame;
import gamezonepro.controllers.RecompensasController;
import gamezonepro.controllers.TorneoController;
import gamezonepro.estructuras.ListaSimple;
import gamezonepro.model.Torneo;
import gamezonepro.threads.Taquilla;
import gamezonepro.util.ArchivoUtil;

public class TorneosPanel extends JPanel {

    private TorneoController controller;
    private JList<Torneo> lista;

    private JTextArea txtLog;
    private JTextArea txtCola;
    private JLabel lblTaquilla1;
    private JLabel lblTaquilla2;

    private JLabel lblNombre;
    private JLabel lblJuego;
    private JLabel lblFecha;
    private JLabel lblHora;
    private JLabel lblPrecio;
    private JLabel lblTickets;
    private JLabel lblVendidos;

    private RecompensasController recompensasController;

    public TorneosPanel(MainFrame frame, RecompensasController recompensasController) {

        this.recompensasController = recompensasController;
        controller = new TorneoController();

        setLayout(new BorderLayout());
        setBackground(new Color(245, 230, 255));

        // titulo
        JLabel titulo = new JLabel("TORNEOS", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(120, 70, 180));
        add(titulo, BorderLayout.NORTH);

        // lista
        DefaultListModel<Torneo> modelo = new DefaultListModel<>();
        ListaSimple torneos = controller.getTorneos();

        for (int i = 0; i < torneos.size(); i++) {
            modelo.addElement((Torneo) torneos.obtener(i));
        }

        lista = new JList<>(modelo);
        lista.setBackground(Color.WHITE);

        add(new JScrollPane(lista), BorderLayout.CENTER);

        // panel info
        JPanel panelInfo = new JPanel(new GridLayout(7, 1, 5, 5));
        panelInfo.setBackground(new Color(255, 240, 255));
        panelInfo.setPreferredSize(new Dimension(220, 0));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Informacion"));

        lblVendidos = new JLabel("Vendidos: 0");
        lblNombre = new JLabel("Nombre:");
        lblJuego = new JLabel("Juego:");
        lblFecha = new JLabel("Fecha:");
        lblHora = new JLabel("Hora:");
        lblPrecio = new JLabel("Precio:");
        lblTickets = new JLabel("Tickets:");

        panelInfo.add(lblVendidos);
        panelInfo.add(lblNombre);
        panelInfo.add(lblJuego);
        panelInfo.add(lblFecha);
        panelInfo.add(lblHora);
        panelInfo.add(lblPrecio);
        panelInfo.add(lblTickets);

        add(panelInfo, BorderLayout.WEST);

        lista.addListSelectionListener(e -> {
            Torneo t = lista.getSelectedValue();

            if (t != null) {
                lblNombre.setText("Nombre: " + t.getNombre());
                lblJuego.setText("Juego: " + t.getJuego());
                lblFecha.setText("Fecha: " + t.getFecha());
                lblHora.setText("Hora: " + t.getHora());
                lblPrecio.setText("Precio: Q" + t.getPrecio());
                lblTickets.setText("Disponibles: " + t.getTicketsDisponibles());
            }
        });

        // panel derecho
        JPanel panelDerecha = new JPanel(new BorderLayout());
        panelDerecha.setPreferredSize(new Dimension(300, 0)); 
        panelDerecha.setBackground(new Color(245, 230, 255));

        lblTaquilla1 = new JLabel("Taquilla 1: Libre");
        lblTaquilla2 = new JLabel("Taquilla 2: Libre");

        JPanel panelEstado = new JPanel(new GridLayout(2, 1));
        panelEstado.setBackground(new Color(255, 240, 255));
        panelEstado.setBorder(BorderFactory.createTitledBorder("Estado"));

        panelEstado.add(lblTaquilla1);
        panelEstado.add(lblTaquilla2);

        txtLog = new JTextArea();
        txtLog.setEditable(false);

        JScrollPane scrollLog = new JScrollPane(txtLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Ventas"));

        txtCola = new JTextArea();
        txtCola.setEditable(false);

        JScrollPane scrollCola = new JScrollPane(txtCola);
        scrollCola.setBorder(BorderFactory.createTitledBorder("Cola"));
        
        JPanel panelCentroDerecha = new JPanel(new GridLayout(2, 1, 5, 5));

panelCentroDerecha.add(scrollLog);
panelCentroDerecha.add(scrollCola);

panelDerecha.add(panelEstado, BorderLayout.NORTH);
panelDerecha.add(panelCentroDerecha, BorderLayout.CENTER);

        

        add(panelDerecha, BorderLayout.EAST);

        // botones
        JButton btnInscribir = crearBoton("Inscribirse");
        JButton btnIniciar = crearBoton("Iniciar Venta");
        JButton btnGuardar = crearBoton("Guardar Tickets");
        JButton btnMenu = crearBoton("Menu");

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(245, 230, 255));

        panelBotones.add(btnInscribir);
        panelBotones.add(btnIniciar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnMenu);

        add(panelBotones, BorderLayout.SOUTH);

        // eventos
        btnInscribir.addActionListener(e -> {

            Torneo t = lista.getSelectedValue();

            if (t == null) {
                JOptionPane.showMessageDialog(this, "Selecciona un torneo");
                return;
            }

            String nombre = JOptionPane.showInputDialog("Ingresa tu nombre:");
            if (nombre == null || nombre.trim().isEmpty()) return;

            controller.inscribir(nombre.trim());
            recompensasController.registrarTorneo();

            actualizarCola();
        });

        btnIniciar.addActionListener(e -> {

            Torneo t = lista.getSelectedValue();

            if (t == null) {
                JOptionPane.showMessageDialog(this, "Selecciona un torneo");
                return;
            }

            if (controller.getCola().estaVacia()) {
                JOptionPane.showMessageDialog(this, "No hay usuarios en la cola");
                return;
            }
            
            controller.resetVendidos();
            actualizarVendidos(0);

            btnIniciar.setEnabled(false);

            Taquilla t1 = new Taquilla("Taquilla 1", controller.getCola(), t, this, controller);
            Taquilla t2 = new Taquilla("Taquilla 2", controller.getCola(), t, this, controller);

            t1.start();
            t2.start();

            new Thread(() -> {
                try {
                    t1.join();
                    t2.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Venta finalizada");
                    btnIniciar.setEnabled(true);
                });
            }).start();
        });

        btnGuardar.addActionListener(e -> {
            ArchivoUtil.guardarTickets(controller.getHistorialTickets());
            JOptionPane.showMessageDialog(this, "Tickets guardados");
        });

        btnMenu.addActionListener(e -> frame.mostrar("MENU"));

        actualizarCola();
        cargarHistorialEnUI();
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

    public void actualizarEstado(String texto) {
        if (texto.contains("Taquilla 1")) lblTaquilla1.setText(texto);
        else if (texto.contains("Taquilla 2")) lblTaquilla2.setText(texto);
    }

    public void agregarLog(String texto) {
        txtLog.append(texto + "\n");
    }

    public void actualizarCola() {
        txtCola.setText("");
        ListaSimple listaCola = controller.getCola().toLista();

        for (int i = 0; i < listaCola.size(); i++) {
            txtCola.append(listaCola.obtener(i) + "\n");
        }
    }

    public void actualizarVendidos(int vendidos) {
        lblVendidos.setText("Vendidos: " + vendidos);
    }

    private void cargarHistorialEnUI() {
        ListaSimple historial = controller.getHistorialTickets();

        for (int i = 0; i < historial.size(); i++) {
            txtLog.append(historial.obtener(i) + "\n");
        }
    }
}

