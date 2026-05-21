// ╔══════════════════════════════════════════════════════════════════╗
// ║                  COMENTARIOS DE CADA FICHERO                     ║
// ╚══════════════════════════════════════════════════════════════════╝


// ══════════════════════════════════════════════════════════════════════
// App.java
// ══════════════════════════════════════════════════════════════════════

//  public class App extends Application
//      → clase principal que lanza la aplicación

//  TabPane tabPane = new TabPane()
//      → contenedor que guarda las pestañas

//  Tab tabFormulario / Tab tabListado
//      → las dos pestañas, setClosable(false) para que no tengan la X

//  TextField txtDni, txtNombre, txtTelefono
//      → campos de texto para introducir los datos del cliente

//  ComboBox<String> cmbTipo / cmbEstatus
//      → listas desplegables para elegir tipo y estatus
//      → setValue() pone el valor que sale por defecto al abrir

//  Button btnGuardar → recoge los datos del formulario y los inserta en BD
//  Button btnMostrar → abre VentanaMostrar con los datos del formulario
//  Button btnLimpiar → vacía todos los campos del formulario

//  GridPane grid
//      → layout en forma de tabla, columna 0 etiquetas, columna 1 controles
//      → setHgap(10) separación horizontal, setVgap(8) separación vertical
//      → grid.add(control, columna, fila)

//  VBox vboxFilas
//      → aquí se meten las filas de clientes dinámicamente al pulsar Cargar

//  ScrollPane scroll
//      → envuelve el vboxFilas para que se pueda hacer scroll si hay muchos clientes

//  MenuBar → Menu → MenuItem
//      → así se construye el menú: barra > menú > opción
//      → Menu dentro de Menu tiene flechita (submenú)
//      → MenuItem dentro de Menu no tiene flechita

//  BorderPane root
//      → layout principal: setTop(menuBar) arriba, setCenter(tabPane) en el centro

//  miSalir.setOnAction(e -> Platform.exit())
//      → cierra la aplicación

//  miNuevo.setOnAction
//      → va a la pestaña formulario y limpia los campos

//  miCargar.setOnAction
//      → va a la pestaña listado y llama a cargarFilas()

//  btnGuardar.setOnAction
//      → valida que DNI y Nombre no estén vacíos
//      → crea un ClienteDO con los datos del formulario
//      → llama a dao.insertar(cliente) para guardarlo en BD

//  btnMostrar.setOnAction
//      → crea un VentanaMostrar pasándole los datos del formulario
//      → ventana.show() la abre

//  btnLimpiar.setOnAction
//      → txtDni.clear(), txtNombre.clear()... vacía todos los campos

//  miExportar.setOnAction
//      → FileChooser en modo guardar (showSaveDialog)
//      → escribe una línea por cliente: dni ; nombre ; telefono ; tipo ; estatus
//      → BufferedWriter bw → bw.write() → bw.newLine() → bw.close()

//  miImportar.setOnAction
//      → FileChooser en modo abrir (showOpenDialog)
//      → lee línea por línea con BufferedReader
//      → indexOf(";") para encontrar la posición de cada ;
//      → substring() para extraer cada campo entre los ;
//      → crea un ClienteDO y llama a dao.insertar()

//  private void cargarFilas(VBox vboxFilas, Stage stage)
//      → método separado porque se llama desde dos sitios: btnCargar y miCargar
//      → limpia las filas anteriores con vboxFilas.getChildren().clear()
//      → llama a dao.listar() y por cada cliente crea una HBox con:
//            Label(cliente.toString()) a la izquierda
//            Button("Borrar") rojo a la derecha
//      → al pulsar Borrar: dao.eliminar(cliente.getId_cliente()) y recarga


// ══════════════════════════════════════════════════════════════════════
// VentanaInsertarChoiceBox.java
// ══════════════════════════════════════════════════════════════════════

//  public class VentanaInsertarChoiceBox extends Stage
//      → extiende Stage para ser una ventana propia que se abre encima

//  VentanaInsertarChoiceBox(Stage padre)
//      → recibe el stage principal para poder ser modal

//  ChoiceBox<String> chbTipo / chbEstatus
//      → como ComboBox pero más simple, solo permite seleccionar no escribir
//      → getItems().addAll() para añadir opciones
//      → setValue() para el valor por defecto

//  GridPane grid
//      → mismo layout que el formulario principal

//  btnInsertar.setOnAction
//      → valida que DNI y Nombre no estén vacíos
//      → crea ClienteDO y llama a dao.insertar()
//      → this.close() cierra la ventana si fue bien

//  this.initModality(Modality.WINDOW_MODAL)
//      → hace que la ventana bloquee la ventana padre mientras está abierta
//  this.initOwner(padre)
//      → le dice quién es la ventana padre


// ══════════════════════════════════════════════════════════════════════
// VentanaEliminar.java
// ══════════════════════════════════════════════════════════════════════

//  private ChoiceBox<ClienteDO> chbClientes
//      → atributo de clase para poder usarlo en cargarClientes()
//      → es de tipo ClienteDO para guardar el objeto entero, no solo el nombre

//  cargarClientes()
//      → llama a dao.listar() y añade cada ClienteDO al ChoiceBox
//      → el ChoiceBox muestra el toString() de ClienteDO

//  btnEliminar.setOnAction
//      → chbClientes.getValue() para coger el cliente seleccionado
//      → si es null avisa con un Alert WARNING
//      → dao.eliminar(seleccionado.getId_cliente()) elimina de BD
//      → chbClientes.getItems().clear() + cargarClientes() recarga la lista


// ══════════════════════════════════════════════════════════════════════
// VentanaMostrar.java
// ══════════════════════════════════════════════════════════════════════

//  recibe: dni, nombre, telefono, tipo, estatus como parámetros String
//      → los datos vienen del formulario en App.java

//  lblTitulo
//      → fuente Georgia, color DARKSLATEBLUE, tamaño 22

//  private Label dato(String texto)
//      → método auxiliar para no repetir el estilo en cada etiqueta
//      → fuente Courier New, color DARKGREEN, tamaño 14
//      → estos estilos son DISTINTOS al formulario (lo pide el examen)

//  vBox.setStyle("-fx-background-color: #eaf4fb")
//      → fondo azul clarito para diferenciarlo visualmente


// ══════════════════════════════════════════════════════════════════════
// ClienteDAO.java
// ══════════════════════════════════════════════════════════════════════

//  clase que gestiona el acceso a la BD para la tabla cliente
//  DAO = Data Access Object

//  insertar(ClienteDO c) → devuelve int
//      → PreparedStatement con INSERT INTO cliente VALUES (?,?,?,?,?)
//      → los ? se asignan con ps.setString(1, c.getDni()) etc en orden
//      → ps.executeUpdate() ejecuta y devuelve filas afectadas (1 si fue bien)
//      → devuelve -1 si hubo error

//  listar() → devuelve List<ClienteDO>
//      → SELECT * FROM cliente
//      → recorre el ResultSet con while(rs.next())
//      → por cada fila crea un ClienteDO con el constructor con parámetros

//  listarPorTipo(String tipo) → devuelve List<ClienteDO>
//      → SELECT * FROM cliente WHERE tipo = ?
//      → filtra solo los clientes del tipo indicado

//  eliminar(int id) → devuelve int
//      → DELETE FROM cliente WHERE id_cliente = ?
//      → devuelve -1 si hubo error

//  actualizar(ClienteDO c) → devuelve void
//      → UPDATE cliente SET ... WHERE id_cliente = ?


// ══════════════════════════════════════════════════════════════════════
// ClienteDO.java
// ══════════════════════════════════════════════════════════════════════

//  clase que representa un cliente
//  DO = Data Object, solo tiene atributos y getters/setters, sin lógica de BD

//  atributos: id_cliente, dni, nombre, telefono, tipo, estatus
//      → corresponden a las columnas de la tabla cliente en BD

//  ClienteDO(int id, String dni, ...)
//      → constructor con todos los campos
//      → lo usa el DAO cuando lee filas de BD

//  ClienteDO()
//      → constructor vacío, necesario para hacer new ClienteDO() sin parámetros
//      → lo usamos en los formularios antes de hacer los setters

//  getNombre() / setNombre()
//      → getters para leer el valor, setters para asignarlo

//  toString()
//      → devuelve todos los datos como texto
//      → lo usan los Label en las filas y el ChoiceBox de VentanaEliminar