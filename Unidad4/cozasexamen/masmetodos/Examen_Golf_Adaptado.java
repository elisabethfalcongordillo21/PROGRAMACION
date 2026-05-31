
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
 
import com.unidad4.utils.Db;
 
/**
 * =====================================================
 * EXAMEN GOLF (23-24) → ADAPTADO A PELICULAS
 * =====================================================
 * TABLA ORIGINAL → TU BD:
 *   socio         → actor          (id, nombre, nacionalidad, fec_nac)
 *   club_golf     → cine           (id, nombre, direccion, localidades)
 *   hoyo          → sala           (id, nombre, localidades, tipo_sala, cine_id)
 *   club_has_socio→ pelicula_has_cine (pelicula_id, cine_id)
 *   recordGolpes  → no hay campo equivalente exacto, usamos localidades de sala
 *
 * MENÚ ORIGINAL → ADAPTADO:
 *   1. Alta de Socios        → Añadir Actor
 *   2. Modificar Club Golf   → Modificar Cine
 *   3. Cálculos              → Cálculos (submenú)
 *   4. Eliminar Hoyo         → Eliminar Sala
 *   5. Salir
 * =====================================================
 */
public class Examen_Golf_Adaptado {
 
    private Connection con;
 
    public Examen_Golf_Adaptado() {
        this.con = Db.conectar();
    }
 
    // ─────────────────────────────────────────────
    // OPCIÓN 1 – ALTA SOCIOS (Añadir Actor)
    // Original: insertar socio, si id ya existe mostrar error
    // Adaptado: insertar actor, id autoincremental
    // CAMBIAR: campos según tabla del examen
    // ─────────────────────────────────────────────
 
    public boolean añadirActor(String nombre, String nacionalidad, String fec_nac) {
        try {
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
 
    // ─────────────────────────────────────────────
    // OPCIÓN 2 – MODIFICAR CLUB GOLF (Modificar Cine)
    // Original: pedir id club, mostrar datos, pedir campo y nuevo valor
    // Adaptado: igual pero con cine
    // CAMBIAR: campos válidos según tabla del examen
    // ─────────────────────────────────────────────
 
    public Object[] buscarCinePorId(int id) {
        try {
            String query = "SELECT * FROM cine WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("direccion"),
                    rs.getInt("localidades")
                };
            }
        } catch (Exception e) {
            System.out.println("Error al buscar cine: " + e.getMessage());
        }
        return null;
    }
 
    public boolean modificarCine(int id, String campo, String nuevoValor) {
        try {
            String query = "UPDATE cine SET " + campo + " = ? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
 
            // CAMBIAR: campos numéricos según tu examen
            if (campo.equals("localidades")) {
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
            System.out.println("Error al modificar cine: " + e.getMessage());
            return false;
        }
    }
 
    // ─────────────────────────────────────────────
    // OPCIÓN 3 – CÁLCULOS → submenú
    // ─────────────────────────────────────────────
 
    /**
     * Opción 3.1: Suma de localidades de salas de cines en un intervalo de IDs
     * Original: suma de longitudes de hoyos de clubes entre id1 e id2 (inclusive)
     * CAMBIAR: tabla (sala → hoyo), campo (localidades → longitud)
     */
    public int sumaLocalidadesSalasPorIntervalo(int idCineDesde, int idCineHasta) {
        try {
            // Sumamos localidades de todas las salas de los cines en el intervalo
            String query = "SELECT SUM(s.localidades) AS total " +
                           "FROM sala s " +
                           "WHERE s.cine_id BETWEEN ? AND ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idCineDesde);
            stmt.setInt(2, idCineHasta);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Error al calcular suma: " + e.getMessage());
        }
        return 0;
    }
 
    /**
     * Opción 3.2: Actor con el mínimo de películas (equivale al socio con récord mínimo de golpes)
     * Original: SELECT socio.nombre, MIN(club_has_socio.recordGolpes) FROM socio JOIN club_has_socio
     * Adaptado: actor que aparece en menos películas (mínimo de apariciones)
     * CAMBIAR: si tu examen tiene un campo numérico en la tabla intermedia, ajusta el COUNT/MIN
     */
    public String actorConMenosPeliculas() {
        try {
            String query = "SELECT a.nombre, COUNT(ahp.pelicula_id) AS total_peliculas " +
                           "FROM actor a " +
                           "JOIN actor_has_pelicula ahp ON a.id = ahp.actor_id " +
                           "GROUP BY a.id, a.nombre " +
                           "ORDER BY total_peliculas ASC " +
                           "LIMIT 1";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nombre") + " (películas: " + rs.getInt("total_peliculas") + ")";
            }
        } catch (Exception e) {
            System.out.println("Error al calcular récord: " + e.getMessage());
        }
        return "No se encontraron datos.";
    }
 
    // ─────────────────────────────────────────────
    // OPCIÓN 4 – ELIMINAR HOYO (Eliminar Sala)
    // Original: pedir id del hoyo y eliminarlo
    // Adaptado: pedir id de la sala y eliminarla
    // CAMBIAR: tabla (sala → hoyo u otra tabla del examen)
    // ─────────────────────────────────────────────
 
    public boolean eliminarSala(int id) {
        try {
            String query = "DELETE FROM sala WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            if (filas == 0) {
                System.out.println("No existe ninguna sala con ID " + id);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error al eliminar sala: " + e.getMessage());
            return false;
        }
    }
}