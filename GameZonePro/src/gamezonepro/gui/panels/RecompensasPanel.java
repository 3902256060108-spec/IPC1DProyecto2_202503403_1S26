package gamezonepro.gui.panels;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

import gamezonepro.MainFrame;
import gamezonepro.controllers.RecompensasController;
import gamezonepro.estructuras.ListaSimple;
import gamezonepro.model.Logro;
import gamezonepro.model.Usuario;
import gamezonepro.util.ArchivoUtil;

public class RecompensasPanel extends JPanel {

    private RecompensasController controller;
    private MainFrame frame;

    private JLabel lblXP;
    private JLabel lblNivel;
    private JProgressBar barra;

    private JPanel panelLogros;

    // nuevo
    private JTable tablaLeaderboard;
    private JPanel panelPodio;

    public RecompensasPanel(MainFrame frame, RecompensasController controller) {

        this.frame = frame;
        this.controller = controller;

        setLayout(new BorderLayout());

        
        // info usuario
        
        JPanel top = new JPanel(new GridLayout(3, 1));

        lblXP = new JLabel("XP: 0");
        lblNivel = new JLabel("Nivel: 1 - Aprendiz");

        barra = new JProgressBar(0, 7000);
        barra.setStringPainted(true);

        top.add(lblXP);
        top.add(lblNivel);
        top.add(barra);

        add(top, BorderLayout.NORTH);

        
        // centro
        
        JPanel centro = new JPanel(new GridLayout(1, 2));

        panelLogros = new JPanel(new GridLayout(0, 1));
        centro.add(new JScrollPane(panelLogros));

        JPanel panelLeaderboard = new JPanel(new BorderLayout());

        panelPodio = new JPanel(new GridLayout(3, 1));
        panelLeaderboard.add(panelPodio, BorderLayout.NORTH);

        tablaLeaderboard = new JTable();
        panelLeaderboard.add(new JScrollPane(tablaLeaderboard), BorderLayout.CENTER);

        centro.add(panelLeaderboard);

        add(centro, BorderLayout.CENTER);

        
        // boton menú
        
        JButton btnMenu = new JButton("Menú");

        btnMenu.addActionListener(e -> frame.mostrar("MENU"));

        add(btnMenu, BorderLayout.SOUTH);

        actualizar();
    }

    
    // actualizar todo
    
    public void actualizar() {

        // XP
        lblXP.setText("XP: " + controller.getUsuario().getXp());

        lblNivel.setText("Nivel: " + controller.getUsuario().getNivel()
                + " - " + controller.getUsuario().getRango());

        barra.setValue(controller.getUsuario().getXp());

        // logros
        panelLogros.removeAll();

        ListaSimple lista = controller.getLogros();

        for (int i = 0; i < lista.size(); i++) {

            Logro l = (Logro) lista.obtener(i);

            JLabel lbl = new JLabel(
                    l.getNombre() + " - " +
                    (l.isDesbloqueado() ? "✔ DESBLOQUEADO" : "🔒 BLOQUEADO")
            );

            panelLogros.add(lbl);
        }

        panelLogros.revalidate();
        panelLogros.repaint();

        // leaderboard
        cargarLeaderboard();
    }

    
    // leaderboard
    
    private void cargarLeaderboard() {

        ListaSimple lista = ArchivoUtil.cargarLeaderboard();

        //  agregar usuario actual
        lista.agregar(controller.getUsuario());

        ordenarPorXP(lista);

        // podio
        panelPodio.removeAll();

        if (lista.size() >= 3) {

            Usuario u1 = (Usuario) lista.obtener(0);
            Usuario u2 = (Usuario) lista.obtener(1);
            Usuario u3 = (Usuario) lista.obtener(2);

            JLabel l1 = new JLabel("🥇 1°: " + u1.getNombre() + " (" + u1.getXp() + ")");
            JLabel l2 = new JLabel("🥈 2°: " + u2.getNombre() + " (" + u2.getXp() + ")");
            JLabel l3 = new JLabel("🥉 3°: " + u3.getNombre() + " (" + u3.getXp() + ")");

            l1.setForeground(Color.ORANGE);
            l2.setForeground(Color.GRAY);
            l3.setForeground(new Color(205, 127, 50));

            panelPodio.add(l1);
            panelPodio.add(l2);
            panelPodio.add(l3);
        }

        panelPodio.revalidate();

        // tabla
        String[] columnas = {"#", "Usuario", "XP"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (int i = 0; i < lista.size() && i < 10; i++) {

            Usuario u = (Usuario) lista.obtener(i);

            modelo.addRow(new Object[]{
                    (i + 1),
                    u.getNombre(),
                    u.getXp()
            });
        }

        tablaLeaderboard.setModel(modelo);

        // resaltar usuario
        tablaLeaderboard.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                String nombre = (String) table.getValueAt(row, 1);

                if (nombre.equals(controller.getUsuario().getNombre())) {
                    c.setBackground(Color.YELLOW);
                } else {
                    c.setBackground(Color.WHITE);
                }

                return c;
            }
        });
    }

    
    // ordenamiento
    
    private void ordenarPorXP(ListaSimple lista) {

        for (int i = 0; i < lista.size(); i++) {
            for (int j = 0; j < lista.size() - 1; j++) {

                Usuario u1 = (Usuario) lista.obtener(j);
                Usuario u2 = (Usuario) lista.obtener(j + 1);

                if (u1.getXp() < u2.getXp()) {

                    Object temp = lista.obtener(j);
                    lista.set(j, lista.obtener(j + 1));
                    lista.set(j + 1, temp);
                }
            }
        }
    }
}
