package gamezonepro.gui.panels;

import javax.swing.*;
import java.awt.*;

import gamezonepro.MainFrame;

public class EstudiantePanel extends JPanel {

    private MainFrame frame;

    public EstudiantePanel(MainFrame frame) {

        this.frame = frame;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 230, 255)); // fondo suave

        
        // titulo
        
        JLabel titulo = new JLabel("👩‍💻 PERFIL DEL JUGADOR", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(120, 70, 180));

        add(titulo, BorderLayout.NORTH);

        
        // panel central
        
        JPanel card = new JPanel();
        card.setLayout(new GridLayout(7, 1, 10, 10));
        card.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
        card.setBackground(new Color(255, 240, 255)); // 🎀 tono rosado suave

        // info
        card.add(crearLabel("Nombre: Yessenia Crystabel Mazariegos Carranza"));
        card.add(crearLabel("Carné: 202503403"));
        card.add(crearLabel("Correo: 3902256060108@ingenieria.usac.edu.gt"));
        card.add(crearLabel("Sección: D"));
        card.add(crearLabel("Semestre: 2026"));

        card.add(new JLabel("")); // espacio

        JLabel acerca = new JLabel("<html><center>GameZone Pro es un sistema de videojuegos<br>"
                + "con tienda, torneos, álbum de cartas y recompensas.</center></html>",
                JLabel.CENTER);
        acerca.setFont(new Font("Arial", Font.ITALIC, 14));
        acerca.setForeground(new Color(100, 50, 150));

        card.add(acerca);

        add(card, BorderLayout.CENTER);

        
        // boton menú
        
        JButton btnMenu = new JButton(" Volver al Menú");
        btnMenu.setFont(new Font("Arial", Font.BOLD, 14));
        btnMenu.setBackground(new Color(200, 150, 255));
        btnMenu.setForeground(Color.WHITE);

        btnMenu.addActionListener(e -> {
            frame.mostrar("MENU");
        });

        JPanel panelSur = new JPanel();
        panelSur.setBackground(new Color(245, 230, 255));
        panelSur.add(btnMenu);

        add(panelSur, BorderLayout.SOUTH);
    }

   
    // label bonito
    
    private JLabel crearLabel(String texto) {

        JLabel lbl = new JLabel(texto, JLabel.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        lbl.setForeground(new Color(90, 40, 140));

        return lbl;
    }
}

