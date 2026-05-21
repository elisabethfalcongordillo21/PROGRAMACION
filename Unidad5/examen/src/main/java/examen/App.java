package examen;

import examen.model.ClienteDAO;
import examen.model.ClienteDO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import panels.VentanaEliminar;
import panels.VentanaInsertarChoiceBox;
import panels.VentanaMostrar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        // ── PESTAÑAS ─────────────────────────────────────────────────────────
        TabPane tabPane = new TabPane();

        Tab tabFormulario = new Tab("Formulario");
        Tab tabListado    = new Tab("Listado");
        tabFormulario.setClosable(false);
        tabListado.setClosable(false);
        tabPane.getTabs().addAll(tabFormulario, tabListado);

        // ── PESTAÑA 1: FORMULARIO ─────────────────────────────────────────────
        TextField txtDni      = new TextField();
        TextField txtNombre   = new TextField();
        TextField txtTelefono = new TextField();

        ComboBox<String> cmbTipo = new ComboBox<>();
        cmbTipo.getItems().addAll("Premium", "VIP", "Partner", "Basico");
        cmbTipo.setValue("Basico");

        ComboBox<String> cmbEstatus = new ComboBox<>();
        cmbEstatus.getItems().addAll("Activo", "Inactivo");
        cmbEstatus.setValue("Activo");

        Button btnGuardar = new Button("Guardar");
        btnGuardar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 13px;");

        Button btnMostrar = new Button("Mostrar");
        btnMostrar.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 13px;");

        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.setStyle("-fx-background-color: #7f8c8d; -fx-text-fill: white; -fx-font-size: 13px;");

        txtDni.setPrefWidth(250);
        txtNombre.setPrefWidth(250);
        txtTelefono.setPrefWidth(250);
        cmbTipo.setPrefWidth(250);
        cmbEstatus.setPrefWidth(250);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(20));

        grid.add(new Label("DNI:"),      0, 0); grid.add(txtDni,      1, 0);
        grid.add(new Label("Nombre:"),   0, 1); grid.add(txtNombre,   1, 1);
        grid.add(new Label("Teléfono:"), 0, 2); grid.add(txtTelefono, 1, 2);
        grid.add(new Label("Tipo:"),     0, 3); grid.add(cmbTipo,     1, 3);
        grid.add(new Label("Estatus:"),  0, 4); grid.add(cmbEstatus,  1, 4);
        grid.add(new Separator(),        0, 5, 2, 1);
        grid.add(btnGuardar, 0, 6);
        grid.add(btnMostrar,  1, 6);
        grid.add(btnLimpiar,  2, 6);

        tabFormulario.setContent(grid);

        // ── PESTAÑA 2: LISTADO CON FILAS Y BOTÓN BORRAR ───────────────────────
        Button btnCargar = new Button("Cargar Clientes");
        btnCargar.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");

        VBox vboxFilas = new VBox(6);
        vboxFilas.setPadding(new Insets(10));

        ScrollPane scroll = new ScrollPane(vboxFilas);
        scroll.setFitToWidth(true);

        VBox vboxListado = new VBox(10, btnCargar, scroll);
        vboxListado.setPadding(new Insets(15));
        tabListado.setContent(vboxListado);

        // ── MENÚ ──────────────────────────────────────────────────────────────
        MenuBar menuBar = new MenuBar();

        Menu mArchivo  = new Menu("Archivo");
        Menu mClientes = new Menu("Clientes");
        Menu mAyuda    = new Menu("Ayuda");

        // Archivo
        MenuItem miNuevo    = new MenuItem("Nuevo cliente");
        MenuItem miCargar   = new MenuItem("Cargar");
        MenuItem miExportar = new MenuItem("Exportar listado...");
        MenuItem miImportar = new MenuItem("Importar listado...");
        MenuItem miSalir    = new MenuItem("Salir");
        mArchivo.getItems().addAll(miNuevo, miCargar, miExportar, miImportar, miSalir);

        // Clientes -> submenús Eliminar e Insertar
        Menu mEliminar = new Menu("Eliminar");
        Menu mInsertar = new Menu("Insertar");
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

        // ── EVENTOS ───────────────────────────────────────────────────────────

        // Salir
        miSalir.setOnAction(e -> Platform.exit());

        // Nuevo cliente -> va al formulario y lo limpia
        miNuevo.setOnAction(e -> {
            tabPane.getSelectionModel().select(tabFormulario);
            txtDni.clear();
            txtNombre.clear();
            txtTelefono.clear();
            cmbTipo.setValue("Basico");
            cmbEstatus.setValue("Activo");
        });

        // Cargar (menú) -> va al listado y carga las filas
        miCargar.setOnAction(e -> {
            tabPane.getSelectionModel().select(tabListado);
            cargarFilas(vboxFilas, stage);
        });

        // Acerca de
        miAcerca.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Acerca de");
            alert.setHeaderText(null);
            alert.setContentText("Gestión de Clientes\nDesarrollado con JavaFX + MySQL");
            alert.showAndWait();
        });

        // Botón Guardar -> inserta en BD
        btnGuardar.setOnAction(e -> {
            if (txtDni.getText().isBlank() || txtNombre.getText().isBlank()) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setHeaderText(null);
                alerta.setContentText("DNI y Nombre son obligatorios.");
                alerta.showAndWait();
                return;
            }

            ClienteDO cliente = new ClienteDO();
            cliente.setDni(txtDni.getText().trim());
            cliente.setNombre(txtNombre.getText().trim());
            cliente.setTelefono(txtTelefono.getText().trim());
            cliente.setTipo(cmbTipo.getValue());
            cliente.setEstatus(cmbEstatus.getValue());

            ClienteDAO dao = new ClienteDAO();
            try {
                dao.insertar(cliente);
                Alert ok = new Alert(Alert.AlertType.INFORMATION);
                ok.setHeaderText(null);
                ok.setContentText("Cliente guardado correctamente.");
                ok.showAndWait();
            } catch (Exception ex) {
                System.out.println("Error al guardar: " + ex.getMessage());
            }
        });

        // Botón Mostrar -> abre VentanaMostrar con los datos del formulario
        btnMostrar.setOnAction(e -> {
            if (txtDni.getText().isBlank() || txtNombre.getText().isBlank()) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setHeaderText(null);
                alerta.setContentText("Rellena al menos DNI y Nombre.");
                alerta.showAndWait();
                return;
            }

            VentanaMostrar ventana = new VentanaMostrar(
                stage,
                txtDni.getText().trim(),
                txtNombre.getText().trim(),
                txtTelefono.getText().trim(),
                cmbTipo.getValue(),
                cmbEstatus.getValue()
            );
            ventana.show();
        });

        // Botón Limpiar -> vacía el formulario
        btnLimpiar.setOnAction(e -> {
            txtDni.clear();
            txtNombre.clear();
            txtTelefono.clear();
            cmbTipo.setValue("Basico");
            cmbEstatus.setValue("Activo");
        });

        // Botón Cargar Clientes -> carga las filas
        btnCargar.setOnAction(e -> cargarFilas(vboxFilas, stage));

        // Ventana Eliminar
        miEliminarCliente.setOnAction(e -> {
            VentanaEliminar ventana = new VentanaEliminar(stage);
            ventana.show();
        });

        // Ventana Insertar
        miInsertarCliente.setOnAction(e -> {
            VentanaInsertarChoiceBox ventana = new VentanaInsertarChoiceBox(stage);
            ventana.show();
        });

        // Exportar a fichero .txt
        miExportar.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Exportar listado");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Ficheros de texto", "*.txt"));

            File fichero = fileChooser.showSaveDialog(stage);

            if (fichero != null) {
                ClienteDAO dao = new ClienteDAO();
                try {
                    List<ClienteDO> lista = dao.listar();
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
                    for (ClienteDO c : lista) {
                        bw.write(c.getDni() + " ; "
                            + c.getNombre() + " ; "
                            + c.getTelefono() + " ; "
                            + c.getTipo() + " ; "
                            + c.getEstatus());
                        bw.newLine();
                    }
                    bw.close();
                    Alert ok = new Alert(Alert.AlertType.INFORMATION);
                    ok.setTitle("Exportado");
                    ok.setHeaderText(null);
                    ok.setContentText("Exportados " + lista.size() + " clientes.");
                    ok.showAndWait();
                } catch (Exception ex) {
                    System.out.println("Error al exportar: " + ex.getMessage());
                }
            }
        });

        // Importar desde fichero .txt
        miImportar.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Importar listado");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Ficheros de texto", "*.txt"));

            File fichero = fileChooser.showOpenDialog(stage);

            if (fichero != null) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(fichero));
                    String linea;
                    int contador = 0;
                    while ((linea = br.readLine()) != null) {
                        // cada línea: dni ; nombre ; telefono ; tipo ; estatus
                        int pos1 = linea.indexOf(";");
                        int pos2 = linea.indexOf(";", pos1 + 1);
                        int pos3 = linea.indexOf(";", pos2 + 1);
                        int pos4 = linea.indexOf(";", pos3 + 1);

                        String dni      = linea.substring(0, pos1).trim();
                        String nombre   = linea.substring(pos1 + 1, pos2).trim();
                        String telefono = linea.substring(pos2 + 1, pos3).trim();
                        String tipo     = linea.substring(pos3 + 1, pos4).trim();
                        String estatus  = linea.substring(pos4 + 1).trim();

                        ClienteDO c = new ClienteDO();
                        c.setDni(dni);
                        c.setNombre(nombre);
                        c.setTelefono(telefono);
                        c.setTipo(tipo);
                        c.setEstatus(estatus);

                        ClienteDAO dao = new ClienteDAO();
                        dao.insertar(c);
                        contador++;
                    }
                    br.close();

                    Alert ok = new Alert(Alert.AlertType.INFORMATION);
                    ok.setTitle("Importado");
                    ok.setHeaderText(null);
                    ok.setContentText("Importados " + contador + " clientes.");
                    ok.showAndWait();

                } catch (Exception ex) {
                    System.out.println("Error al importar: " + ex.getMessage());
                }
            }
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