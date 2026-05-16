package com.practicajavafx.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import io.github.cdimascio.dotenv.Dotenv;

public class ConexionBD {
     
    public static Connection getConexion(){

        //cargamos todas las variables
        Dotenv dotenv = Dotenv.configure().directory("C:/Users/elisa/Desktop/PROGRAMACION/Unidad5/practicajavafx")
    .load();

        //asigno las variables del fichero .env
        String host = dotenv.get("DB_HOST");
        String port = dotenv.get("DB_PORT");
        String dbName = dotenv.get("DB_NAME");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        System.out.println("HOST: " + host);
        System.out.println("PORT: " + port);
        System.out.println("NAME: " + dbName);
        System.out.println("USER: " + user);

        //creamos la url de conexion con el formato para mysql
        String url = "jdbc:mysql://"+ host + ":"+port+"/"+dbName;

        //intentamos conectarnos y devolvemos la conexion
        try{
            
            return DriverManager.getConnection(url, user, password);

        }catch(Exception e){
            System.out.println("Error al conectar con la BD: "+ e.getMessage());
            return null;

        }

    }








}
