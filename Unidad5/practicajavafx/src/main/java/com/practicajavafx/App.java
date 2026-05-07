package com.practicajavafx;

import com.practicajavafx.model.ProductoDO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.util.ArrayList;

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

        mArchivo.getItems().addAll(miNuevo, miSalir);
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

        // evento de guardar
        formulario.btnMostrar.setOnAction(e -> {
            ProductoDO p = new ProductoDO();
            p.setNombre(formulario.txtNombre.getText());
            p.setPrecio(formulario.sldPrecio.getValue());
            p.setDescripcion(formulario.txtDescripcion.getText());
            p.setTipo(formulario.cmbTipo.getValue());
            p.setDisponible(formulario.chkDisponible.isSelected());

            listaProductos.add(p);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Guardado");
            alert.setHeaderText(null);
            alert.setContentText("Producto guardado: " + p.getNombre());
            alert.showAndWait();
        });

        // evento de actualizar
        btnActualizar.setOnAction(e -> {
            txtListado.clear();
            for (ProductoDO p : listaProductos) {
                txtListado.appendText(p.toString() + "\n");
            }
        });

        // scene
        Scene scene = new Scene(root, 420, 380);
        stage.setTitle("Gestión de Productos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}