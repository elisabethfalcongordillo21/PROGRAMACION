package com.practicajavafx;

import com.practicajavafx.model.ProductoDAO;
import com.practicajavafx.model.ProductoDO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    // Lista que guarda los productos en memoria
    private ArrayList<ProductoDO> listaProductos = new ArrayList<>();

    @Override
    public void start(Stage stage) {

        // pestañas
        TabPane tabPane = new TabPane();
        Tab tabFormulario = new Tab("Formulario");
        Tab tabListado    = new Tab("Listado");
        tabFormulario.setClosable(false);
        tabListado.setClosable(false);
        tabPane.getTabs().addAll(tabFormulario, tabListado);

        // pestaña 1
        FormEntidad formulario = new FormEntidad();
        formulario.btnMostrar.setText("Guardar");
        tabFormulario.setContent(formulario);

        // pestaña 2
        TextArea txtListado = new TextArea();
        txtListado.setEditable(false);
        VBox.setVgrow(txtListado, Priority.ALWAYS);
        txtListado.setMaxHeight(Double.MAX_VALUE);
        txtListado.setMaxWidth(Double.MAX_VALUE);
        Button btnActualizar = new Button("Actualizar");
        javafx.scene.layout.VBox vboxListado = new javafx.scene.layout.VBox(10, txtListado, btnActualizar);
        vboxListado.setPadding(new javafx.geometry.Insets(20));
        tabListado.setContent(vboxListado);

        // barra del menu 
        MenuBar menuBar = new MenuBar();
        Menu mArchivo = new Menu("Archivo");
        Menu mVer     = new Menu("Ver");
        Menu mAyuda   = new Menu("Ayuda");

        MenuItem miNuevo  = new MenuItem("Nueva producto");
        MenuItem miSalir  = new MenuItem("Salir");
        MenuItem miListado = new MenuItem("Listado");
        MenuItem miAcerca  = new MenuItem("Acerca de...");
        MenuItem miExportar = new MenuItem("Exportar listado...");
        MenuItem miImportar = new MenuItem("Importar listado...");
        
        mArchivo.getItems().addAll(miNuevo, miExportar,miImportar, miSalir);
        mVer.getItems().add(miListado);
        mAyuda.getItems().add(miAcerca);
        menuBar.getMenus().addAll(mArchivo, mVer, mAyuda);

        // BorderPane
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(tabPane);

        // eventos del menu
        miNuevo.setOnAction(e -> {
            tabPane.getSelectionModel().select(tabFormulario);
            formulario.btnLimpiar.fire();
        });

        miSalir.setOnAction(e -> Platform.exit());

        miListado.setOnAction(e -> {
            tabPane.getSelectionModel().select(tabListado);
        });

        miAcerca.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Acerca de");
            alert.setHeaderText(null);
            alert.setContentText("Aplicación desarrollada por Elisabeth \nGestión de Productos");
            alert.showAndWait();
        });

        miExportar.setOnAction(e->{
            //abrimos el dialogo para guardar el fichero
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Exportar listado");
            //filtramos para que solo muestre ficheros .txt
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Ficheros de texto", "*.txt"));

            File fichero = fileChooser.showSaveDialog(stage);

            if(fichero != null){
                try(BufferedWriter bw = new BufferedWriter(new FileWriter(fichero))) {
                  
                    ProductoDAO dao = new ProductoDAO();
                    List<ProductoDO> productos = dao.listar();

                    for (ProductoDO p : productos) {
                        
                        //una linea por producto separado por ;
                        bw.write(p.getNombre()+ " ; " 
                        + p.getPrecio() + " ; " 
                        + p.getDescripcion() + " ; " 
                        + p.getTipo() + " ; " 
                        + p.isDisponible());
                         bw.newLine();
                    }
                    
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Exportado");
                    alert.setHeaderText(null);
                    alert.setContentText("Exportado: " + productos.size() + " entidades.");
                    alert.showAndWait();
               
               
               
               
                } catch (Exception ex) {
                    System.out.println("Error al exportar: "+ ex.getMessage());
                }
            }

        });

        miImportar.setOnAction(e-> {
            //abrimos el dialogo para abrir el fichero
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Importar listado");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Ficheros de texto", "*.txt"));

            File fichero = fileChooser.showOpenDialog(stage);

            if (fichero != null) {
                try (BufferedReader br = new BufferedReader(new FileReader(fichero))){
                    
                    String linea;
                    int contador=0;
                    while ((linea = br.readLine()) !=null) {
                        // Buscamos la posición de cada ;
                        int pos1 = linea.indexOf(";");
                        int pos2 = linea.indexOf(";", pos1 + 1);
                        int pos3 = linea.indexOf(";", pos2 + 1);
                        int pos4 = linea.indexOf(";", pos3 + 1);

                        // Extraemos cada campo entre los ;
                        String nombre = linea.substring(0, pos1);
                        String precio = linea.substring(pos1 + 1, pos2);
                        String descripcion = linea.substring(pos2 + 1, pos3);
                        String tipo = linea.substring(pos3 + 1, pos4);
                        String disponible = linea.substring(pos4 + 1);
                    
                       // Creamos el objeto
                        ProductoDO p = new ProductoDO();
                        p.setNombre(nombre);
                        p.setPrecio(Double.parseDouble(precio));
                        p.setDescripcion(descripcion);
                        p.setTipo(tipo);
                        p.setDisponible(Boolean.parseBoolean(disponible));

                        listaProductos.add(p);
                        contador++; 
                    }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Importado");
                alert.setHeaderText(null);
                alert.setContentText("Importado: " + contador + " entidades.");
                alert.showAndWait();

                } catch (Exception ex) {

                    System.out.println("Error al importar: " + ex.getMessage());

                }
                
            }


        });

        // evento de guardar
        formulario.btnMostrar.setOnAction(e -> {
            ProductoDO p = new ProductoDO();
            p.setNombre(formulario.txtNombre.getText());
            p.setPrecio(formulario.sldPrecio.getValue());
            p.setDescripcion(formulario.txtDescripcion.getText());
            p.setTipo(formulario.cmbTipo.getValue());
            p.setDisponible(formulario.chkDisponible.isSelected());

            listaProductos.add(p);

            //llamamos al DAO para insertar en la BD

            ProductoDAO dao = new ProductoDAO();
            int resultado = dao.insertar(p);

            //si el resultado es -1 es que algo fue mal 
            if (resultado == -1){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error al guardar en la BD.");
                alert.showAndWait();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Guardado");
            alert.setHeaderText(null);
            alert.setContentText("Producto guardado: " + p.getNombre());
            alert.showAndWait();
        });

        // evento de actualizar
        btnActualizar.setOnAction(e -> {
            txtListado.clear();
            
            //llamamos al DAO para obtener los productos de la BD
            ProductoDAO dao = new ProductoDAO();
            List<ProductoDO> productos = dao.listar();
            
            for (ProductoDO p : productos) {
                txtListado.appendText(p.toString() + "\n");
            }


        });

        // scene
        Scene scene = new Scene(root, 600, 450);
        stage.setTitle("Gestión de Productos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}