package examen;

import examen.model.ClienteDAO;
import examen.model.ClienteDO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import panels.VentanaEliminar;
import panels.VentanaInsertarChoiceBox;

import java.util.List;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        // pestañas
        TabPane tabPane = new TabPane();
        Tab tabListado = new Tab("Listado");
        tabListado.setClosable(false);
        tabPane.getTabs().add(tabListado);

        // pestaña listado - un TextField y botón "Cargar Clientes"
        TextField txtBuscar = new TextField();
        txtBuscar.setPromptText("Buscar por nombre...");
        Button btnCargar = new Button("Cargar Clientes");

        // aquí se van a poner las filas de clientes dinámicamente
        VBox vboxFilas = new VBox(6);
        vboxFilas.setPadding(new Insets(10));

        ScrollPane scroll = new ScrollPane(vboxFilas);
        scroll.setFitToWidth(true);

        VBox vboxListado = new VBox(10, txtBuscar, btnCargar, scroll);
        vboxListado.setPadding(new Insets(15));
        tabListado.setContent(vboxListado);

        // barra de menú
        MenuBar menuBar = new MenuBar();

        Menu mArchivo   = new Menu("Archivo");
        Menu mClientes  = new Menu("Clientes");
        Menu mAyuda     = new Menu("Ayuda");

        // Archivo
        MenuItem miCargar = new MenuItem("Cargar");
        MenuItem miSalir  = new MenuItem("Salir");
        mArchivo.getItems().addAll(miCargar, miSalir);

        // Clientes -> submenús Eliminar e Insertar
        Menu mEliminar  = new Menu("Eliminar");
        Menu mInsertar  = new Menu("Insertar");
        MenuItem miEliminarCliente = new MenuItem("Eliminar cliente");
        MenuItem miInsertarCliente = new MenuItem("Insertar cliente");
        mEliminar.getItems().add(miEliminarCliente);
        mInsertar.getItems().add(miInsertarCliente);
        mClientes.getItems().addAll(mEliminar, mInsertar);

        // Ayuda
        MenuItem miAcerca = new MenuItem("Acerca de...");
        mAyuda.getItems().add(miAcerca);

        menuBar.getMenus().addAll(mArchivo, mClientes, mAyuda);

        // BorderPane principal
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(tabPane);

        // ── EVENTOS ──────────────────────────────────────────────────────────

        // Salir
        miSalir.setOnAction(e -> Platform.exit());

        // Acerca de
        miAcerca.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Acerca de");
            alert.setHeaderText(null);
            alert.setContentText("Gestión de Clientes\nDesarrollado con JavaFX + MySQL");
            alert.showAndWait();
        });

        // Cargar (menú) -> activa la pestaña listado y carga todos los clientes
        miCargar.setOnAction(e -> {
            tabPane.getSelectionModel().select(tabListado);
            cargarFilas(vboxFilas, stage);
        });

        // Botón "Cargar Clientes" -> carga todos los clientes en las filas
        btnCargar.setOnAction(e -> cargarFilas(vboxFilas, stage));

        // Abrir ventana Eliminar
        miEliminarCliente.setOnAction(e -> {
            VentanaEliminar ventana = new VentanaEliminar(stage);
            ventana.show();
        });

        // Abrir ventana Insertar
        miInsertarCliente.setOnAction(e -> {
            VentanaInsertarChoiceBox ventana = new VentanaInsertarChoiceBox(stage);
            ventana.show();
        });

        // Scene
        Scene scene = new Scene(root, 700, 500);
        stage.setTitle("Gestión de Clientes");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Carga todos los clientes de BD y crea una fila por cada uno.
     * Cada fila tiene los datos del cliente y un botón Borrar a la derecha.
     */
    private void cargarFilas(VBox vboxFilas, Stage stage) {
        // limpiamos las filas anteriores
        vboxFilas.getChildren().clear();

        ClienteDAO dao = new ClienteDAO();
        try {
            List<ClienteDO> lista = dao.listar();

            for (ClienteDO cliente : lista) {

                // etiqueta con los datos del cliente
                Label lblDatos = new Label(cliente.toString());
                lblDatos.setMaxWidth(Double.MAX_VALUE);
                HBox.setHgrow(lblDatos, Priority.ALWAYS);

                // botón borrar de esta fila
                Button btnBorrar = new Button("Borrar");
                btnBorrar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

                // al pulsar borrar, elimina de BD y recarga la lista
                btnBorrar.setOnAction(e -> {
                    ClienteDAO dao2 = new ClienteDAO();
                    try {
                        dao2.eliminar(cliente.getId_cliente());
                        // recargamos para que desaparezca la fila
                        cargarFilas(vboxFilas, stage);
                    } catch (Exception ex) {
                        System.out.println("Error al borrar: " + ex.getMessage());
                    }
                });

                // fila: datos + botón borrar
                HBox fila = new HBox(10, lblDatos, btnBorrar);
                fila.setPadding(new Insets(5));
                fila.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 0 0 1 0;");

                vboxFilas.getChildren().add(fila);
            }

        } catch (Exception ex) {
            System.out.println("Error al cargar clientes: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}