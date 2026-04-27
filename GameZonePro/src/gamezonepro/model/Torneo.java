package gamezonepro.model;

public class Torneo {

    private String id;
    private String nombre;
    private String juego;
    private String fecha;
    private String hora;
    private double precio;
    private int ticketsDisponibles;

    public Torneo(String id, String nombre, String juego,
                  String fecha, String hora,
                  double precio, int ticketsDisponibles) {

        this.id = id;
        this.nombre = nombre;
        this.juego = juego;
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.ticketsDisponibles = ticketsDisponibles;
    }

    // getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getJuego() { return juego; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public double getPrecio() { return precio; }
    public int getTicketsDisponibles() { return ticketsDisponibles; }

    public void setTicketsDisponibles(int t) {
        this.ticketsDisponibles = t;
    }

    @Override
    public String toString() {
        return nombre + " - " + juego + " (" + fecha + ")";
    }
    
   
}