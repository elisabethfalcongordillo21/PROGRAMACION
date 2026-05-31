package com.unidad4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.unidad4.utils.Db;

/**
 * =====================================================
 * EXAMEN BARCOS (Junio 23-24 Rec) → ADAPTADO A PELICULAS
 * =====================================================
 * TABLA ORIGINAL → TU BD:
 *   barco   → pelicula  (id, titulo, duracion, clasificacion, sinopsis)
 *   puerto  → director  (id, nombre) — el barco pertenece a un puerto
 *                         la pelicula pertenece a un director (id_director)
 *   eslora  → duracion  (campo numérico para filtrar)
 *
 * MENÚ ORIGINAL → ADAPTADO:
 *   1. Insertar Barco    → Insertar Película
 *   2. Mostrar Barcos    → Mostrar Películas (submenú)
 *   3. Eliminar Barco    → Eliminar Película
 *   4. Salir
 * =====================================================
 */
public class Examen_Barcos_Adaptado {

    private Connection con;

    public Examen_Barcos_Adaptado() {
        this.con = Db.conectar();
    }

    // ─────────────────────────────────────────────
    // OPCIÓN 1 – INSERTAR BARCO (Insertar Película)
    // Original: pedir datos del barco, si id existe mostrar error
    // Adaptado: insertar película con id autoincremental
    // CAMBIAR: campos según tabla del examen
    // ─────────────────────────────────────────────

    public boolean insertarPelicula(String titulo, int clasificacion, int duracion, String sinopsis, int idDirector) {
        try {
            String query = "INSERT INTO pelicula (titulo, clasificacion, duracion, sinopsis, id_director) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, titulo);
            stmt.setInt(2, clasificacion);
            stmt.setInt(3, duracion);
            stmt.setString(4, sinopsis);
            stmt.setInt(5, idDirector); // equivale a id_puerto en el examen
            int filas = stmt.executeUpdate();
            return filas == 1;
        } catch (Exception e) {
            System.out.println("Error al insertar película: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────────
    // OPCIÓN 2 – MOSTRAR BARCOS → submenú
    // En ambos casos muestra todos los datos + nombre del director (= nombre del puerto)
    // ─────────────────────────────────────────────

    /**
     * Opción 2.1: Mostrar todas ordenadas por un campo dado
     * Original: pedir campo (id, nombre o eslora) y ordenar por él
     * CAMBIAR: campos válidos (id, titulo, duracion)
     * IMPORTANTE: el campo se valida antes de usarlo para evitar SQL injection
     */
    public List<String> mostrarPeliculasOrdenadasPorCampo(String campo) {
        List<String> lista = new ArrayList<>();

        // Validamos que el campo sea uno de los permitidos
        // CAMBIAR: añadir o quitar campos según el examen
        if (!campo.equals("id") && !campo.equals("titulo") && !campo.equals("duracion")) {
            System.out.println("Campo no válido. Solo se puede ordenar por: id, titulo, duracion.");
            return lista;
        }

        try {
            // JOIN con director para mostrar su nombre (equivale al nombre del puerto)
            String query = "SELECT p.id, p.titulo, p.duracion, p.clasificacion, p.sinopsis, d.nombre AS nombre_director " +
                           "FROM pelicula p " +
                           "JOIN director d ON p.id_director = d.id " +
                           "ORDER BY p." + campo; // campo ya validado arriba
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(formatearConDirector(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar películas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Opción 2.2: Mostrar películas con duración mayor a X
     * Original: mostrar barcos con eslora mayor a X
     * CAMBIAR: campo de filtro (duracion → eslora u otro campo numérico)
     */
    public List<String> mostrarPeliculasPorDuracionSuperior(int duracionMinima) {
        List<String> lista = new ArrayList<>();
        try {
            String query = "SELECT p.id, p.titulo, p.duracion, p.clasificacion, p.sinopsis, d.nombre AS nombre_director " +
                           "FROM pelicula p " +
                           "JOIN director d ON p.id_director = d.id " +
                           "WHERE p.duracion > ?"; // CAMBIAR campo si hace falta
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, duracionMinima);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(formatearConDirector(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al filtrar películas: " + e.getMessage());
        }
        return lista;
    }

    // ─────────────────────────────────────────────
    // OPCIÓN 3 – ELIMINAR BARCO (Eliminar Película por ID)
    // Original: pedir código del barco y eliminarlo
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
    // UTILIDAD: formatear fila con JOIN de director
    // ─────────────────────────────────────────────

    private String formatearConDirector(ResultSet rs) throws Exception {
        return "[ID: " + rs.getInt("id") + "] " +
               rs.getString("titulo") +
               " | Duración: " + rs.getInt("duracion") + " min" +
               " | Clasificación: " + rs.getInt("clasificacion") +
               " | Director: " + rs.getString("nombre_director");
    }
}
