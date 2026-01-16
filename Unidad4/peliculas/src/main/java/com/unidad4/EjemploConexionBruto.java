package com.unidad4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import io.github.cdimascio.dotenv.Dotenv;

public class EjemploConexionBruto {
    public static void main(String[] args) {
        
        Dotenv dotenv = Dotenv.load();

        Connection connection = null;
        
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");

        String host = dotenv.get("DB_HOST");
        String port = dotenv.get("DB_PORT");
        String name = dotenv.get("DB_NAME");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");
        String url= "jdbc:mysql://" + host + ":" + port + "/" + name;


        connection = DriverManager.getConnection(url,user,password);

        String query = "select * from pelicula";

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        rs.next();

        connection.close();

        }
        catch(ClassNotFoundException ce){
          System.out.println("No tenemos el driver de conexion a base de datos instalado");
            ce.printStackTrace();
        }
        
        catch(Exception e) {
            System.out.println("Hubo un problema con la BD");
            e.printStackTrace();
        }
    }
}
