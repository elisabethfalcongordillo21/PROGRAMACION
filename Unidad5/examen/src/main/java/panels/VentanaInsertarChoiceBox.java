package panels;

import examen.model.ClienteDAO;
import examen.model.ClienteDO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Ventana emergente con formulario para insertar un cliente.
 * Los campos de selección (tipo y estatus) usan ChoiceBox en lugar de ComboBox.
 * (Examen Rec Junio 23/24 - submenú Insertar)
 */
public class VentanaInsertarChoiceBox extends Stage {

    public VentanaInsertarChoiceBox(Stage padre) {

        // ---- Controles ----
        Label lblDni      = new Label("DNI:");
        Label lblNombre   = new Label("Nombre:");
        Label lblTelefono = new Label("Teléfono:");
        Label lblTipo     = new Label("Tipo:");
        Label lblEstatus  = new Label("Estatus:");

        TextField txtDni      = new TextField();
        TextField txtNombre   = new TextField();
        TextField txtTelefono = new TextField();

        // ChoiceBox para tipo y estatus (campos de selección)
        ChoiceBox<String> chbTipo = new ChoiceBox<>();
        chbTipo.getItems().addAll("Premium", "VIP", "Partner", "Basico");
        chbTipo.setValue("Basico");

        ChoiceBox<String> chbEstatus = new ChoiceBox<>();
        chbEstatus.getItems().addAll("Activo", "Inactivo");
        chbEstatus.setValue("Activo");

        Button btnInsertar = new Button("Insertar");
        btnInsertar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 13px;");

        // ---- GridPane ----
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(20));

        txtDni.setPrefWidth(250);
        txtNombre.setPrefWidth(250);
        txtTelefono.setPrefWidth(250);
        chbTipo.setPrefWidth(250);
        chbEstatus.setPrefWidth(250);

        grid.add(lblDni,       0, 0); grid.add(txtDni,      1, 0);
        grid.add(lblNombre,    0, 1); grid.add(txtNombre,   1, 1);
        grid.add(lblTelefono,  0, 2); grid.add(txtTelefono, 1, 2);
        grid.add(lblTipo,      0, 3); grid.add(chbTipo,     1, 3);
        grid.add(lblEstatus,   0, 4); grid.add(chbEstatus,  1, 4);
        grid.add(new Separator(), 0, 5, 2, 1);
        grid.add(btnInsertar,  0, 6);

        // ---- Evento insertar ----
        btnInsertar.setOnAction(e -> {
            if (txtNombre.getText().isBlank() || txtDni.getText().isBlank()) {
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
            cliente.setTipo(chbTipo.getValue());
            cliente.setEstatus(chbEstatus.getValue());

            try (ClienteDAO dao = new ClienteDAO()) {
                int resultado = dao.insertar(cliente);
                if (resultado == 1) {
                    Alert ok = new Alert(Alert.AlertType.INFORMATION);
                    ok.setHeaderText(null);
                    ok.setContentText("Cliente insertado correctamente.");
                    ok.showAndWait();
                    this.close();
                } else {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setHeaderText(null);
                    error.setContentText("Error al insertar el cliente.");
                    error.showAndWait();
                }
            } catch (Exception ex) {
                System.out.println("Error insertar: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(grid, 420, 320);
        this.setScene(scene);
        this.setTitle("Insertar Cliente");
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(padre);
    }
}