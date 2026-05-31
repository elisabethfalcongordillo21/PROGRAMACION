package com.unidad4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.unidad4.utils.Db;

/**
 * =====================================================
 * EXAMEN AEROPUERTO (Junio 23-24) → ADAPTADO A PELICULAS
 * =====================================================
 * TABLA ORIGINAL → TU BD:
 *   pasajero      → actor       (id, nombre, nacionalidad, fec_nac)
 *   maleta        → pelicula    (id, titulo, duracion, clasificacion, sinopsis)
 *   pasajero_id   → actor_id en actor_has_pelicula
 *
 * MENÚ ORIGINAL → ADAPTADO:
 *   1. Dar de Alta Pasajero     → Añadir Actor
 *   2. Añadir Maletas           → Añadir Películas a un Actor
 *   3. Mostrar Maletas          → Mostrar Películas de un Actor
 *   4. Eliminar Maletas         → Eliminar Películas de un Actor
 *   5. Salir
 * =====================================================
 */
public class Examen_Aeropuerto_Adaptado {

    private Connection con;

    public Examen_Aeropuerto_Adaptado() {
        this.con = Db.conectar();
    }

    // ─────────────────────────────────────────────
    // OPCIÓN 1 – DAR DE ALTA (Añadir Actor)
    // Original: pedir datos de pasajero, comprobar DNI no repetido
    // Adaptado: pedir datos de actor, el id es autoincremental
    // CAMBIAR: campos según tabla (nombre, nacionalidad, fec_nac)
    // ─────────────────────────────────────────────

    public boolean añadirActor(String nombre, String nacionalidad, String fec_nac) {
        try {
            // Comprobamos que no exista ya un actor con ese nombre (equivale a comprobar DNI)
            if (existeActor(nombre)) {
                System.out.println("Ya existe un actor con ese nombre.");
                return false;
            }

            String query = "INSERT INTO actor (nombre, nacionalidad, fec_nac) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, nombre);
            stmt.setString(2, nacionalidad);
            stmt.setString(3, fec_nac); // formato: "1990-05-20"
            int filas = stmt.executeUpdate();
            return filas == 1;
        } catch (Exception e) {
            System.out.println("Error al añadir actor: " + e.getMessage());
            return false;
        }
    }

    // Método auxiliar para comprobar si ya existe (equivale a comprobar DNI duplicado)
    private boolean existeActor(String nombre) {
        try {
            String query = "SELECT id FROM actor WHERE LOWER(nombre) = LOWER(?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // si hay resultado, ya existe
        } catch (Exception e) {
            return false;
        }
    }

    // ─────────────────────────────────────────────
    // OPCIÓN 2 – AÑADIR MALETAS (Añadir películas a un actor)
    // Original: pedir id pasajero y cuántas maletas, meterlas de 1 en 1
    // Adaptado: pedir id actor y asociarle películas existentes de 1 en 1
    // CAMBIAR: si quieres insertar películas nuevas en vez de asociar existentes
    // ─────────────────────────────────────────────

    public boolean asociarPeliculaActor(int actorId, int peliculaId, String personaje) {
        try {
            String query = "INSERT INTO actor_has_pelicula (actor_id, pelicula_id, personaje) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, actorId);
            stmt.setInt(2, peliculaId);
            stmt.setString(3, personaje);
            int filas = stmt.executeUpdate();
            return filas == 1;
        } catch (Exception e) {
            System.out.println("Error al asociar película al actor: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────────
    // OPCIÓN 3 – MOSTRAR MALETAS → submenú
    // ─────────────────────────────────────────────

    /**
     * Opción 3.1: Mostrar películas de un actor ordenadas por duración DESC
     * Original: maletas de un pasajero ordenadas por tamaño de mayor a menor
     * CAMBIAR: el campo de orden (duracion → el campo que pida el examen)
     */
    public List<String> mostrarPeliculasActorOrdenadas(String nombreActor) {
        List<String> lista = new ArrayList<>();
        try {
            String query = "SELECT p.id, p.titulo, p.duracion, p.clasificacion, p.sinopsis, " +
                           "a.nombre, a.nacionalidad " +
                           "FROM pelicula p " +
                           "JOIN actor_has_pelicula ahp ON p.id = ahp.pelicula_id " +
                           "JOIN actor a ON ahp.actor_id = a.id " +
                           "WHERE LOWER(a.nombre) = LOWER(?) " +
                           "ORDER BY p.duracion DESC"; // CAMBIAR campo de orden si hace falta
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, nombreActor);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String linea = "[ID: " + rs.getInt("id") + "] " +
                               rs.getString("titulo") +
                               " | Duración: " + rs.getInt("duracion") + " min" +
                               " | Clasificación: " + rs.getInt("clasificacion") +
                               " | Actor: " + rs.getString("nombre") +
                               " (" + rs.getString("nacionalidad") + ")";
                lista.add(linea);
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar películas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Opción 3.2: Mostrar películas de un actor con duración mayor a X
     * Original: maletas de un pasajero que pesen más de X
     * CAMBIAR: el campo de filtro (duracion → peso u otro campo numérico)
     */
    public List<String> mostrarPeliculasActorPorDuracion(String nombreActor, int duracionMinima) {
        List<String> lista = new ArrayList<>();
        try {
            String query = "SELECT p.id, p.titulo, p.duracion, p.clasificacion, p.sinopsis, " +
                           "a.nombre, a.nacionalidad " +
                           "FROM pelicula p " +
                           "JOIN actor_has_pelicula ahp ON p.id = ahp.pelicula_id " +
                           "JOIN actor a ON ahp.actor_id = a.id " +
                           "WHERE LOWER(a.nombre) = LOWER(?) " +
                           "AND p.duracion > ?"; // CAMBIAR campo de filtro si hace falta
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, nombreActor);
            stmt.setInt(2, duracionMinima);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String linea = "[ID: " + rs.getInt("id") + "] " +
                               rs.getString("titulo") +
                               " | Duración: " + rs.getInt("duracion") + " min" +
                               " | Actor: " + rs.getString("nombre");
                lista.add(linea);
            }
        } catch (Exception e) {
            System.out.println("Error al filtrar películas: " + e.getMessage());
        }
        return lista;
    }

    // ─────────────────────────────────────────────
    // OPCIÓN 4 – ELIMINAR MALETAS (Eliminar películas de un actor)
    // Original: pedir id cliente y eliminar sus maletas
    // Adaptado: pedir id actor y eliminar sus relaciones en actor_has_pelicula
    // ─────────────────────────────────────────────

    public boolean eliminarPeliculasDeActor(int actorId) {
        try {
            String query = "DELETE FROM actor_has_pelicula WHERE actor_id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, actorId);
            int filas = stmt.executeUpdate();
            if (filas == 0) {
                System.out.println("No se encontraron películas para el actor con ID " + actorId);
                return false;
            }
            System.out.println("Se eliminaron " + filas + " película(s) del actor.");
            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar películas: " + e.getMessage());
            return false;
        }
    }
}
