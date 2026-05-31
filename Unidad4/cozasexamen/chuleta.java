// =============================================================================
// CHULETA COMPLETA - EXAMEN PRÁCTICO JDBC
// Base de datos: peliculas_marcadiz
// Modelo del examen: Gestión de Críticos de Cine (modelo A)
// Para adaptarlo a Directores (modelo B), cambia "critico" por "director",
// "CriticoDO" por "DirectorDO", etc. La estructura es exactamente igual.
// =============================================================================

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// =============================================================================
// CLASE Db — La da el profesor. Solo cambia URL, USER y PASS si hace falta.
// =============================================================================
class Db {
    // Cambia el puerto (3306) o el nombre de la BD si tu profesor usa otro
    private static final String URL  = "jdbc:mysql://localhost:3306/peliculas_marcadiz";
    private static final String USER = "root";   // usuario de MySQL
    private static final String PASS = "1234";   // contraseña de MySQL

    // Llama a este método cada vez que necesites una conexión
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

// =============================================================================
// CLASE CriticoDO — "Data Object" = el molde para guardar UN crítico en memoria
// Un DO tiene los mismos campos que la tabla de la BD.
// Para el modelo B (Directores) crea DirectorDO con: nombre, nacionalidad, anyoNacimiento
// =============================================================================
class CriticoDO {

    // --- Atributos (uno por columna de la tabla "critico") ---
    private int    id;
    private String nombre;
    private String medio;        // periódico/revista/web donde escribe
    private int    anyoInicio;   // año en que empezó a ejercer la crítica

    // --- Constructor (el id se pone 0 al insertar porque la BD lo genera sola) ---
    public CriticoDO(int id, String nombre, String medio, int anyoInicio) {
        this.id         = id;
        this.nombre     = nombre;
        this.medio      = medio;
        this.anyoInicio = anyoInicio;
    }

    // --- Getters (para leer los datos desde fuera) ---
    public int    getId()         { return id; }
    public String getNombre()     { return nombre; }
    public String getMedio()      { return medio; }
    public int    getAnyoInicio() { return anyoInicio; }

    // --- toString: se usa para mostrar los datos por pantalla fácilmente ---
    @Override
    public String toString() {
        return "ID: " + id + " | Nombre: " + nombre
             + " | Medio: " + medio + " | Año inicio: " + anyoInicio;
    }
}

// =============================================================================
// CLASE CriticoDAO — "Data Access Object" = toda la lógica con la BD va aquí
// Cada opción del menú tiene su propio método (el profesor lo exige).
// Recibe una Connection en el constructor para reutilizarla.
// =============================================================================
class CriticoDAO {

    private Connection con; // conexión que se pasa desde el main

    public CriticoDAO(Connection con) {
        this.con = con;
    }

    // -------------------------------------------------------------------------
    // OPCIÓN 1 — Insertar un nuevo crítico
    // Devuelve true si se insertó bien, false si falló
    // -------------------------------------------------------------------------
    public boolean insertar(CriticoDO c) {
        // Los ? son los parámetros que se rellenan con setString/setInt
        String sql = "INSERT INTO critico (nombre, medio, anyo_inicio) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            // Posición 1 = primer ?, posición 2 = segundo ?, etc.
            stmt.setString(1, c.getNombre());
            stmt.setString(2, c.getMedio());
            stmt.setInt(3, c.getAnyoInicio());

            int filasAfectadas = stmt.executeUpdate(); // devuelve cuántas filas cambió
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }

    // -------------------------------------------------------------------------
    // OPCIÓN 2 — Buscar un crítico por su ID (para mostrarlo antes de modificar)
    // Devuelve un CriticoDO si existe, o null si no se encuentra
    // -------------------------------------------------------------------------
    public CriticoDO buscarPorId(int id) {
        String sql = "SELECT * FROM critico WHERE id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery(); // executeQuery para SELECT
            if (rs.next()) { // si hay resultado, construye el objeto
                return new CriticoDO(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("medio"),
                    rs.getInt("anyo_inicio")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar: " + e.getMessage());
        }
        return null; // no existe
    }

    // -------------------------------------------------------------------------
    // OPCIÓN 2 — Modificar un campo concreto de un crítico
    // "campo" es el nombre de la columna: "nombre", "medio" o "anyo_inicio"
    // "nuevoValor" siempre llega como String; si es número se convierte dentro
    // -------------------------------------------------------------------------
    public boolean modificar(int id, String campo, String nuevoValor) {

        // Validación: solo se permiten estos tres campos para evitar SQL injection
        if (!campo.equals("nombre") && !campo.equals("medio") && !campo.equals("anyo_inicio")) {
            System.out.println("Campo no válido. Usa: nombre, medio o anyo_inicio");
            return false;
        }

        // El nombre del campo va directo en el SQL (es seguro porque lo validamos arriba)
        String sql = "UPDATE critico SET " + campo + " = ? WHERE id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            // Si el campo es numérico, hay que convertirlo a int
            if (campo.equals("anyo_inicio")) {
                stmt.setInt(1, Integer.parseInt(nuevoValor)); // lanza NumberFormatException si no es número
            } else {
                stmt.setString(1, nuevoValor);
            }
            stmt.setInt(2, id);

            return stmt.executeUpdate() > 0;

        } catch (NumberFormatException e) {
            System.out.println("El año debe ser un número entero");
            return false;
        } catch (SQLException e) {
            System.out.println("Error al modificar: " + e.getMessage());
            return false;
        }
    }

    // -------------------------------------------------------------------------
    // OPCIÓN 3.1 — Listar todos los críticos ordenados por nombre A-Z
    // Devuelve una lista de CriticoDO (puede estar vacía si no hay ninguno)
    // -------------------------------------------------------------------------
    public List<CriticoDO> listarTodos() {
        List<CriticoDO> lista = new ArrayList<>();
        String sql = "SELECT * FROM critico ORDER BY nombre ASC";

        // Sin parámetros ? se puede usar prepareStatement igualmente
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) { // abrir rs dentro del try para que se cierre solo

            while (rs.next()) { // recorre fila a fila hasta que no haya más
                lista.add(new CriticoDO(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("medio"),
                    rs.getInt("anyo_inicio")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }

    // -------------------------------------------------------------------------
    // OPCIÓN 3.2 — Listar todos ordenados por año de inicio, mayor a menor
    // Solo cambia el ORDER BY respecto al método anterior
    // -------------------------------------------------------------------------
    public List<CriticoDO> listarPorAnyo() {
        List<CriticoDO> lista = new ArrayList<>();
        String sql = "SELECT * FROM critico ORDER BY anyo_inicio DESC"; // DESC = mayor a menor

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new CriticoDO(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("medio"),
                    rs.getInt("anyo_inicio")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar por año: " + e.getMessage());
        }
        return lista;
    }

    // -------------------------------------------------------------------------
    // OPCIÓN 3.3 — Listar críticos por medio (sin distinguir mayúsculas)
    // LOWER() convierte todo a minúsculas antes de comparar
    // -------------------------------------------------------------------------
    public List<CriticoDO> listarPorMedio(String medio) {
        List<CriticoDO> lista = new ArrayList<>();
        String sql = "SELECT * FROM critico WHERE LOWER(medio) = LOWER(?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, medio);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new CriticoDO(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("medio"),
                    rs.getInt("anyo_inicio")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar por medio: " + e.getMessage());
        }
        return lista;
    }

    // -------------------------------------------------------------------------
    // OPCIÓN 3.4 — Mostrar reseñas de un crítico (necesita JOIN con otras tablas)
    // NOTA: la tabla "resena" no está en la migración que nos dieron, el profesor
    // la habrá creado aparte. Ajusta los nombres de tabla/columna si hace falta.
    // -------------------------------------------------------------------------
    public void mostrarResenas(String nombreCritico) {
        // JOIN une la tabla resena con pelicula y critico para obtener todo de una vez
        String sql = "SELECT p.id, p.titulo, p.duracion, p.clasificacion, r.puntuacion " +
                     "FROM resena r " +
                     "JOIN pelicula p  ON r.pelicula_id = p.id " +
                     "JOIN critico  c  ON r.critico_id  = c.id " +
                     "WHERE LOWER(c.nombre) = LOWER(?)"; // sin distinción mayúsculas

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nombreCritico);
            ResultSet rs = stmt.executeQuery();

            boolean hayResultados = false;
            while (rs.next()) {
                hayResultados = true;
                // printf formatea la salida: %-30s = texto alineado a la izquierda en 30 chars
                System.out.printf("ID: %d | %-30s | %d min | Clasif: %d | Puntuación: %.1f%n",
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getInt("duracion"),
                    rs.getInt("clasificacion"),
                    rs.getDouble("puntuacion"));
            }

            if (!hayResultados) {
                System.out.println("El crítico no existe o no tiene reseñas.");
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar reseñas: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // OPCIÓN 3.5 — Contar cuántos críticos escriben en un medio concreto
    // COUNT(*) devuelve un número; se lee con rs.getInt(1) (columna 1)
    // -------------------------------------------------------------------------
    public int contarPorMedio(String medio) {
        String sql = "SELECT COUNT(*) FROM critico WHERE LOWER(medio) = LOWER(?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, medio);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // columna 1 = el resultado del COUNT
            }
        } catch (SQLException e) {
            System.out.println("Error al contar: " + e.getMessage());
        }
        return 0;
    }

    // -------------------------------------------------------------------------
    // OPCIÓN 4 — Eliminar un crítico por ID
    // Si tiene reseñas asociadas, la BD lanza error de clave foránea (código 1451)
    // -------------------------------------------------------------------------
    public boolean eliminar(int id) {
        String sql = "DELETE FROM critico WHERE id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            // Código 1451 = violación de clave foránea (tiene registros relacionados)
            if (e.getErrorCode() == 1451) {
                System.out.println("No se puede eliminar: el crítico tiene reseñas asociadas.");
            } else {
                System.out.println("Error al eliminar: " + e.getMessage());
            }
            return false;
        }
    }
}

// =============================================================================
// CLASE MAIN — El programa principal con el menú y la navegación
// Aquí se crea la conexión y el DAO, y se llama a un método por cada opción
// =============================================================================
public class CineManager_CHULETA {

    // El Scanner se declara aquí arriba para usarlo en todos los métodos static
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Connection con = null;
        try {
            con = Db.getConnection(); // abre la conexión al arrancar
            CriticoDAO dao = new CriticoDAO(con);

            int opcion;
            do {
                mostrarMenu();
                try {
                    opcion = Integer.parseInt(sc.nextLine()); // nextLine() evita problemas con el buffer
                } catch (NumberFormatException e) {
                    opcion = 0; // si escribe letras, fuerza que vuelva al menú
                }

                switch (opcion) {
                    case 1 -> anyadirCritico(dao);
                    case 2 -> modificarCritico(dao);
                    case 3 -> menuConsultas(dao);  // submenú
                    case 4 -> eliminarCritico(dao);
                    case 5 -> System.out.println("¡Hasta luego!");
                    default -> System.out.println("Opción no válida. Elige entre 1 y 5.");
                }

            } while (opcion != 5); // el bucle termina cuando el usuario elige "Salir"

        } catch (SQLException e) {
            // Si la BD no está arrancada o las credenciales son malas, cae aquí
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        } finally {
            // El bloque finally se ejecuta siempre, aunque haya excepción
            // Es el lugar correcto para cerrar la conexión
            if (con != null) {
                try { con.close(); } catch (SQLException e) { /* ignorar error al cerrar */ }
            }
        }
    }

    // =========================================================================
    // MENÚ PRINCIPAL — Se muestra cada vez que se termina una acción
    // =========================================================================
    private static void mostrarMenu() {
        System.out.println("\n========== Bienvenido a CineManager ==========");
        System.out.println("Gestión de Críticos");
        System.out.println("1. Añadir crítico");
        System.out.println("2. Modificar crítico");
        System.out.println("3. Consultar críticos");
        System.out.println("4. Eliminar crítico");
        System.out.println("5. Salir");
        System.out.print("Opción (1-5): ");
    }

    // =========================================================================
    // OPCIÓN 1 — Pedir datos al usuario e insertar en la BD
    // =========================================================================
    private static void anyadirCritico(CriticoDAO dao) {
        System.out.println("\n--- Añadir crítico ---");
        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Medio (periódico/revista/web): ");
            String medio = sc.nextLine();

            System.out.print("Año de inicio: ");
            int anyo = Integer.parseInt(sc.nextLine()); // lanza excepción si no es número

            // Se crea el DO con id=0 porque la BD lo genera automáticamente (AUTO_INCREMENT)
            CriticoDO nuevo = new CriticoDO(0, nombre, medio, anyo);

            if (dao.insertar(nuevo)) {
                System.out.println("Crítico añadido correctamente.");
            } else {
                System.out.println("No se pudo añadir el crítico.");
            }

        } catch (NumberFormatException e) {
            // Si el usuario escribe algo que no es número en el año, no se rompe el programa
            System.out.println("Error: el año de inicio debe ser un número entero.");
        }
        // Al terminar (con éxito o con error) vuelve automáticamente al menú principal
    }

    // =========================================================================
    // OPCIÓN 2 — Buscar, mostrar y modificar un crítico
    // =========================================================================
    private static void modificarCritico(CriticoDAO dao) {
        System.out.println("\n--- Modificar crítico ---");
        try {
            System.out.print("ID del crítico: ");
            int id = Integer.parseInt(sc.nextLine());

            // Primero buscamos si existe
            CriticoDO critico = dao.buscarPorId(id);
            if (critico == null) {
                System.out.println("No existe ningún crítico con ese ID.");
                return; // return sale del método y vuelve al menú
            }

            // Si existe, lo mostramos por pantalla
            System.out.println("Datos actuales: " + critico);

            System.out.print("Campo a modificar (nombre / medio / anyo_inicio): ");
            String campo = sc.nextLine();

            System.out.print("Nuevo valor: ");
            String valor = sc.nextLine();

            if (dao.modificar(id, campo, valor)) {
                System.out.println("Crítico actualizado correctamente.");
            } else {
                System.out.println("No se pudo actualizar.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: el ID debe ser un número entero.");
        }
    }

    // =========================================================================
    // OPCIÓN 3 — Submenú de consultas (bucle propio hasta que el usuario elija Volver)
    // =========================================================================
    private static void menuConsultas(CriticoDAO dao) {
        int op;
        do {
            System.out.println("\n--- Consultar críticos ---");
            System.out.println("1. Todos ordenados por nombre (A-Z)");
            System.out.println("2. Todos ordenados por año de inicio (mayor a menor)");
            System.out.println("3. Críticos por medio");
            System.out.println("4. Reseñas de un crítico");
            System.out.println("5. Número de críticos de un medio");
            System.out.println("6. Volver");
            System.out.print("Opción (1-6): ");

            try {
                op = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                op = 0;
            }

            switch (op) {
                case 1 -> mostrarLista(dao.listarTodos());
                case 2 -> mostrarLista(dao.listarPorAnyo());
                case 3 -> {
                    System.out.print("Nombre del medio: ");
                    String medio = sc.nextLine();
                    List<CriticoDO> resultado = dao.listarPorMedio(medio);
                    if (resultado.isEmpty()) {
                        System.out.println("No hay críticos en ese medio.");
                    } else {
                        mostrarLista(resultado);
                    }
                }
                case 4 -> {
                    System.out.print("Nombre del crítico: ");
                    String nombre = sc.nextLine();
                    dao.mostrarResenas(nombre); // este método imprime directamente
                }
                case 5 -> {
                    System.out.print("Nombre del medio: ");
                    String medio = sc.nextLine();
                    int total = dao.contarPorMedio(medio);
                    System.out.println("Número de críticos en '" + medio + "': " + total);
                }
                case 6 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }

        } while (op != 6); // el submenú se repite hasta que el usuario elija "Volver"
    }

    // =========================================================================
    // OPCIÓN 4 — Pedir ID y eliminar el crítico
    // =========================================================================
    private static void eliminarCritico(CriticoDAO dao) {
        System.out.println("\n--- Eliminar crítico ---");
        try {
            System.out.print("ID del crítico a eliminar: ");
            int id = Integer.parseInt(sc.nextLine());

            if (dao.eliminar(id)) {
                System.out.println("Crítico eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar (no existe o tiene reseñas asociadas).");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: el ID debe ser un número entero.");
        }
    }

    // =========================================================================
    // MÉTODO AUXILIAR — Muestra por pantalla todos los elementos de una lista
    // Se reutiliza en las opciones 3.1, 3.2 y 3.3 para no repetir código
    // =========================================================================
    private static void mostrarLista(List<CriticoDO> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay resultados.");
        } else {
            System.out.println("\n--- Resultados (" + lista.size() + ") ---");
            for (CriticoDO c : lista) {
                System.out.println(c); // llama a toString() de CriticoDO
            }
        }
    }
}

// =============================================================================
// REFERENCIA RÁPIDA — Métodos JDBC más importantes
// =============================================================================
//
// ABRIR CONEXIÓN:
//   Connection con = DriverManager.getConnection(url, user, pass);
//
// CREAR STATEMENT:
//   PreparedStatement stmt = con.prepareStatement("SELECT * FROM tabla WHERE id = ?");
//
// SETTERS (rellenar los ?):
//   stmt.setString(posicion, valor)   → texto
//   stmt.setInt(posicion, valor)      → número entero
//   stmt.setDouble(posicion, valor)   → número decimal
//   stmt.setDate(posicion, valor)     → fecha (java.sql.Date)
//
// EJECUTAR:
//   ResultSet rs = stmt.executeQuery()  → para SELECT
//   int filas   = stmt.executeUpdate()  → para INSERT / UPDATE / DELETE
//
// LEER RESULTADOS:
//   while (rs.next()) { ... }           → avanza fila a fila
//   rs.getString("columna")             → leer texto
//   rs.getInt("columna")                → leer entero (devuelve 0 si es NULL)
//   rs.getDouble("columna")             → leer decimal
//   rs.wasNull()                        → true si el último campo era NULL en BD
//
// CERRAR (orden obligatorio):
//   rs.close()   → primero el ResultSet
//   stmt.close() → luego el Statement
//   con.close()  → por último la Connection
//   (con try-with-resources se cierran solos al salir del bloque)
//
// TRUCOS SQL:
//   LOWER(campo) = LOWER(?)            → búsqueda sin distinguir mayúsculas
//   campo LIKE ?  con valor "%" + x + "%" → búsqueda parcial (contiene)
//   ORDER BY campo ASC                  → orden A-Z o menor a mayor
//   ORDER BY campo DESC                 → orden Z-A o mayor a menor
//   COUNT(*)                            → contar filas
//   JOIN tabla2 ON tabla1.col = tabla2.col → unir dos tablas
//
// CÓDIGO DE ERROR MySQL:
//   e.getErrorCode() == 1451  → clave foránea (no se puede borrar, tiene relacionados)
//   e.getErrorCode() == 1062  → clave duplicada (ya existe ese valor único)
//
// =============================================================================