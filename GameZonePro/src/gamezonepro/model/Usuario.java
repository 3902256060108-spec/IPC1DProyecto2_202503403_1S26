package gamezonepro.model;

public class Usuario {

    private String nombre;
    private int xp;

    public Usuario(String nombre) {
        this.nombre = nombre;
        this.xp = 0;
    }

    public String getNombre() { return nombre; }
    public int getXp() { return xp; }

    public void agregarXP(int cantidad) {
        xp += cantidad;
    }

    public int getNivel() {

        if (xp >= 7000) return 5;
        if (xp >= 3500) return 4;
        if (xp >= 1500) return 3;
        if (xp >= 500) return 2;
        return 1;
    }

    public String getRango() {

        switch (getNivel()) {
            case 1: return "Aprendiz";
            case 2: return "Jugador";
            case 3: return "Veterano";
            case 4: return "Maestro";
            default: return "Leyenda";
        }
    }
}