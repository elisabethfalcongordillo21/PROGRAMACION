package com.unidad4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.unidad4.utils.Db;

/**
 * =====================================================
 * EXAMEN PHONESTORAGE (24-25) → ADAPTADO A PELICULAS
 * =====================================================
 * TABLA ORIGINAL → TU BD:
 *   phones  → pelicula (id, titulo, duracion, clasificacion, sinopsis)
 *   marca   → clasificacion (campo para filtrar, tiene varios valores)
 *   nombre  → titulo
 *   precio  → duracion (campo numérico para ordenar)
 *
 * MENÚ ORIGINAL → ADAPTADO:
 *   1. Introducir Datos de Terminal  → Añadir Película
 *   2. Modificar Terminal            → Modificar Película
 *   3. Mostrar Terminales            → Mostrar Películas (submenú)
 *   4. Eliminar Terminal             → Eliminar Película
 *   5. Salir
 * =====================================================
 */
public class Examen_PhoneStorage_Adaptado {

    private Connection con;

    public Examen_PhoneStorage_Adaptado() {
        this.con = Db.conectar();
    }

    // ─────────────────────────────────────────────
    // OPCIÓN 1 – INTRODUCIR DATOS (Añadir Película)
    // Original: pedir datos de phone, si el id ya existe mostrar error
    // Adaptado: insertar película, id es autoincremental
    // CAMBIAR: campos según lo que pida el examen
    // ─────────────────────────────────────────────

    public boolean añadirPelicula(String titulo, int clasificacion, int duracion, String sinopsis) {
        try {
            String query = "INSERT INTO pelicula (titulo, clasificacion, duracion, sinopsis) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, titulo);
            stmt.setInt(2, clasificacion);
            stmt.setInt(3, duracion);
            stmt.setString(4, sinopsis);
            int filas = stmt.executeUpdate();
            return filas == 1;
        } catch (Exception e) {
            System.out.println("Error al añadir película: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────────
    // OPCIÓN 2 – MODIFICAR (Modificar Película)
    // Original: pedir id, mostrar datos, pedir campo y nuevo valor
    // Adaptado: igual pero con pelicula
    // CAMBIAR: campos válidos según tabla del examen
    // ─────────────────────────────────────────────

    public Object[] buscarPeliculaPorId(int id) {
        try {
            String query = "SELECT * FROM pelicula WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Object[]{
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getInt("clasificacion"),
                    rs.getInt("duracion"),
                    rs.getString("sinopsis")
                };
            }
        } catch (Exception e) {
            System.out.println("Error al buscar película: " + e.getMessage());
        }
        return null;
    }

    public boolean modificarPelicula(int id, String campo, String nuevoValor) {
        try {
            String query = "UPDATE pelicula SET " + campo + " = ? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);

            // Si el campo es numérico lo parseamos como entero
            // CAMBIAR: añadir más campos numéricos si los hay en tu examen
            if (campo.equals("duracion") || campo.equals("clasificacion")) {
                stmt.setInt(1, Integer.parseInt(nuevoValor));
            } else {
                stmt.setString(1, nuevoValor);
            }

            stmt.setInt(2, id);
            int filas = stmt.executeUpdate();
            return filas == 1;
        } catch (NumberFormatException e) {
            System.out.println("El valor introducido no es un número válido.");
            return false;
        } catch (Exception e) {
            System.out.println("Error al modificar: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────────
    // OPCIÓN 3 – MOSTRAR → submenú
    // ─────────────────────────────────────────────

    /**
     * Opción 3.1: Mostrar todas ordenadas por título DESC
     * Original: mostrar todas ordenadas por nombre DESC
     * CAMBIAR: campo de orden (titulo → nombre en tu examen)
     */
    public List<String> mostrarTodasOrdenadasPorTitulo() {
        List<String> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM pelicula ORDER BY titulo DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(formatearPelicula(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar películas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Opción 3.2: Mostrar todas ordenadas por duración DESC
     * Original: mostrar todas ordenadas por precio DESC
     * CAMBIAR: campo de orden (duracion → precio u otro campo numérico)
     */
    public List<String> mostrarTodasOrdenadasPorDuracion() {
        List<String> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM pelicula ORDER BY duracion DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(formatearPelicula(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar películas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Opción 3.3: Mostrar por clasificación (equivale a filtrar por marca)
     * Original: mostrar los de Marca X (sin distinguir mayúsculas)
     * CAMBIAR: clasificacion → el campo de filtro de tu examen
     */
    public List<String> mostrarPorClasificacion(int clasificacion) {
        List<String> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM pelicula WHERE clasificacion = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, clasificacion);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(formatearPelicula(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al filtrar películas: " + e.getMessage());
        }
        return lista;
    }

    // ─────────────────────────────────────────────
    // OPCIÓN 4 – ELIMINAR (Eliminar Película por ID)
    // Original: pedir id del terminal y eliminarlo
    // ─────────────────────────────────────────────

    public boolean eliminarPelicula(int id) {
        try {
            String query = "DELETE FROM pelicula WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            if (filas == 0) {
                System.out.println("No existe ninguna película con ID " + id);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar la película: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────────
    // UTILIDAD: formatear una fila del ResultSet
    // ─────────────────────────────────────────────

    private String formatearPelicula(ResultSet rs) throws Exception {
        return "[ID: " + rs.getInt("id") + "] " +
               rs.getString("titulo") +
               " | Duración: " + rs.getInt("duracion") + " min" +
               " | Clasificación: " + rs.getInt("clasificacion") +
               " | Sinopsis: " + rs.getString("sinopsis");
    }
}
