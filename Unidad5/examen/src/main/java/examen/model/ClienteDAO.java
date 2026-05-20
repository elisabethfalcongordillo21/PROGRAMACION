package examen.model;
import examen.utils.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // ── INSERT ────────────────────────────────────────────────────────
    public int insertar(ClienteDO c)  {
        String sql = "INSERT INTO cliente (dni,nombre,telefono,tipo,estatus) VALUES (?,?,?,?,?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getDni());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getTipo());
            ps.setString(5, c.getEstatus());
            ps.executeUpdate();
        }
        catch (SQLException e) {
             System.out.println("Error al insertar cliente : " + e.getMessage());
        }
    }

    // ── SELECT ALL ────────────────────────────────────────────────────
    public List<ClienteDO> listar()  {
        List<ClienteDO> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new ClienteDO(
                    rs.getInt("id_cliente"),
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("tipo"),
                    rs.getString("estatus")
                ));
            }
        }
        catch (SQLException e) {
             System.out.println("Error al listar el cliente : " + e.getMessage());
        }
        return lista;
    }

    // ── SELECT filtrado por tipo ──────────────────────────────────────
    public List<ClienteDO> listarPorTipo(String tipo) {
        List<ClienteDO> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente WHERE tipo = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new ClienteDO(
                    rs.getInt("id_cliente"), rs.getString("dni"),
                    rs.getString("nombre"),  rs.getString("telefono"),
                    rs.getString("tipo"),    rs.getString("estatus")
                ));
            }
        }
        catch (SQLException e) {
             System.out.println("Error al listar por tipo : " + e.getMessage());
        }
        
        return lista;
    }

    // ── SELECT IDs ───────────────────────────────────
    public List<Integer> listarIds()  {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id_cliente FROM cliente";
        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) ids.add(rs.getInt("id_cliente"));
        
        }
        catch (SQLException e) {
             System.out.println("Error al listarIds : " + e.getMessage());
        }

         return ids;
    }

    // ── DELETE ────────────────────────────────────────────────────────
    public int eliminar(int id)  {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch (SQLException e) {
             System.out.println("Error al eliminar : " + e.getMessage());
        }
    }

    // ── UPDATE ────────────────────────────────────────────────────────
    public void actualizar(ClienteDO c) {
        String sql = "UPDATE cliente SET dni=?,nombre=?,telefono=?,tipo=?,estatus=? WHERE id_cliente=?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getDni());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getTipo());
            ps.setString(5, c.getEstatus());
            ps.setInt(6, c.getId_cliente());
            ps.executeUpdate();
            
        } 
        catch (SQLException e) {
             System.out.println("Error al actualizar : " + e.getMessage());
        }
    }

}
