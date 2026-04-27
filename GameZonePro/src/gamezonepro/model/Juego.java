package gamezonepro.model;

public class Juego {
    private String id;
    private String nombre;
    private String categoria;
    private double precio;
    private String plataforma;
    private int stock;
    private String descripcion;

    public Juego(String id, String nombre, String categoria,
                 double precio, String plataforma, int stock, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.plataforma = plataforma;
        this.stock = stock;
        this.descripcion = descripcion;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecio() { return precio; }
    public String getPlataforma() { return plataforma; }
    public int getStock() { return stock; }
    public String getDescripcion() { return descripcion; }
}