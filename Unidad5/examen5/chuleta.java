// ╔══════════════════════════════════════════════════════════════════╗
// ║                     CHULETA EXAMEN JAVAFX                       ║
// ╚══════════════════════════════════════════════════════════════════╝

// ══════════════════════════════════════════════════════════════════════
// FICHEROS QUE TIENES
// ══════════════════════════════════════════════════════════════════════
//
//  App.java                   → ventana principal, menús, pestañas, eventos
//  VentanaInsertarChoiceBox   → ventana emergente con formulario para insertar
//  VentanaEliminar            → ventana emergente con ChoiceBox para eliminar
//  VentanaMostrar             → ventana emergente con datos en fuente/color distinto
//  ClienteDAO.java            → acceso a BD: insertar, listar, eliminar, actualizar
//  ClienteDO.java             → objeto Cliente con atributos y getters/setters

// ══════════════════════════════════════════════════════════════════════
// QUÉ HAY EN App.java Y DÓNDE ESTÁ CADA COSA
// ══════════════════════════════════════════════════════════════════════

// ── PESTAÑAS ──────────────────────────────────────────────────────────
//
//  Pestaña 1 "Formulario"
//    - campos: DNI, Nombre, Teléfono (TextField) + Tipo, Estatus (ComboBox)
//    - btnGuardar  → inserta en BD
//    - btnMostrar  → abre VentanaMostrar con los datos del formulario
//    - btnLimpiar  → vacía todos los campos
//
//  Pestaña 2 "Listado"
//    - btnCargar   → carga filas de BD
//    - cada fila   → Label con datos + botón Borrar rojo
//    - btnBorrar   → elimina de BD y recarga las filas

// ── MENÚS ─────────────────────────────────────────────────────────────
//
//  Archivo
//    - Nuevo cliente  → va al formulario y lo limpia
//    - Cargar         → va al listado y carga las filas
//    - Exportar...    → guarda .txt con todos los clientes (campo ; campo)
//    - Importar...    → lee .txt e inserta en BD con indexOf(";")
//    - Salir          → Platform.exit()
//
//  Clientes
//    - Eliminar → abre VentanaEliminar
//    - Insertar → abre VentanaInsertarChoiceBox
//
//  Ayuda
//    - Acerca de... → Alert INFORMATION con info de la app

// ── MÉTODO cargarFilas() ──────────────────────────────────────────────
//
//  - está al final de App.java, fuera de start()
//  - limpia las filas anteriores con vboxFilas.getChildren().clear()
//  - llama a dao.listar() y por cada cliente crea una HBox con:
//      Label(cliente.toString()) + Button("Borrar")
//  - al pulsar Borrar → dao.eliminar(cliente.getId_cliente()) → recarga

// ══════════════════════════════════════════════════════════════════════
// QUÉ HAY EN CADA VENTANA
// ══════════════════════════════════════════════════════════════════════

// ── VentanaInsertarChoiceBox ──────────────────────────────────────────
//
//  - GridPane con DNI, Nombre, Teléfono (TextField) + Tipo, Estatus (ChoiceBox)
//  - btnInsertar → valida DNI y Nombre no vacíos → dao.insertar() → cierra

// ── VentanaEliminar ───────────────────────────────────────────────────
//
//  - ChoiceBox<ClienteDO> cargado con dao.listar()
//  - btnEliminar → dao.eliminar(seleccionado.getId_cliente()) → recarga ChoiceBox

// ── VentanaMostrar ────────────────────────────────────────────────────
//
//  - recibe: dni, nombre, telefono, tipo, estatus como parámetros
//  - título:  fuente Georgia, color DARKSLATEBLUE, tamaño 22
//  - datos:   fuente Courier New, color DARKGREEN, tamaño 14
//  - fondo:   #eaf4fb (azul clarito)

// ══════════════════════════════════════════════════════════════════════
// QUÉ HAY EN ClienteDAO.java
// ══════════════════════════════════════════════════════════════════════
//
//  insertar(ClienteDO c)       → INSERT en BD, devuelve int
//  listar()                    → SELECT * FROM cliente, devuelve List<ClienteDO>
//  listarPorTipo(String tipo)  → SELECT filtrado por tipo, devuelve List<ClienteDO>
//  listarIds()                 → SELECT solo ids, devuelve List<Integer>
//  eliminar(int id)            → DELETE por id, devuelve int
//  actualizar(ClienteDO c)     → UPDATE todos los campos

// ══════════════════════════════════════════════════════════════════════
// COSAS QUE PUEDES QUITAR SEGÚN LO QUE PIDA EL EXAMEN
// ══════════════════════════════════════════════════════════════════════
//
//  Si NO piden Exportar/Importar  → quita miExportar, miImportar y sus eventos
//  Si NO piden VentanaMostrar     → quita btnMostrar y su evento
//  Si NO piden VentanaInsertar    → quita mInsertar, miInsertarCliente y su evento
//  Si NO piden VentanaEliminar    → quita mEliminar, miEliminarCliente y su evento
//  Si NO piden pestaña Formulario → quita todo el bloque PESTAÑA 1 y sus eventos
//  Si NO piden pestaña Listado    → quita todo el bloque PESTAÑA 2 y cargarFilas()

// ══════════════════════════════════════════════════════════════════════
// SNIPPETS ÚTILES
// ══════════════════════════════════════════════════════════════════════

// ── Abrir una ventana emergente ───────────────────────────────────────
//
//  VentanaEliminar ventana = new VentanaEliminar(stage);
//  ventana.show();

// ── Alert rápido ─────────────────────────────────────────────────────
//
//  Alert alert = new Alert(Alert.AlertType.INFORMATION); // o WARNING o ERROR
//  alert.setHeaderText(null);
//  alert.setContentText("Tu mensaje aquí");
//  alert.showAndWait();

// ── Cambiar de pestaña por código ────────────────────────────────────
//
//  tabPane.getSelectionModel().select(tabListado);

// ── Llamar al DAO ────────────────────────────────────────────────────
//
//  ClienteDAO dao = new ClienteDAO();
//  try {
//      dao.insertar(cliente);   // o listar(), eliminar(id), etc.
//  } catch (Exception ex) {
//      System.out.println("Error: " + ex.getMessage());
//  }

// ── Exportar a fichero .txt ───────────────────────────────────────────
//
//  FileChooser fileChooser = new FileChooser();
//  fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Ficheros de texto", "*.txt"));
//  File fichero = fileChooser.showSaveDialog(stage);
//  if (fichero != null) {
//      BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
//      bw.write(c.getDni() + " ; " + c.getNombre() + " ; " ...);
//      bw.newLine();
//      bw.close();
//  }

// ── Importar desde fichero .txt ───────────────────────────────────────
//
//  File fichero = fileChooser.showOpenDialog(stage);
//  if (fichero != null) {
//      BufferedReader br = new BufferedReader(new FileReader(fichero));
//      String linea;
//      while ((linea = br.readLine()) != null) {
//          int pos1 = linea.indexOf(";");
//          int pos2 = linea.indexOf(";", pos1 + 1);
//          String campo1 = linea.substring(0, pos1).trim();
//          String campo2 = linea.substring(pos1 + 1, pos2).trim();
//          // ... etc
//      }
//      br.close();
//  }

// ── Fila con botón Borrar ─────────────────────────────────────────────
//
//  Label lblDatos = new Label(cliente.toString());
//  lblDatos.setMaxWidth(Double.MAX_VALUE);
//  HBox.setHgrow(lblDatos, Priority.ALWAYS);
//  Button btnBorrar = new Button("Borrar");
//  btnBorrar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
//  btnBorrar.setOnAction(e -> {
//      dao.eliminar(cliente.getId_cliente());
//      cargarFilas(vboxFilas, stage);
//  });
//  HBox fila = new HBox(10, lblDatos, btnBorrar);
//  vboxFilas.getChildren().add(fila);

// ── ChoiceBox cargado desde BD ────────────────────────────────────────
//
//  ChoiceBox<ClienteDO> chbClientes = new ChoiceBox<>();
//  ClienteDAO dao = new ClienteDAO();
//  try {
//      List<ClienteDO> lista = dao.listar();
//      for (ClienteDO c : lista) {
//          chbClientes.getItems().add(c);
//      }
//  } catch (Exception e) {
//      System.out.println("Error: " + e.getMessage());
//  }

// ── Submenú con flechita ──────────────────────────────────────────────
//
//  Menu mEliminar = new Menu("Eliminar");           // <- este tiene flechita
//  MenuItem miEliminar = new MenuItem("Eliminar cliente");
//  mEliminar.getItems().add(miEliminar);
//  mClientes.getItems().add(mEliminar);
//
//  // Si quieres sin flechita, pon directamente MenuItem en el menú padre:
//  MenuItem miEliminar = new MenuItem("Eliminar cliente");
//  mClientes.getItems().add(miEliminar);