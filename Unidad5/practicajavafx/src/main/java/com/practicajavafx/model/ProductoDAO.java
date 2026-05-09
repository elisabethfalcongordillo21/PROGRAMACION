package com.practicajavafx.model;

import com.practicajavafx.utils.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    
    //metodo para insertar producto

    public int insertar(ProductoDO p){

    // el sql - los ? son valores que vamos a meter
    String sql = "INSERT INTO producto (nombre, precio,descripcion,tipo,disponible) VALUES (?,?,?,?,?)";

    // try-with-resources: abre la conexion y la cierra automaticamente al acabar
    try(Connection con = ConexionBD.getConexion(); 
        //para preparar la consulta sql 
        PreparedStatement stmt = con.prepareStatement(sql)){

            //asignamos los valores de cada ? en orden

            stmt.setString(1,p.getNombre());
            stmt.setDouble(2, p.getPrecio());
            stmt.setString(3, p.getDescripcion());
            stmt.setString(4,p.getTipo());
            stmt.setBoolean(5, p.isDisponible());

            //executeupdate ejecuta el insert y devuelve las filas afectadas(1 si fue bien, -1 si no)

            return stmt.executeUpdate();



        }catch(Exception e){
            System.out.println("Error al insertar: " + e.getMessage());
            return -1;

        }



    }

    //metodo para listar todos los productos

    public List<ProductoDO> listar(){

        //creamos la lista que va a devolver todos los productos
        List<ProductoDO> lista = new ArrayList<>();

        String sql = "SELECT * FROM producto";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery())
        {
            
            //recprremos fila a fila el resultado
            while (rs.next()) {

                //por cada fila creamos un producto
                ProductoDO p = new ProductoDO();
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setTipo(rs.getString("tipo"));
                p.setDisponible(rs.getBoolean("disponible"));
                
                //lo añadimos a la lista
                lista.add(p);
            }


        } catch (Exception e) {
            System.out.println("Error al listar: " + e.getMessage());
        }

        return lista;
        }

}
