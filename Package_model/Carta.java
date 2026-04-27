package gamezonepro.model;
public class Carta {

    private String codigo;
    private String nombre;
    private String tipo;
    private String rareza;
    private int ataque;
    private int defensa;
    private int vida;
    private String imagen;

    public Carta(String codigo, String nombre, String tipo, String rareza,
                 int ataque, int defensa, int vida, String imagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.rareza = rareza;
        this.ataque = ataque;
        this.defensa = defensa;
        this.vida = vida;
        this.imagen = imagen;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public String getRareza() { return rareza; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getVida() { return vida; }
    public String getImagen() { return imagen; }
}
