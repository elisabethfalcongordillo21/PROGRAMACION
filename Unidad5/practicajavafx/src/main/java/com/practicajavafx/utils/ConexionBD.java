package com.practicajavafx.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import io.github.cdimascio.dotenv.Dotenv;

public class ConexionBD {
     
    public static Connection conectar(){

        //cargamos todas las variables
        Dotenv dotenv = Dotenv.load();

        //asigno las variables del fichero .env
        String host = dotenv.get("DB_HOST");
        String port = dotenv.get("DB_PORT");
        String dbName = dotenv.get("DB_HOST");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        //creamos la url de conexion con el formato para mysql
        String url = "jdbc:mysql://"+ host + ":"+port+"/"+dbName;

        

    }








}
