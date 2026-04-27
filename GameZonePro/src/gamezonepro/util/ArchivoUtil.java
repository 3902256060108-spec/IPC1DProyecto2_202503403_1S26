package gamezonepro.util;

import java.io.*;
import gamezonepro.model.Juego;
import gamezonepro.model.Carta;
import gamezonepro.estructuras.ListaSimple;
import gamezonepro.estructuras.NodoMatriz;
import gamezonepro.model.Torneo;
import gamezonepro.model.Usuario;

public class ArchivoUtil {

    // 🔹 CARGAR CATÁLOGO
    public static ListaSimple cargarCatalogo(String ruta) {
        ListaSimple lista = new ListaSimple();

        try {
            File file = new File("catalogo.txt");

            if (!file.exists()) {
                PrintWriter pw = new PrintWriter(file);

                pw.println("J001|FIFA 23|Deportes|350|PlayStation|10|Juego de futbol");
                pw.println("J002|Zelda|Aventura|500|Nintendo|5|Juego de aventura");
                pw.println("J003|COD|Accion|450|PC|8|Shooter");

                pw.close();
            }

            BufferedReader br = new BufferedReader(new FileReader(file));

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");

                Juego j = new Juego(
                        partes[0],
                        partes[1],
                        partes[2],
                        Double.parseDouble(partes[3]),
                        partes[4],
                        Integer.parseInt(partes[5]),
                        partes[6]
                );

                lista.agregar(j);
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // 🔥 GUARDAR HISTORIAL
    public static void guardarHistorial(ListaSimple historial) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("historial.txt"))) {

            for (int i = 0; i < historial.size(); i++) {
                Juego j = (Juego) historial.obtener(i);

                pw.println(
                        j.getId() + "|" +
                        j.getNombre() + "|" +
                        j.getCategoria() + "|" +
                        j.getPrecio() + "|" +
                        j.getPlataforma() + "|" +
                        j.getStock() + "|" +
                        j.getDescripcion()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 CARGAR HISTORIAL
    public static ListaSimple cargarHistorial() {
        ListaSimple lista = new ListaSimple();

        try {
            File file = new File("historial.txt");

            if (!file.exists()) return lista;

            BufferedReader br = new BufferedReader(new FileReader(file));

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");

                Juego j = new Juego(
                        partes[0],
                        partes[1],
                        partes[2],
                        Double.parseDouble(partes[3]),
                        partes[4],
                        Integer.parseInt(partes[5]),
                        partes[6]
                );

                lista.agregar(j);
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    
    public static ListaSimple cargarTorneos() {

    ListaSimple lista = new ListaSimple();

    try {
        File file = new File("torneos.txt");

        // 🔥 si no existe, crear datos de prueba
        if (!file.exists()) {
            PrintWriter pw = new PrintWriter(file);

            pw.println("T01|Torneo FIFA|FIFA|2026-06-01|18:00|50|5");
            pw.println("T02|Torneo COD|Call of Duty|2026-06-02|20:00|60|5");
            pw.println("T03|Torneo Smash|Smash Bros|2026-06-03|16:00|40|5");

            pw.close();
        }

        BufferedReader br = new BufferedReader(new FileReader(file));

        String linea;

        while ((linea = br.readLine()) != null) {

            String[] p = linea.split("\\|");

            Torneo t = new Torneo(
                    p[0],
                    p[1],
                    p[2],
                    p[3],
                    p[4],
                    Double.parseDouble(p[5]),
                    Integer.parseInt(p[6])
            );

            lista.agregar(t);
        }

        br.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}
    
    public static ListaSimple cargarTickets() {
    ListaSimple lista = new ListaSimple();

    try {
        File file = new File("tickets_vendidos.txt");

        if (!file.exists()) return lista;

        BufferedReader br = new BufferedReader(new FileReader(file));

        String linea;
        while ((linea = br.readLine()) != null) {
            lista.agregar(linea);
        }

        br.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}
    
    
    public static ListaSimple cargarCartas() {
    ListaSimple lista = new ListaSimple();

    lista.agregar(new Carta("C001","Pikachu","Electrico","Raro",50,30,100,""));
    lista.agregar(new Carta("C002","Charizard","Fuego","Epico",80,60,120,""));
    lista.agregar(new Carta("C003","Bulbasaur","Planta","Comun",40,40,90,""));
    lista.agregar(new Carta("C004","Squirtle","Agua","Comun",35,50,95,""));
    lista.agregar(new Carta("C005","Mewtwo","Psiquico","Legendario",100,80,150,""));

    return lista;
}
    
  public static ListaSimple cargarLeaderboard() {

    ListaSimple lista = new ListaSimple();

    try {
        File file = new File("leaderboard.txt");

        // 🔥 CREAR SI NO EXISTE
        if (!file.exists()) {

            PrintWriter pw = new PrintWriter(file);

            pw.println("Juan|1200");
            pw.println("Maria|800");
            pw.println("Pedro|3500");
            pw.println("Luis|200");
            pw.println("Ana|7000");

            pw.close();
        }

        BufferedReader br = new BufferedReader(new FileReader(file));

        String linea;
        while ((linea = br.readLine()) != null) {

            String[] p = linea.split("\\|");

            Usuario u = new Usuario(p[0]);
            u.agregarXP(Integer.parseInt(p[1]));

            lista.agregar(u);
        }

        br.close();

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}
    
    

    // 🔥 GUARDAR ÁLBUM
    public static void guardarAlbum(NodoMatriz inicio, int filas, int columnas) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("album.txt"))) {

            NodoMatriz actual = inicio;

            for (int i = 0; i < filas; i++) {

                NodoMatriz fila = actual;

                for (int j = 0; j < columnas; j++) {

                    if (fila.getDato() != null) {
                        Carta c = fila.getDato();

                        pw.println(
                              c.getCodigo() + "|" +
                              c.getNombre() + "|" +
                              c.getTipo() + "|" +
                              c.getRareza() + "|" +
                              c.getAtaque() + "|" +
                              c.getDefensa() + "|" +
                              c.getVida() + "|" +
                              c.getImagen()
                          );
                        
                    } else {
                        pw.println("null");
                    }

                    fila = fila.derecha;
                }

                actual = actual.abajo;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
public static void cargarAlbum(NodoMatriz inicio, int filas, int columnas) {

    try {
        File file = new File("album.txt");

        if (!file.exists()) return;

        BufferedReader br = new BufferedReader(new FileReader(file));

        NodoMatriz actual = inicio;

        for (int i = 0; i < filas; i++) {

            NodoMatriz fila = actual;

            for (int j = 0; j < columnas; j++) {

                String linea = br.readLine();

                if (linea == null) break;

                if (!linea.equals("null")) {

                    String[] p = linea.split("\\|");

                    // 🔥 VALIDACIÓN IMPORTANTE
                    if (p.length >= 7) {

                        Carta c = new Carta(
                                p[0],
                                p[1],
                                p[2],
                                p[3],
                                Integer.parseInt(p[4]),
                                Integer.parseInt(p[5]),
                                Integer.parseInt(p[6]),
                                (p.length > 7 ? p[7] : "")
                        );

                        fila.setDato(c);
                    }
                }

                fila = fila.derecha;
            }

            actual = actual.abajo;
        }

        br.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void guardarTickets(ListaSimple lista) {
    try (PrintWriter pw = new PrintWriter(new FileWriter("tickets_vendidos.txt"))) {

        for (int i = 0; i < lista.size(); i++) {
            pw.println(lista.obtener(i));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}



private static String generarNombre(String tipo) {

    java.time.LocalDateTime ahora = java.time.LocalDateTime.now();

    java.time.format.DateTimeFormatter formato =
            java.time.format.DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");

    return formato.format(ahora) + "_" + tipo + ".html";
}

private static void abrirArchivo(File file) {
    try {
        java.awt.Desktop.getDesktop().browse(file.toURI());
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void generarReporteInventario(ListaSimple juegos) {

    String nombre = generarNombre("Inventario");

    try {
        FileWriter fw = new FileWriter(nombre);

        fw.write("""
        <html><head><style>
        body { font-family: Arial; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid black; padding: 8px; text-align: center; }
        th { background: #333; color: white; }
        </style></head><body>
        <h2>Inventario de Tienda</h2>
        <table>
        <tr><th>Nombre</th><th>Precio</th><th>Stock</th><th>Plataforma</th></tr>
        """);

        for (int i = 0; i < juegos.size(); i++) {
            Juego j = (Juego) juegos.obtener(i);

            fw.write("<tr>");
            fw.write("<td>" + j.getNombre() + "</td>");
            fw.write("<td>Q" + j.getPrecio() + "</td>");
            fw.write("<td>" + j.getStock() + "</td>");
            fw.write("<td>" + j.getPlataforma() + "</td>");
            fw.write("</tr>");
        }

        fw.write("</table></body></html>");
        fw.close();

        abrirArchivo(new File(nombre));

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void generarReporteVentas(ListaSimple historial) {

    String nombre = generarNombre("Ventas");

    try {
        FileWriter fw = new FileWriter(nombre);

        fw.write("""
        <html><head><style>
        body { font-family: Arial; }
        </style></head><body>
        <h2>Reporte de Ventas</h2>
        <ul>
        """);

        for (int i = 0; i < historial.size(); i++) {
            Juego j = (Juego) historial.obtener(i);

            fw.write("<li>" + j.getNombre() + " - Q" + j.getPrecio() + "</li>");
        }

        fw.write("</ul></body></html>");
        fw.close();

        abrirArchivo(new File(nombre));

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void generarReporteAlbum(NodoMatriz inicio, int filas, int columnas) {

    String nombre = generarNombre("Album");

    try {
        FileWriter fw = new FileWriter(nombre);

        fw.write("""
        <html><head><style>
        table { border-collapse: collapse; }
        td { width: 100px; height: 100px; text-align: center; }
        .vacio { background: gray; }
        .legendario { background: gold; }
        </style></head><body>
        <h2>Álbum</h2>
        <table>
        """);

        NodoMatriz fila = inicio;

        for (int i = 0; i < filas; i++) {

            fw.write("<tr>");
            NodoMatriz col = fila;

            for (int j = 0; j < columnas; j++) {

                if (col.getDato() == null) {
                    fw.write("<td class='vacio'>Vacío</td>");
                } else {
                    Carta c = col.getDato();

                    if (c.getRareza().equalsIgnoreCase("Legendario")) {
                        fw.write("<td class='legendario'>" + c.getNombre() + "</td>");
                    } else {
                        fw.write("<td>" + c.getNombre() + "</td>");
                    }
                }

                col = col.derecha;
            }

            fw.write("</tr>");
            fila = fila.abajo;
        }

        fw.write("</table></body></html>");
        fw.close();

        abrirArchivo(new File(nombre));

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void generarReporteTorneos(ListaSimple torneos, ListaSimple tickets) {

    String nombre = generarNombre("Torneos");

    try {
        FileWriter fw = new FileWriter(nombre);

        fw.write("""
        <html><head><style>
        body { font-family: Arial; }
        </style></head><body>
        <h2>Torneos</h2>
        <ul>
        """);

        for (int i = 0; i < torneos.size(); i++) {
            Torneo t = (Torneo) torneos.obtener(i);

            fw.write("<li>" + t.getNombre() + " - " + t.getJuego() + "</li>");
        }

        fw.write("</ul><h2>Tickets vendidos</h2><ul>");

        for (int i = 0; i < tickets.size(); i++) {
            fw.write("<li>" + tickets.obtener(i) + "</li>");
        }

        fw.write("</ul></body></html>");
        fw.close();

        abrirArchivo(new File(nombre));

    } catch (Exception e) {
        e.printStackTrace();
    }
}





    
}