import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
 
import com.unidad4.utils.Db;
 
/**
 * DAO para gestionar la tabla critico en la base de datos.
 * Todos los métodos devuelven List<CriticoDO> o valores simples.
 * Se usan PreparedStatement para todas las consultas con parámetros.
 */
public class CriticoDAO {
 
    private Connection con;
 
    public CriticoDAO() {
        // Nos conectamos a la BD al crear el objeto
        this.con = Db.conectar();
    }
 
    // ─────────────────────────────────────────────
    // AÑADIR crítico
    // ─────────────────────────────────────────────
 
    /**
     * Inserta un nuevo crítico en la BD.
     * @return true si se insertó correctamente, false si falló
     */
    public boolean añadirCritico(String nombre, String medio, int anyo_inicio) {
        try {
            String query = "INSERT INTO critico (nombre, medio, anyo_inicio) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, nombre);
            stmt.setString(2, medio);
            stmt.setInt(3, anyo_inicio);
            int filas = stmt.executeUpdate();
            return filas == 1;
        } catch (Exception e) {
            System.out.println("Error al añadir el crítico: " + e.getMessage());
            return false;
        }
    }
 
    // ─────────────────────────────────────────────
    // MODIFICAR crítico
    // ─────────────────────────────────────────────
 
    /**
     * Busca un crítico por su ID y lo devuelve, o null si no existe.
     */
    public CriticoDO buscarPorId(int id) {
        try {
            String query = "SELECT * FROM critico WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new CriticoDO(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("medio"),
                    rs.getInt("anyo_inicio")
                );
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el crítico: " + e.getMessage());
        }
        return null;
    }
 
    /**
     * Modifica un campo concreto de un crítico por su ID.
     * @param id        ID del crítico
     * @param campo     nombre del campo a modificar (nombre, medio, anyo_inicio)
     * @param valor     nuevo valor en String (se convierte si hace falta)
     * @return true si se modificó correctamente
     */
    public boolean modificarCritico(int id, String campo, String valor) {
        try {
            String query = "UPDATE critico SET " + campo + " = ? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
 
            // Si el campo es anyo_inicio lo guardamos como entero
            if (campo.equals("anyo_inicio")) {
                stmt.setInt(1, Integer.parseInt(valor));
            } else {
                stmt.setString(1, valor);
            }
 
            stmt.setInt(2, id);
            int filas = stmt.executeUpdate();
            return filas == 1;
        } catch (NumberFormatException e) {
            System.out.println("El año de inicio debe ser un número entero.");
            return false;
        } catch (Exception e) {
            System.out.println("Error al modificar el crítico: " + e.getMessage());
            return false;
        }
    }
 
    // ─────────────────────────────────────────────
    // CONSULTAR críticos
    // ─────────────────────────────────────────────
 
    /**
     * Devuelve todos los críticos ordenados por nombre A-Z.
     */
    public List<CriticoDO> obtenerTodosOrdenadosPorNombre() {
        List<CriticoDO> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM critico ORDER BY nombre ASC";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new CriticoDO(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("medio"),
                    rs.getInt("anyo_inicio")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error al consultar críticos: " + e.getMessage());
        }
        return lista;
    }
 
    /**
     * Devuelve todos los críticos ordenados por año de inicio, de mayor a menor.
     */
    public List<CriticoDO> obtenerTodosOrdenadosPorAnyo() {
        List<CriticoDO> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM critico ORDER BY anyo_inicio DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new CriticoDO(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("medio"),
                    rs.getInt("anyo_inicio")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error al consultar críticos: " + e.getMessage());
        }
        return lista;
    }
 
    /**
     * Devuelve todos los críticos que escriben en el medio indicado (sin distinguir mayúsculas).
     */
    public List<CriticoDO> obtenerPorMedio(String medio) {
        List<CriticoDO> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM critico WHERE LOWER(medio) = LOWER(?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, medio);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new CriticoDO(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("medio"),
                    rs.getInt("anyo_inicio")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar por medio: " + e.getMessage());
        }
        return lista;
    }
 
    /**
     * Devuelve las películas reseñadas por un crítico buscado por nombre.
     * Usa la tabla critico_has_pelicula para hacer el JOIN con pelicula.
     * Devuelve lista de Strings con: id, titulo, duracion, clasificacion, puntuacion.
     */
    public List<String> obtenerResenyas(String nombreCritico) {
        List<String> lista = new ArrayList<>();
        try {
            String query = "SELECT p.id, p.titulo, p.duracion, p.clasificacion, chp.puntuacion " +
                           "FROM critico_has_pelicula chp " +
                           "JOIN pelicula p ON chp.pelicula_id = p.id " +
                           "JOIN critico c ON chp.critico_id = c.id " +
                           "WHERE LOWER(c.nombre) = LOWER(?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, nombreCritico);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String linea = "[ID: " + rs.getInt("id") + "] " +
                               rs.getString("titulo") +
                               " | Duración: " + rs.getInt("duracion") + " min" +
                               " | Clasificación: " + rs.getInt("clasificacion") +
                               " | Puntuación: " + rs.getInt("puntuacion") + "/10";
                lista.add(linea);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener reseñas: " + e.getMessage());
        }
        return lista;
    }
 
    /**
     * Devuelve el número de críticos que escriben en el medio indicado.
     */
    public int contarCriticosPorMedio(String medio) {
        try {
            String query = "SELECT COUNT(*) AS total FROM critico WHERE LOWER(medio) = LOWER(?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, medio);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Error al contar críticos: " + e.getMessage());
        }
        return 0;
    }
 
    // ─────────────────────────────────────────────
    // ELIMINAR crítico
    // ─────────────────────────────────────────────
 
    /**
     * Elimina un crítico por su ID.
     * Si tiene reseñas en critico_has_pelicula, la FK lo impedirá y se captura el error.
     * @return true si se eliminó, false si falló o no existe
     */
    public boolean eliminarCritico(int id) {
        try {
            String query = "DELETE FROM critico WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            if (filas == 0) {
                System.out.println("No existe ningún crítico con ID " + id);
                return false;
            }
            return true;
        } catch (Exception e) {
            // Si la excepción es por FK (tiene reseñas asociadas en critico_has_pelicula)
            if (e.getMessage() != null && e.getMessage().contains("foreign key")) {
                System.out.println("No se puede eliminar: el crítico tiene reseñas asociadas en la BD.");
            } else {
                System.out.println("Error al eliminar el crítico: " + e.getMessage());
            }
            return false;
        }
    }
}