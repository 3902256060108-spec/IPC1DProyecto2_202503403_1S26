
package gamezonepro;
import gamezonepro.estructuras.ListaSimple;

public class GameZonePro {

    
    public static void main(String[] args) {
        
         ListaSimple lista = new ListaSimple();
    lista.agregar("Hola");
    lista.agregar("Mundo");

    System.out.println(lista.size()); 

    java.awt.EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }
    
}
