package com.practicajavafx;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;

public class FormEntidad extends GridPane {
    
    //etiquetas
    private Label lblNombre;
    private Label lblPrecio;
    private Label lblDescripcion;
    private Label lblTipo;
    private Label lblDisponible;
    private Label lblPrecioValor;
 
    //controles
    public TextField txtNombre;
    public Slider sldPrecio;
    public TextField txtDescripcion;
    public ComboBox<String> cmbTipo;
    public CheckBox chkDisponible;

    //botones
    public Button btnMostrar;
    public Button btnLimpiar;

    //label para mostrar resultados
    public Label lblResultado;


    public FormEntidad(){

    //creacion de objetos
    lblNombre = new Label("Nombre:");
    lblPrecio = new Label("Precio:");
    lblDescripcion = new Label("Descripción:");
    lblTipo = new Label("Tipo:");
    lblDisponible= new Label("Disponible:");
    lblPrecioValor = new Label("0.00 €");

    txtNombre = new TextField();
    txtDescripcion= new TextField();
    
    sldPrecio= new Slider(0,1000,0);
    cmbTipo = new ComboBox<String>();
    cmbTipo.getItems().addAll("fisico","digital");
    chkDisponible= new CheckBox();

    btnMostrar = new Button("Mostrar datos");
    btnLimpiar = new Button("Limpiar");
    lblResultado = new Label("");

    // tamaño de los controles
    txtNombre.setPrefWidth(300);
    txtDescripcion.setPrefWidth(300);
    sldPrecio.setPrefWidth(300);
    cmbTipo.setPrefWidth(300);

    // botones con color
    btnMostrar.setStyle("-fx-background-color: #69baf09a; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 20 8 20;");
    btnLimpiar.setStyle("-fx-background-color: #69baf09a; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 20 8 20;");


    //configurar el grid 
    this.setHgap(10);
    this.setVgap(8);
    this.setPadding(new Insets(20));

    //añadir objetos al grid
    this.add(lblNombre, 0, 0);
    this.add(txtNombre, 1, 0);
    this.add(lblPrecio, 0, 1);
    this.add(sldPrecio, 1, 1);
    this.add(lblDescripcion, 0, 2);
    this.add(txtDescripcion, 1, 2); 
    this.add(lblTipo, 0, 3);
    this.add(cmbTipo, 1, 3);
    this.add(lblDisponible,  0, 4);
    this.add(chkDisponible, 1, 4);
    this.add(lblPrecioValor, 2, 1);


   //separator 
   
    Separator separador = new Separator();
    this.add(separador, 0, 5, 2, 1);
    this.add(btnMostrar, 0, 6);
    this.add(btnLimpiar, 1, 6);
    this.add(lblResultado, 0, 7, 2, 1);

    //eventos botones
    btnMostrar.setOnAction(e->{mostrarDatos();});
    btnLimpiar.setOnAction(e->{limpiar();});

    //evento Slider
    sldPrecio.valueProperty().addListener((o,oldVal,newVal) -> {
        lblPrecioValor.setText(String.format("%.2f €", newVal.doubleValue()));
    });


    }

    private void mostrarDatos(){
        String texto = "Nombre:" + txtNombre.getText()
        + " | Precio: " + String.format("%.2f", sldPrecio.getValue())
        + " | Descripción: " + txtDescripcion.getText()
        + " | Tipo: " + cmbTipo.getValue()
        + " | Disponible: " + chkDisponible.isSelected();

        lblResultado.setText(texto);
    }

    private void limpiar(){
        txtNombre.clear();
        txtDescripcion.clear();
        sldPrecio.setValue(0);
        cmbTipo.getSelectionModel().selectFirst();
        chkDisponible.setSelected(false);
        lblResultado.setText("");
    }


}
