package panels;



import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.ResultSet;

import examen.model.ClienteDAO;
import examen.model.ClienteDO;

/**
 * Ventana emergente con ChoiceBox (no ComboBox) para eliminar un cliente.
 * El ChoiceBox se carga desde BD con los ids de los clientes.
 * (Examen Rec Junio 23/24 - opción submenú Eliminar)
 */
public class VentanaEliminar extends Stage {

    private ChoiceBox<ClienteDO> chbClientes;

    public VentanaEliminar(Stage padre) {

        chbClientes = new ChoiceBox<>();
        cargarClientes();

        Button btnEliminar = new Button("Eliminar");

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

            try (ClienteDAO dao = new ClienteDAO()) {
                int resultado = dao.eliminar(seleccionado.getId_cliente());
                if (resultado == 1) {
                    Alert ok = new Alert(Alert.AlertType.INFORMATION);
                    ok.setHeaderText(null);
                    ok.setContentText("Cliente eliminado correctamente.");
                    ok.showAndWait();
                    chbClientes.getItems().clear();
                    cargarClientes();
                } else {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setHeaderText(null);
                    error.setContentText("No se pudo eliminar el cliente.");
                    error.showAndWait();
                }
            } catch (Exception ex) {
                System.out.println("Error al eliminar: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(vBox, 450, 200);
        this.setScene(scene);
        this.setTitle("Eliminar Cliente");
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(padre);
    }

    /**
     * Carga los clientes de BD en el ChoiceBox usando ResultSet directamente.
     */
    private void cargarClientes() {
        try (ClienteDAO dao = new ClienteDAO();
             ResultSet rs = dao.getClientes()) {

            while (rs.next()) {
                ClienteDO c = new ClienteDO();
                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNombre(rs.getString("nombre"));
                chbClientes.getItems().add(c);
            }

        } catch (Exception e) {
            System.out.println("Error al cargar clientes en ChoiceBox: " + e.getMessage());
        }
    }
}