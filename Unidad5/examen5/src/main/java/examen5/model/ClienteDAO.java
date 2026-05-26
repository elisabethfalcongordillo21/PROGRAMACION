package examen5.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import examen5.utils.ConexionBD;

public class ClienteDAO {
    


// obtener todos de la base de datos 
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


    //cliente por id
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

    // eliminar 
     public int eliminar(int id)  {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
        catch (SQLException e) {
             System.out.println("Error al eliminar : " + e.getMessage());
            return -1;
            }
    }


}
