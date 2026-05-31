
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.unidad4.utils.Db;

/**
 * DAO para gestionar la tabla director en la base de datos.
 * Todos los métodos usan PreparedStatement para consultas con parámetros.
 * Los resultados se devuelven como List<DirectorDO>.
 */
public class DirectorDAO {

    private Connection con;

    public DirectorDAO() {
        // Conexión a BD al crear el objeto
        this.con = Db.conectar();
    }

    // ─────────────────────────────────────────────
    // AÑADIR director
    // ─────────────────────────────────────────────

    /**
     * Inserta un nuevo director en la BD.
     * @return true si se insertó correctamente, false si falló
     */
    public boolean añadirDirector(String nombre, String nacionalidad, int anyo_nacimiento) {
        try {
            String query = "INSERT INTO director (nombre, nacionalidad, anyo_nacimiento) VALUES (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, nombre);
            stmt.setString(2, nacionalidad);
            stmt.setInt(3, anyo_nacimiento);
            int filas = stmt.executeUpdate();
            return filas == 1;
        } catch (Exception e) {
            System.out.println("Error al añadir el director: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────────
    // MODIFICAR director
    // ─────────────────────────────────────────────

    /**
     * Busca un director por su ID. Devuelve null si no existe.
     */
    public DirectorDO buscarPorId(int id) {
        try {
            String query = "SELECT * FROM director WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new DirectorDO(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("nacionalidad"),
                    rs.getInt("anyo_nacimiento")
                );
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el director: " + e.getMessage());
        }
        return null;
    }

    /**
     * Modifica un campo concreto de un director por su ID.
     * @param id     ID del director
     * @param campo  nombre del campo (nombre, nacionalidad, anyo_nacimiento)
     * @param valor  nuevo valor como String
     * @return true si se modificó correctamente
     */
    public boolean modificarDirector(int id, String campo, String valor) {
        try {
            String query = "UPDATE director SET " + campo + " = ? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);

            if (campo.equals("anyo_nacimiento")) {
                stmt.setInt(1, Integer.parseInt(valor));
            } else {
                stmt.setString(1, valor);
            }

            stmt.setInt(2, id);
            int filas = stmt.executeUpdate();
            return filas == 1;
        } catch (NumberFormatException e) {
            System.out.println("El año de nacimiento debe ser un número entero.");
            return false;
        } catch (Exception e) {
            System.out.println("Error al modificar el director: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────────
    // CONSULTAR directores
    // ─────────────────────────────────────────────

    /**
     * Devuelve todos los directores ordenados por nombre A-Z.
     */
    public List<DirectorDO> obtenerTodosOrdenadosPorNombre() {
        List<DirectorDO> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM director ORDER BY nombre ASC";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new DirectorDO(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("nacionalidad"),
                    rs.getInt("anyo_nacimiento")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error al consultar directores: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Devuelve todos los directores ordenados por año de nacimiento, de mayor a menor.
     */
    public List<DirectorDO> obtenerTodosOrdenadosPorAnyo() {
        List<DirectorDO> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM director ORDER BY anyo_nacimiento DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new DirectorDO(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("nacionalidad"),
                    rs.getInt("anyo_nacimiento")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error al consultar directores: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Devuelve directores filtrados por nacionalidad (sin distinguir mayúsculas).
     */
    public List<DirectorDO> obtenerPorNacionalidad(String nacionalidad) {
        List<DirectorDO> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM director WHERE LOWER(nacionalidad) = LOWER(?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, nacionalidad);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new DirectorDO(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("nacionalidad"),
                    rs.getInt("anyo_nacimiento")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error al filtrar por nacionalidad: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Devuelve las películas dirigidas por un director buscado por nombre.
     * Hace JOIN entre director y pelicula usando id_director.
     * Devuelve lista de Strings con: id, titulo, duracion, clasificacion.
     */
    public List<String> obtenerPeliculasDeDirector(String nombreDirector) {
        List<String> lista = new ArrayList<>();
        try {
            String query = "SELECT p.id, p.titulo, p.duracion, p.clasificacion " +
                           "FROM pelicula p " +
                           "JOIN director d ON p.id_director = d.id " +
                           "WHERE LOWER(d.nombre) = LOWER(?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, nombreDirector);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String linea = "[ID: " + rs.getInt("id") + "] " +
                               rs.getString("titulo") +
                               " | Duración: " + rs.getInt("duracion") + " min" +
                               " | Clasificación: " + rs.getInt("clasificacion");
                lista.add(linea);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener películas del director: " + e.getMessage());
        }
        return lista;
    }

    // ─────────────────────────────────────────────
    // ELIMINAR director
    // ─────────────────────────────────────────────

    /**
     * Elimina un director por su ID.
     * Si tiene películas asociadas (FK en pelicula.id_director), la BD lo impedirá.
     * @return true si se eliminó, false si no existe o falla
     */
    public boolean eliminarDirector(int id) {
        try {
            String query = "DELETE FROM director WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            if (filas == 0) {
                System.out.println("No existe ningún director con ID " + id);
                return false;
            }
            return true;
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("foreign key")) {
                System.out.println("No se puede eliminar: el director tiene películas asociadas en la BD.");
            } else {
                System.out.println("Error al eliminar el director: " + e.getMessage());
            }
            return false;
        }
    }
}