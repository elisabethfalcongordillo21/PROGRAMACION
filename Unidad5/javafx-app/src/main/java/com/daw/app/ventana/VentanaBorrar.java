package com.daw.app.ventana;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.daw.app.model.PeliculaCombo;
import com.daw.app.model.PeliculasDAO;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VentanaBorrar extends Stage{

    private ComboBox<PeliculaCombo> cmbPeliculas;

    public VentanaBorrar(Stage stage)
    {
        //Creamos un vbox para añadir los elementos
        VBox pVertical = new VBox();
        //Añadimos al Vbox la lista y el boton de borrar
        cmbPeliculas= new ComboBox<>();
        //Cargamos las peliculas de BD en el combobox
        this.cargarPeliculas();
        pVertical.getChildren().add(cmbPeliculas);
        Button btnBorrar = new Button("Borrar pelicula");
        pVertical.getChildren().add(btnBorrar);
        //creamos la scene con el vbox con la lista y el boton
        Scene scene = new Scene(pVertical,600,400);
        //Esta misma clase es el stage, le asignamos la scene con el vbox
        this.setScene(scene);
        
        //Ponemos de modalidad de aperetura window_modal para bloquear a la padre
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(stage);
        
    

    }

    private void cargarPeliculas()
    {
        //Creamos el objeto PeliculasDAO para sacar las peliculas de bases de datos
        try(PeliculasDAO pDAO = new PeliculasDAO();
       
        ResultSet rs = pDAO.getPeliculas();)
        {
            while (rs.next())
            {
                //para cada registro cogemos el id y el titulo
                // y con ellos cargamos nuestro objeto peliculaCombo
                PeliculaCombo peliCmb = new PeliculaCombo();
                peliCmb.setId(rs.getInt(("id")));
                peliCmb.setTitulo(rs.getString(("titulo")));

                cmbPeliculas.getItems().add(peliCmb);

            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
