
package gamezonepro.gui.panels;

import javax.swing.*;
import java.awt.*;
import gamezonepro.MainFrame;

public class MenuPanel extends JPanel {

    public MenuPanel(MainFrame frame) {

        setLayout(new BorderLayout());

        // fondo suave
        setBackground(new Color(245, 230, 255));

        
        // titulo
        
        JLabel titulo = new JLabel("GameZone Pro", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(120, 70, 180));

        add(titulo, BorderLayout.NORTH);

        
        // panel botones
        
        JPanel panelBotones = new JPanel(new GridLayout(7, 1, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(40, 200, 40, 200));
        panelBotones.setBackground(new Color(245, 230, 255));

        // botones
        JButton btnTienda = crearBoton("Tienda");
        JButton btnTorneos = crearBoton("Torneos");
        JButton btnAlbum = crearBoton("Álbum");
        JButton btnRecompensas = crearBoton("Recompensas");
        JButton btnReportes = crearBoton("Reportes");
        JButton btnEstudiante = crearBoton("Estudiante");
        JButton btnSalir = crearBoton("Salir");

        panelBotones.add(btnTienda);
        panelBotones.add(btnTorneos);
        panelBotones.add(btnAlbum);
        panelBotones.add(btnRecompensas);
        panelBotones.add(btnReportes);
        panelBotones.add(btnEstudiante);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);

        
        // eventos
        
        btnTienda.addActionListener(e -> frame.mostrar("TIENDA"));
        btnTorneos.addActionListener(e -> frame.mostrar("TORNEOS"));
        btnAlbum.addActionListener(e -> frame.mostrar("ALBUM"));
        btnRecompensas.addActionListener(e -> frame.mostrar("RECOMPENSAS"));
        btnReportes.addActionListener(e -> frame.mostrar("REPORTES"));
        btnEstudiante.addActionListener(e -> frame.mostrar("ESTUDIANTE"));

        // salir
        btnSalir.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Deseas salir del juego?",
                    "Salir",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    
    // boton estilo juego
    
    private JButton crearBoton(String texto) {

        JButton btn = new JButton(texto);

        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(new Color(200, 150, 255));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // hover
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
