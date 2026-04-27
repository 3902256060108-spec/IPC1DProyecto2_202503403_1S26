package gamezonepro.model;

public class Logro {

    private String nombre;
    private String descripcion;
    private boolean desbloqueado;

    public Logro(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.desbloqueado = false;
    }

    // getters
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isDesbloqueado() {
        return desbloqueado;
    }

    
    public void setDesbloqueado(boolean desbloqueado) {
        this.desbloqueado = desbloqueado;
    }
}
