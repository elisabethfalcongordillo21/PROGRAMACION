package com.unidad4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.unidad4.utils.Db;

public class PremioDAO {
     private Connection con;
     public PremioDAO(){
        this.con=Db.conectar();
     }
    

     // añadir premio
     public boolean anadirPremio(String nombre,String categoria, int anyio_prem){

      try {
         String query = "INSERT INTO premio (nombre, categoria, anyio_prem) VALUES (?, ?, ?)";
         PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, nombre);
            stmt.setString(2, categoria);
            stmt.setInt(3, anyio_prem);
            int filas = stmt.executeUpdate();
            return filas == 1;
         } catch (Exception e) {
         System.out.println("Error al añadir el premio: " + e.getMessage());
            return false;      
         }
     }

     // modificar premio
   
      public Object[] buscarPremioPorId(int id) {
        try {
            String query = "SELECT * FROM premio WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getInt("anyio_prem")
                };
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el premio: " + e.getMessage());
        }
        return null;
    }
 
    public boolean modificarPremio(int id, String campo, String nuevoValor) {
        try {
            String query = "UPDATE premio SET " + campo + " = ? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
 
            // CAMBIAR: nombre, categoria, anyio
            if (campo.equals("nombre ")) {
               stmt.setString(1, nuevoValor);
            } if (campo.equals("categoria")) {
               stmt.setString(1,nuevoValor);
            }else{stmt.setInt(1, Integer.parseInt(nuevoValor));}
 
            stmt.setInt(2, id);
            int filas = stmt.executeUpdate();
            return filas == 1;
        } catch (NumberFormatException e) {
            System.out.println("El valor introducido no es  válido.");
            return false;
        } catch (Exception e) {
            System.out.println("Error al modificar el premio: " + e.getMessage());
            return false;
        }
    }

     // consultar premios

     // ordenados por anyio

   public List<PremioDO> obtenerTodosOrdenadosPorAnyio(){
      List<PremioDO> lista= new ArrayList<>();
      try {
         String query = "SELECT * FROM premio ORDER BY anyio_prem DESC";
         PreparedStatement stmt = con.prepareStatement(query);
         ResultSet rs = stmt.executeQuery();
         while (rs.next()) {
            lista.add(new PremioDO(
               rs.getInt("id"),
               rs.getString("nombre"),
               rs.getString("categoria"),
               rs.getInt("anyio_prem")
            ));
         } 
      } catch (Exception e) {
         System.out.println("Error al consultar premios: " + e.getMessage());
      }
      return lista;
   }

   //ordenados por categoria
   public List<PremioDO> obtenerPorCategoria(String categoria) {
        List<PremioDO> lista = new ArrayList<>();
        try {
            String query = "SELECT * FROM premio WHERE LOWER(categoria) = LOWER(?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, categoria);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new PremioDO(
               rs.getInt("id"),
               rs.getString("nombre"),
               rs.getString("categoria"),
               rs.getInt("anyio_prem")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar por categoria: " + e.getMessage());
        }
        return lista;
    }

    //mostrar premios de una pelicula
      public List<String> obtenerPeliculasPremio(String nombrePelicula) {
        List<String> lista = new ArrayList<>();
        try {
            String query = "SELECT pr.id, pr.nombre, pr.categoria, pr.anyio_prem " +
                           "FROM premio pr " +
                           "JOIN pelicula p ON pr.id = p.id " +
                           "WHERE LOWER(d.nombre) = LOWER(?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, nombrePelicula);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String linea = "[ID Premio: " + rs.getInt("id") + "] " +
                               rs.getString("nombre") +
                               " | Categoria: " + rs.getString("categoria") + " min" +
                               " | Año de conseción: " + rs.getInt("anyio_prem");
                lista.add(linea);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener películas del premio: " + e.getMessage());
        }
        return lista;
    }
   
    //mostrar peliculas con mas de un premio
    public List<PeliculaDO> obtenerPeliculaMas1( int duracion){
      List<PeliculaDO> lista = new ArrayList<>();
      try {
         String query = "SELECT id, titulo, duracion FROM pelicula WHERE duracion > ?";
         PreparedStatement stmt = con.prepareStatement(query);
         stmt.setInt(1, duracion);
         ResultSet rs = stmt.executeQuery();
         while (rs.next()) {
            lista.add(new PeliculaDO(
               rs.getInt("id"),
               rs.getString("titulo"),
               rs.getInt("duracion"),
               rs.getInt("clasificacion"),
               rs.getString("sinopsis")
            ));
         }
      } catch (Exception e) {
         System.out.println("Error al encontrar peliculas: " + e.getMessage());
      }
      return lista;
    }
   
    // eliminar premio 
   public boolean eliminarPremio(int id) {
        try {
            String query = "DELETE FROM premio WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            if (filas == 0) {
                System.out.println("No existe ningún premio con ID " + id);
                return false;
            }
            return true;
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("foreign key")) {
                System.out.println("No se puede eliminar: el premio tiene películas asociadas en la BD.");
            } else {
                System.out.println("Error al eliminar el premio: " + e.getMessage());
            }
            return false;
        }
    }
   
}
