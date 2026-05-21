package panels;

import examen.model.ClienteDAO;
import examen.model.ClienteDO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.List;

public class VentanaEliminar extends Stage {

    private ChoiceBox<ClienteDO> chbClientes;

    public VentanaEliminar(Stage padre) {

        chbClientes = new ChoiceBox<>();
        cargarClientes();

        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 13px;");

        VBox vBox = new VBox(15,
                new Label("Selecciona el cliente a eliminar:"),
                chbClientes,
                btnEliminar);
        vBox.setPadding(new Insets(20));

        btnEliminar.setOnAction(e -> {
            ClienteDO seleccionado = chbClientes.getValue();

            if (seleccionado == null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setHeaderText(null);
                alerta.setContentText("Selecciona un cliente de la lista.");
                alerta.showAndWait();
                return;
            }

            ClienteDAO dao = new ClienteDAO();
            try {
                dao.eliminar(seleccionado.getId_cliente());
                Alert ok = new Alert(Alert.AlertType.INFORMATION);
                ok.setHeaderText(null);
                ok.setContentText("Cliente eliminado correctamente.");
                ok.showAndWait();
                chbClientes.getItems().clear();
                cargarClientes();
            } catch (Exception ex) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText(null);
                error.setContentText("No se pudo eliminar el cliente.");
                error.showAndWait();
                System.out.println("Error eliminar: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(vBox, 450, 200);
        this.setScene(scene);
        this.setTitle("Eliminar Cliente");
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(padre);
    }

    private void cargarClientes() {
        ClienteDAO dao = new ClienteDAO();
        try {
            List<ClienteDO> lista = dao.listar();
            for (ClienteDO c : lista) {
                chbClientes.getItems().add(c);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar clientes: " + e.getMessage());
        }
    }
}