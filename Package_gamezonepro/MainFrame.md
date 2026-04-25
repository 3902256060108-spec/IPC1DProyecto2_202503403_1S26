```java
package gamezonepro;
import gamezonepro.controllers.AlbumController;
import gamezonepro.controllers.RecompensasController;
import gamezonepro.controllers.TiendaController;
import gamezonepro.controllers.TorneoController;
import gamezonepro.gui.panels.*;
import gamezonepro.util.ArchivoUtil;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends javax.swing.JFrame {
    private JPanel contenedor;
    private CardLayout card;
    private TiendaController controller;
    private RecompensasController recompensasController;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainFrame.class.getName());

   
    public MainFrame() {
        initComponents();
        
          card = new CardLayout();
    contenedor = new JPanel(card);
    
     controller = new TiendaController();
     recompensasController = new RecompensasController("Jugador1");

    
    addWindowListener(new java.awt.event.WindowAdapter() {
    public void windowClosing(java.awt.event.WindowEvent e) {
        ArchivoUtil.guardarHistorial(controller.getHistorial());
    }
});

    this.setContentPane(contenedor);
    
        
    contenedor.add(new MenuPanel(this), "MENU");
    contenedor.add(new TiendaPanel(this, controller, recompensasController), "TIENDA"); 
    contenedor.add(new CarritoPanel(this, controller,recompensasController), "CARRITO");
    contenedor.add(new AlbumPanel(this,  recompensasController), "ALBUM");
    contenedor.add(new TorneosPanel(this,  recompensasController), "TORNEOS");
    contenedor.add(new RecompensasPanel(this, recompensasController), "RECOMPENSAS");
    contenedor.add(new ReportesPanel(this, controller, new TorneoController(), new AlbumController()), "REPORTES");
    contenedor.add(new EstudiantePanel(this), "ESTUDIANTE");
    
    card.show(contenedor, "MENU");
    
    }
    
    //Actualizar los paneles al entrar 
   public void mostrar(String nombre) {
       
       
       if (nombre.equals("RECOMPENSAS")) {
    for (Component comp : contenedor.getComponents()) {
        if (comp instanceof RecompensasPanel) {
            ((RecompensasPanel) comp).actualizar();
        }
    }
}

    if (nombre.equals("CARRITO")) {
        for (Component comp : contenedor.getComponents()) {
            if (comp instanceof CarritoPanel) {
                ((CarritoPanel) comp).cargarCarrito();
            }
        }
    }
}

    card.show(contenedor, nombre);
}
