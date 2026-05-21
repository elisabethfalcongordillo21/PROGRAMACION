package panels;

import examen.model.ClienteDAO;
import examen.model.ClienteDO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VentanaInsertarChoiceBox extends Stage {

    public VentanaInsertarChoiceBox(Stage padre) {

        TextField txtDni      = new TextField();
        TextField txtNombre   = new TextField();
        TextField txtTelefono = new TextField();

        ChoiceBox<String> chbTipo = new ChoiceBox<>();
        chbTipo.getItems().addAll("Premium", "VIP", "Partner", "Basico");
        chbTipo.setValue("Basico");

        ChoiceBox<String> chbEstatus = new ChoiceBox<>();
        chbEstatus.getItems().addAll("Activo", "Inactivo");
        chbEstatus.setValue("Activo");

        Button btnInsertar = new Button("Insertar");
        btnInsertar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 13px;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(20));

        txtDni.setPrefWidth(250);
        txtNombre.setPrefWidth(250);
        txtTelefono.setPrefWidth(250);
        chbTipo.setPrefWidth(250);
        chbEstatus.setPrefWidth(250);

        grid.add(new Label("DNI:"),      0, 0); grid.add(txtDni,      1, 0);
        grid.add(new Label("Nombre:"),   0, 1); grid.add(txtNombre,   1, 1);
        grid.add(new Label("Teléfono:"), 0, 2); grid.add(txtTelefono, 1, 2);
        grid.add(new Label("Tipo:"),     0, 3); grid.add(chbTipo,     1, 3);
        grid.add(new Label("Estatus:"),  0, 4); grid.add(chbEstatus,  1, 4);
        grid.add(new Separator(),        0, 5, 2, 1);
        grid.add(btnInsertar,            0, 6);

        btnInsertar.setOnAction(e -> {
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
            cliente.setTipo(chbTipo.getValue());
            cliente.setEstatus(chbEstatus.getValue());

            ClienteDAO dao = new ClienteDAO();
            try {
                dao.insertar(cliente);
                Alert ok = new Alert(Alert.AlertType.INFORMATION);
                ok.setHeaderText(null);
                ok.setContentText("Cliente insertado correctamente.");
                ok.showAndWait();
                this.close();
            } catch (Exception ex) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText(null);
                error.setContentText("Error al insertar el cliente.");
                error.showAndWait();
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