package panels;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Ventana emergente que muestra los datos del cliente con un tipo de letra,
 * color y tamaño DISTINTOS al original.
 * (Lo que pide el examen Junio 2022 al pulsar botón "Mostrar")
 */
public class VentanaMostrar extends Stage {

    public VentanaMostrar(Stage padre, String dni, String nombre,
                          String telefono, String tipo, String estatus) {

        // Título con estilo distinto
        Label lblTitulo = new Label("Datos del Cliente");
        lblTitulo.setFont(Font.font("Georgia", FontWeight.BOLD, 22));
        lblTitulo.setTextFill(Color.DARKSLATEBLUE);

        // Datos con fuente, color y tamaño distintos al formulario principal
        Label lblDni      = dato("DNI: " + dni);
        Label lblNombre   = dato("Nombre: " + nombre);
        Label lblTelefono = dato("Teléfono: " + telefono);
        Label lblTipo     = dato("Tipo: " + tipo);
        Label lblEstatus  = dato("Estatus: " + estatus);

        VBox vBox = new VBox(12, lblTitulo, lblDni, lblNombre, lblTelefono, lblTipo, lblEstatus);
        vBox.setPadding(new Insets(30));
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setStyle("-fx-background-color: #eaf4fb;");

        Scene scene = new Scene(vBox, 420, 300);
        this.setScene(scene);
        this.setTitle("Mostrar Cliente");
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(padre);
    }

    /**
     * Crea una etiqueta con fuente Courier New, color verde oscuro y tamaño 14.
     * Distinto al formulario principal que usa la fuente por defecto del sistema.
     */
    private Label dato(String texto) {
        Label lbl = new Label(texto);
        lbl.setFont(Font.font("Courier New", FontWeight.NORMAL, 14));
        lbl.setTextFill(Color.DARKGREEN);
        return lbl;
    }
}