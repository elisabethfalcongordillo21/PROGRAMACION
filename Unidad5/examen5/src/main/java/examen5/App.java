package examen5;

import java.util.List;

import examen5.model.ClienteDAO;
import examen5.model.ClienteDO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.When;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;


public class App extends Application {

private Stage principal;

@Override
public void start(Stage stage){

    this.principal= stage;

    //MENÚ
    MenuBar menuBar = new MenuBar();

    Menu mArchivo  = new Menu("Archivo");
    MenuItem miCargar   = new MenuItem("Cargar");
    MenuItem miSalir    = new MenuItem("Salir");
    mArchivo.getItems().addAll(miCargar,miSalir);

    Menu mClientes = new Menu("Clientes");
    MenuItem miCargar2   = new MenuItem("Cargar");
    MenuItem miBuscar = new MenuItem("Buscar");
    mClientes.getItems().addAll(miCargar2,miBuscar );  
    menuBar.getMenus().addAll(mArchivo, mClientes);
    
// ── PESTAÑA PRINCIPAL ─────────────────────────────────────────────────────────
        TabPane tabPane = new TabPane();

        Tab tabListado    = new Tab("Listado");
        tabListado.setClosable(false);
        tabPane.getTabs().addAll(tabListado);

        Button btnCargar = new Button("Cargar Clientes");
        btnCargar.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");

        VBox vboxFilas = new VBox(6);
        vboxFilas.setPadding(new Insets(10));

        ScrollPane scroll = new ScrollPane(vboxFilas);
        scroll.setFitToWidth(true);

        VBox vboxListado = new VBox(10, btnCargar, scroll);
        vboxListado.setPadding(new Insets(15));
        tabListado.setContent(vboxListado);


        // BorderPane principal
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(tabPane);
    
         // Salir
        miSalir.setOnAction(e -> 
            {
                Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,"¿Seguro que quieres salir?");
                confirmacion.setTitle("Salir");
                confirmacion.setHeaderText(null);
                confirmacion.showAndWait().ifPresent(respuesta -> {
                    if (respuesta == ButtonType.OK){Platform.exit();}
                });
            }
        );

        //cargar clientes
        miCargar.setOnAction(e-> {
            tabPane.getSelectionModel().select(tabListado);
            cargarFilas(vboxFilas,stage);
        });

        miCargar2.setOnAction(e-> {
            tabPane.getSelectionModel().select(tabListado);
            cargarFilas(vboxFilas,stage);
        });
        



        // Botón Cargar Clientes -> carga las filas
        btnCargar.setOnAction(e -> cargarFilas(vboxFilas, stage));

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
        vboxFilas.getChildren().clear();

        ClienteDAO dao = new ClienteDAO();
        try {
            List<ClienteDO> lista = dao.listar();

            for (ClienteDO cliente : lista) {

                Label lblDatos = new Label(cliente.toString());
                lblDatos.setMaxWidth(Double.MAX_VALUE);
                HBox.setHgrow(lblDatos, Priority.ALWAYS);

                Button btnBorrar = new Button("Borrar");
                btnBorrar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

                btnBorrar.setOnAction(e -> {
                    ClienteDAO dao2 = new ClienteDAO();
                    try {
                        dao2.eliminar(cliente.getId_cliente());
                        cargarFilas(vboxFilas, stage);
                    } catch (Exception ex) {
                        System.out.println("Error al borrar: " + ex.getMessage());
                    }
                });

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