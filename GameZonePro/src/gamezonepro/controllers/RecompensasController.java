package gamezonepro.controllers;

import gamezonepro.estructuras.ListaSimple;
import gamezonepro.model.Usuario;
import gamezonepro.model.Logro;

import javax.swing.JOptionPane;

public class RecompensasController {

    private Usuario usuario;
    private ListaSimple logros;

    // Contadores
    private int compras = 0;
    private int cartas = 0;
    private int torneos = 0;
    private double dineroGastado = 0;

    public RecompensasController(String nombreUsuario) {
        usuario = new Usuario(nombreUsuario);
        logros = new ListaSimple();

        cargarLogros();
    }

    
    // Cargar logros
    
    private void cargarLogros() {

        logros.agregar(new Logro("Primera Compra", "Compra tu primer juego"));
        logros.agregar(new Logro("Coleccionista Novato", "10 cartas"));
        logros.agregar(new Logro("Coleccionista Experto", "Completar fila"));
        logros.agregar(new Logro("Taquillero", "3 torneos"));
        logros.agregar(new Logro("Alta Rareza", "Carta legendaria"));
        logros.agregar(new Logro("Gamer Dedicado", "1000 XP"));
        logros.agregar(new Logro("Leyenda Viviente", "Nivel 5"));
        logros.agregar(new Logro("Gran Gastador", "Q2000 gastados"));
    }

    
    // XP
    
    public void sumarXP(int xp) {
        usuario.agregarXP(xp);
        verificarLogros(); // metodo 
    }

    
    // Acciones
    

    // compra de juego
    public void registrarCompra(double monto) {
        compras++;
        dineroGastado += monto;

        sumarXP(50);

        if (compras >= 1) {
            desbloquear("Primera Compra");
        }

        if (dineroGastado >= 2000) {
            desbloquear("Gran Gastador");
        }
    }

    // carta
    public void registrarCarta(String rareza) {
        cartas++;

        sumarXP(20); //  XP base por carta

        if (cartas >= 10) {
            desbloquear("Coleccionista Novato");
        }

        if (rareza.equalsIgnoreCase("Legendario")) {
            sumarXP(200);
            desbloquear("Alta Rareza");
        }
    }

    // fila completa
    public void registrarFilaCompleta() {
        sumarXP(100);
        desbloquear("Coleccionista Experto");
    }

    // torneo
    public void registrarTorneo() {
        torneos++;

        sumarXP(150);

        if (torneos >= 3) {
            desbloquear("Taquillero");
        }
    }

    
    // Verificar logros automáticos
    
    private void verificarLogros() {

        if (usuario.getXp() >= 1000) {
            desbloquear("Gamer Dedicado");
        }

        if (usuario.getNivel() >= 5) {
            desbloquear("Leyenda Viviente");
        }
    }

    
    // desbloquear logro
    
    private void desbloquear(String nombre) {

        for (int i = 0; i < logros.size(); i++) {

            Logro l = (Logro) logros.obtener(i);

            if (l.getNombre().equals(nombre) && !l.isDesbloqueado()) {

                l.setDesbloqueado(true);

                
                JOptionPane.showMessageDialog(
                        null,
                        "🏆 Logro desbloqueado: " + nombre
                );
            }
        }
    }

    
    // getters
    
    public Usuario getUsuario() {
        return usuario;
    }

    public ListaSimple getLogros() {
        return logros;
    }
}
