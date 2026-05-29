// =============================================================================
// EXAMEN PRÁCTICO - Java con JDBC
// Gestión de Críticos de Cine
// Base de datos: peliculas_marcadiz
//
// TABLAS QUE USA ESTE PROGRAMA (de la migración):
//   - critico       (id, nombre, medio, anyo_inicio)       <- la crea el profe aparte
//   - resena        (id, critico_id, pelicula_id, puntuacion) <- la crea el profe aparte
//   - pelicula      (id, titulo, duracion, clasificacion, sinopsis)
//
// ESTRUCTURA DE ARCHIVOS (entregar sueltos, sin carpeta):
//   Db.java, CriticoDO.java, CriticoDAO.java, Main.java
//   -> Aquí están todos juntos en un solo fichero para estudiar
// =============================================================================

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// =============================================================================
// 1. CLASE Db  —  La da el profesor, NO tocar salvo URL/USER/PASS
// =============================================================================
class Db {

    private static final String URL  = "jdbc:mysql://localhost:3306/peliculas_marcadiz";
    private static final String USER = "root";
    private static final String PASS = "1234";

    // Devuelve una nueva conexión cada vez que se llama
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}


// =============================================================================
// 2. CLASE CriticoDO  —  Un objeto por cada fila de la tabla "critico"
//    DO = Data Object = el "molde" que representa un registro de la BD en Java
// =============================================================================
class CriticoDO {

    // ---- Atributos: uno por columna de la tabla critico ----
    private int    id;
    private String nombre;
    private String medio;       // periódico, revista o web donde escribe
    private int    anyoInicio;  // año en que empezó a ejercer la crítica

    // ---- Constructor completo ----
    // Cuando insertas, pasa id=0 porque MySQL lo genera solo (AUTO_INCREMENT)
    public CriticoDO(int id, String nombre, String medio, int anyoInicio) {
        this.id         = id;
        this.nombre     = nombre;
        this.medio      = medio;
        this.anyoInicio = anyoInicio;
    }

    // ---- Getters: para acceder a los datos desde fuera de la clase ----
    public int    getId()         { return id; }
    public String getNombre()     { return nombre; }
    public String getMedio()      { return medio; }
    public int    getAnyoInicio() { return anyoInicio; }

    // ---- toString: cuando haces System.out.println(critico) muestra esto ----
    @Override
    public String toString() {
        return String.format("ID: %-4d | Nombre: %-25s | Medio: %-20s | Año inicio: %d",
                id, nombre, medio, anyoInicio);
    }
}


// =============================================================================
// 3. CLASE CriticoDAO  —  Toda la lógica de acceso a la base de datos
//    DAO = Data Access Object = aquí van TODOS los métodos que tocan la BD
//    El profesor exige UN MÉTODO POR CADA OPCIÓN DEL MENÚ
// =============================================================================
class CriticoDAO {

    private Connection con; // se recibe en el constructor y se reutiliza

    public CriticoDAO(Connection con) {
        this.con = con;
    }

    // -------------------------------------------------------------------------
    // OPCIÓN 1 — Insertar un nuevo crítico en la BD
    // Recibe un CriticoDO ya construido con los datos del usuario
    // Devuelve true si se insertó bien, false si falló
    // -------------------------------------------------------------------------
    public boolean insertar(CriticoDO c) {
        // Los ? se sustituyen en orden con los setXxx de abajo
        String sql = "INSERT INTO critico (nombre, medio, anyo_inicio) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, c.getNombre());    // ? posición 1 = nombre
            stmt.setString(2, c.getMedio());     // ? posición 2 = medio
            stmt.setInt   (3, c.getAnyoInicio()); // ? posición 3 = anyo_inicio

            int filasInsertadas = stmt.executeUpdate(); // devuelve nº de filas afectadas
            return filasInsertadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar crítico: " + e.getMessage());
            return false;
        }
    }

    // -------------------------------------------------------------------------
    // OPCIÓN 2 (paso 1) — Buscar un crítico por su ID para mostrarlo antes de editar
    // Devuelve el CriticoDO si existe, null si no se encuentra
    // -------------------------------------------------------------------------
    public CriticoDO buscarPorId(int id) {
        String sql = "SELECT * FROM critico WHERE id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery(); // executeQuery para SELECT

            if (rs.next()) { // si hay al menos una fila, construye el objeto
                return new CriticoDO(
                    rs.getInt   ("id"),
                    rs.getString("nombre"),
                    rs.getString("medio"),
                    rs.getInt   ("anyo_inicio")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar crítico: " + e.getMessage());
        }
        return null; // no existe
    }

    // -------------------------------------------------------------------------
    // OPCIÓN 2 (paso 2) — Modificar UN campo concreto de un crítico
    // campo = nombre de la columna: "nombre", "medio" o "anyo_inicio"
    // nuevoValor = siempre String; se convierte a int si el campo es numérico
    // -------------------------------------------------------------------------
    public boolean modificar(int id, String campo, String nuevoValor) {

        // Validación de seguridad: solo dejamos tocar estos tres campos
        // Así evitamos que el usuario pueda escribir SQL malicioso en "campo"
        if (!campo.equals("nombre") && !campo.equals("medio") && !campo.equals("anyo_inicio")) {
            System.out.println("Campo no válido. Escribe: nombre, medio o anyo_inicio");
            return false;
        }

        // El nombre del campo va directamente en el SQL (es seguro porque lo validamos arriba)
        String sql = "UPDATE critico SET " + campo + " = ? WHERE id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            // Si el campo es numérico hay que parsearlo
            if (campo.equals("anyo_inicio")) {
                stmt.setInt(1, Integer.parseInt(nuevoValor)); // lanza NumberFormatException si no es número
            } else {
                stmt.setString(1, nuevoValor);
            }
            stmt.setInt(2, id); // siempre el id en la última posición

            int filas = stmt.executeUpdate();
            return filas > 0; // true = se actualizó algo

        } catch (NumberFormatException e) {
            System.out.println("Error: el año debe ser un número entero.");
            return false;
        } catch (SQLException e) {
            System.out.println("Error al modificar: " + e.getMessage());
            return false;
        }
    }

    // -------------------------------------------------------------------------
    // OPCIÓN 3.1 — Listar TODOS los críticos ordenados por nombre A→Z
    // Devuelve List<CriticoDO> (puede estar vacía si no hay ninguno)
    // -------------------------------------------------------------------------
    public List<CriticoDO> listarTodos() {
        List<CriticoDO> lista = new ArrayList<>();
        String sql = "SELECT * FROM critico ORDER BY nombre ASC"; // ASC = A a Z

        // try-with-resources: cierra stmt y rs automáticamente al salir del bloque
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) { // recorre una fila por iteración hasta que no haya más
                lista.add(new CriticoDO(
                    rs.getInt   ("id"),
                    rs.getString("nombre"),
                    rs.getString("medio"),
                    rs.getInt   ("anyo_inicio")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }

    // -------------------------------------------------------------------------
    // OPCIÓN 3.2 — Listar TODOS ordenados por año de inicio, mayor a menor
    // Igual que el anterior pero con ORDER BY anyo_inicio DESC
    // -------------------------------------------------------------------------
    public List<CriticoDO> listarPorAnyo() {
        List<CriticoDO> lista = new ArrayList<>();
        String sql = "SELECT * FROM critico ORDER BY anyo_inicio DESC"; // DESC = mayor a menor

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new CriticoDO(
                    rs.getInt   ("id"),
                    rs.getString("nombre"),
                    rs.getString("medio"),
                    rs.getInt   ("anyo_inicio")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar por año: " + e.getMessage());
        }
        return lista;
    }

    // -------------------------------------------------------------------------
    // OPCIÓN 3.3 — Listar críticos de un medio concreto (sin distinguir mayúsculas)
    // LOWER() convierte a minúsculas para comparar sin importar si está en mayúsculas
    // -------------------------------------------------------------------------
    public List<CriticoDO> listarPorMedio(String medio) {
        List<CriticoDO> lista = new ArrayList<>();
        // LOWER en ambos lados para ignorar mayúsculas/minúsculas
        String sql = "SELECT * FROM critico WHERE LOWER(medio) = LOWER(?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, medio);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new CriticoDO(
                    rs.getInt   ("id"),
                    rs.getString("nombre"),
                    rs.getString("medio"),
                    rs.getInt   ("anyo_inicio")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error al filtrar por medio: " + e.getMessage());
        }
        return lista;
    }

    // -------------------------------------------------------------------------
    // OPCIÓN 3.4 — Mostrar todas las películas que ha reseñado un crítico
    // Usa JOIN para unir las tablas critico, resena y pelicula
    // Muestra: id pelicula, titulo, duracion, clasificacion, puntuacion
    // -------------------------------------------------------------------------
    public void mostrarResenas(String nombreCritico) {

        // JOIN = unir varias tablas en una sola consulta
        // r = alias de resena, p = alias de pelicula, c = alias de critico
        String sql =
            "SELECT p.id, p.titulo, p.duracion, p.clasificacion, r.puntuacion " +
            "FROM resena r " +
            "JOIN pelicula p ON r.pelicula_id = p.id " +   // une resena con pelicula
            "JOIN critico  c ON r.critico_id  = c.id " +   // une resena con critico
            "WHERE LOWER(c.nombre) = LOWER(?)";             // filtra por nombre del crítico

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nombreCritico);

            ResultSet rs = stmt.executeQuery();

            boolean hayResultados = false;
            while (rs.next()) {
                hayResultados = true;
                // printf formatea la salida con anchos fijos para que quede alineado
                System.out.printf("ID: %d | %-30s | %3d min | Clasif: %d | Puntuación: %.1f%n",
                    rs.getInt   ("id"),
                    rs.getString("titulo"),
                    rs.getInt   ("duracion"),
                    rs.getInt   ("clasificacion"),
                    rs.getDouble("puntuacion")      // getDouble para decimales
                );
            }

            if (!hayResultados) {
                System.out.println("El crítico no existe o no tiene reseñas registradas.");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener reseñas: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // OPCIÓN 3.5 — Contar cuántos críticos escriben en un medio
    // COUNT(*) cuenta las filas que cumplen el WHERE
    // -------------------------------------------------------------------------
    public int contarPorMedio(String medio) {
        String sql = "SELECT COUNT(*) FROM critico WHERE LOWER(medio) = LOWER(?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, medio);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // columna 1 = el número que devuelve COUNT(*)
            }

        } catch (SQLException e) {
            System.out.println("Error al contar: " + e.getMessage());
        }
        return 0; // si algo falla devolvemos 0
    }

    // -------------------------------------------------------------------------
    // OPCIÓN 4 — Eliminar un crítico por su ID
    // Si tiene reseñas asociadas, MySQL lanza error de clave foránea (código 1451)
    // El programa debe capturarlo y mostrar un mensaje claro
    // -------------------------------------------------------------------------
    public boolean eliminar(int id) {
        String sql = "DELETE FROM critico WHERE id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int filasEliminadas = stmt.executeUpdate();

            if (filasEliminadas == 0) {
                // El DELETE no falló, pero no borró nada → el ID no existía
                System.out.println("No existe ningún crítico con ID " + id);
                return false;
            }
            return true;

        } catch (SQLException e) {
            // Código MySQL 1451 = violación de clave foránea
            // (el crítico tiene reseñas que apuntan a él y no se puede borrar)
            if (e.getErrorCode() == 1451) {
                System.out.println("No se puede eliminar: este crítico tiene reseñas asociadas.");
                System.out.println("Elimina primero sus reseñas.");
            } else {
                System.out.println("Error al eliminar: " + e.getMessage());
            }
            return false;
        }
    }
}


// =============================================================================
// 4. CLASE PRINCIPAL  —  Menú, navegación y entrada de datos por consola
//    Aquí se crea la conexión y el DAO, y cada opción llama a su propio método
// =============================================================================
public class CineManager_EXAMEN {

    // Scanner estático para usarlo en todos los métodos sin pasarlo como parámetro
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Connection con = null;

        try {
            con = Db.getConnection();          // abre la conexión UNA sola vez al arrancar
            CriticoDAO dao = new CriticoDAO(con); // se la pasamos al DAO

            int opcion;
            do {
                mostrarMenuPrincipal();

                try {
                    // nextLine() es mejor que nextInt() porque vacía el buffer del enter
                    opcion = Integer.parseInt(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    opcion = 0; // si escribe letras, fuerza que vuelva al menú
                }

                // Switch con lambdas (Java 14+). Si el profe usa Java 8, cambia -> por :
                switch (opcion) {
                    case 1 -> opcionAnyadir(dao);
                    case 2 -> opcionModificar(dao);
                    case 3 -> menuConsultas(dao);  // abre el submenú
                    case 4 -> opcionEliminar(dao);
                    case 5 -> System.out.println("¡Hasta luego!");
                    default -> System.out.println("Opción no válida. Elige entre 1 y 5.");
                }

            } while (opcion != 5); // el bucle principal termina solo cuando el usuario elige Salir

        } catch (SQLException e) {
            // Si MySQL no está arrancado o las credenciales son incorrectas, cae aquí
            System.out.println("ERROR: No se pudo conectar a la base de datos.");
            System.out.println("Comprueba que MySQL está arrancado y las credenciales son correctas.");
            System.out.println("Detalle: " + e.getMessage());
        } finally {
            // El bloque finally se ejecuta SIEMPRE, haya error o no
            // Es el lugar correcto para cerrar la conexión
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    // Ignoramos errores al cerrar
                }
            }
        }
    }

    // =========================================================================
    // MENÚ PRINCIPAL — Se imprime al principio de cada iteración del bucle
    // =========================================================================
    private static void mostrarMenuPrincipal() {
        System.out.println("\n======================================");
        System.out.println("    Bienvenido a CineManager");
        System.out.println("    Gestión de Críticos");
        System.out.println("======================================");
        System.out.println("1. Añadir crítico");
        System.out.println("2. Modificar crítico");
        System.out.println("3. Consultar críticos");
        System.out.println("4. Eliminar crítico");
        System.out.println("5. Salir");
        System.out.print("Opción (1-5): ");
    }

    // =========================================================================
    // OPCIÓN 1 — Pedir datos al usuario y llamar a dao.insertar()
    // =========================================================================
    private static void opcionAnyadir(CriticoDAO dao) {
        System.out.println("\n--- Añadir nuevo crítico ---");

        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine().trim();

            System.out.print("Medio (periódico / revista / web): ");
            String medio = sc.nextLine().trim();

            System.out.print("Año de inicio (número): ");
            // parseInt lanza NumberFormatException si el usuario escribe letras
            int anyoInicio = Integer.parseInt(sc.nextLine().trim());

            // id = 0 porque la BD lo asigna sola con AUTO_INCREMENT
            CriticoDO nuevo = new CriticoDO(0, nombre, medio, anyoInicio);

            if (dao.insertar(nuevo)) {
                System.out.println("✓ Crítico añadido correctamente.");
            } else {
                System.out.println("✗ No se pudo añadir el crítico.");
            }

        } catch (NumberFormatException e) {
            // Si el año no es un número entero, mostramos error y VOLVEMOS AL MENÚ
            // El programa NO se cierra (requisito del examen)
            System.out.println("Error: el año de inicio debe ser un número entero.");
        }
        // Al terminar este método (con éxito o con error) el do-while vuelve al menú
    }

    // =========================================================================
    // OPCIÓN 2 — Buscar, mostrar y modificar un campo de un crítico
    // =========================================================================
    private static void opcionModificar(CriticoDAO dao) {
        System.out.println("\n--- Modificar crítico ---");

        try {
            System.out.print("ID del crítico que quieres modificar: ");
            int id = Integer.parseInt(sc.nextLine().trim());

            // Primero buscamos si existe
            CriticoDO critico = dao.buscarPorId(id);

            if (critico == null) {
                System.out.println("No existe ningún crítico con ese ID.");
                return; // return = salir del método y volver al menú principal
            }

            // Si existe, lo mostramos por pantalla
            System.out.println("Datos actuales:");
            System.out.println(critico.toString());

            System.out.print("\nCampo a modificar (nombre / medio / anyo_inicio): ");
            String campo = sc.nextLine().trim();

            System.out.print("Nuevo valor: ");
            String valor = sc.nextLine().trim();

            if (dao.modificar(id, campo, valor)) {
                System.out.println("✓ Crítico actualizado correctamente.");
            } else {
                System.out.println("✗ No se pudo actualizar.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: el ID debe ser un número entero.");
        }
    }

    // =========================================================================
    // OPCIÓN 3 — Submenú de consultas con su propio bucle do-while
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
            System.out.println("6. Volver al menú principal");
            System.out.print("Opción (1-6): ");

            try {
                op = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                op = 0; // opción inválida, se vuelve a mostrar el submenú
            }

            switch (op) {
                case 1 -> {
                    // Opción 3.1: todos por nombre
                    List<CriticoDO> todos = dao.listarTodos();
                    mostrarLista(todos);
                }
                case 2 -> {
                    // Opción 3.2: todos por año descendente
                    List<CriticoDO> porAnyo = dao.listarPorAnyo();
                    mostrarLista(porAnyo);
                }
                case 3 -> {
                    // Opción 3.3: filtrar por medio (sin distinguir mayúsculas)
                    System.out.print("Nombre del medio: ");
                    String medio = sc.nextLine().trim();
                    List<CriticoDO> porMedio = dao.listarPorMedio(medio);
                    if (porMedio.isEmpty()) {
                        System.out.println("No hay críticos registrados en ese medio.");
                    } else {
                        mostrarLista(porMedio);
                    }
                }
                case 4 -> {
                    // Opción 3.4: películas reseñadas por un crítico (JOIN)
                    System.out.print("Nombre del crítico: ");
                    String nombre = sc.nextLine().trim();
                    dao.mostrarResenas(nombre); // este método imprime directamente
                }
                case 5 -> {
                    // Opción 3.5: COUNT de críticos de un medio
                    System.out.print("Nombre del medio: ");
                    String medio = sc.nextLine().trim();
                    int total = dao.contarPorMedio(medio);
                    System.out.println("Número de críticos en '" + medio + "': " + total);
                }
                case 6 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida. Elige entre 1 y 6.");
            }

        } while (op != 6); // el submenú se repite hasta que el usuario elija "Volver"
    }

    // =========================================================================
    // OPCIÓN 4 — Pedir ID y eliminar
    // =========================================================================
    private static void opcionEliminar(CriticoDAO dao) {
        System.out.println("\n--- Eliminar crítico ---");

        try {
            System.out.print("ID del crítico a eliminar: ");
            int id = Integer.parseInt(sc.nextLine().trim());

            if (dao.eliminar(id)) {
                System.out.println("✓ Crítico eliminado correctamente.");
            }
            // Los mensajes de error ya los imprime dao.eliminar() internamente

        } catch (NumberFormatException e) {
            System.out.println("Error: el ID debe ser un número entero.");
        }
    }

    // =========================================================================
    // MÉTODO AUXILIAR — Imprime una lista de críticos
    // Se reutiliza en las opciones 3.1, 3.2 y 3.3 para no repetir código
    // =========================================================================
    private static void mostrarLista(List<CriticoDO> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay resultados para mostrar.");
        } else {
            System.out.println("\n--- " + lista.size() + " resultado(s) ---");
            for (CriticoDO c : lista) {
                System.out.println(c); // llama automáticamente a toString() de CriticoDO
            }
        }
    }
}


// =============================================================================
// REFERENCIA RÁPIDA DE MÉTODOS JDBC (para repasar antes del examen)
// =============================================================================
//
// ── CONEXIÓN ─────────────────────────────────────────────────────────────────
//  Connection con = DriverManager.getConnection(url, user, pass)
//  con.close()
//
// ── CREAR STATEMENT ──────────────────────────────────────────────────────────
//  PreparedStatement stmt = con.prepareStatement("SQL con ? para parámetros")
//
// ── SETTERS (rellenar los ?) ─────────────────────────────────────────────────
//  stmt.setString (posicion, String)         → texto VARCHAR
//  stmt.setInt    (posicion, int)            → entero INT
//  stmt.setDouble (posicion, double)         → decimal DOUBLE/FLOAT
//  stmt.setDate   (posicion, Date.valueOf(s))→ fecha DATE
//
// ── EJECUTAR ─────────────────────────────────────────────────────────────────
//  ResultSet rs = stmt.executeQuery()        → para SELECT
//  int filas    = stmt.executeUpdate()       → para INSERT / UPDATE / DELETE
//  ResultSet ks = stmt.getGeneratedKeys()    → para obtener el ID insertado
//
// ── LEER RESULTADOS ──────────────────────────────────────────────────────────
//  while (rs.next())             → avanza fila a fila; devuelve false al acabar
//  rs.getString ("columna")      → lee texto
//  rs.getInt    ("columna")      → lee entero  (devuelve 0 si el campo es NULL)
//  rs.getDouble ("columna")      → lee decimal
//  rs.getDate   ("columna")      → lee fecha
//  rs.getObject ("columna")      → lee cualquier tipo automáticamente
//  rs.wasNull()                  → true si el ÚLTIMO campo leído era NULL en BD
//  rs.getInt(1)                  → lee por posición (útil para COUNT(*))
//
// ── CERRAR RECURSOS (orden obligatorio) ──────────────────────────────────────
//  1. rs.close()
//  2. stmt.close()
//  3. con.close()
//  Con try-with-resources se cierran solos al salir del bloque try
//
// ── TRUCOS SQL ────────────────────────────────────────────────────────────────
//  LOWER(campo) = LOWER(?)        → sin distinción mayúsculas/minúsculas
//  campo LIKE ?  → setString(1, "%" + texto + "%")  → búsqueda parcial (contiene)
//  ORDER BY campo ASC             → A-Z o menor a mayor
//  ORDER BY campo DESC            → Z-A o mayor a menor
//  COUNT(*)                       → contar filas
//  JOIN tabla2 ON t1.col = t2.col → unir tablas
//
// ── CÓDIGOS DE ERROR MySQL ────────────────────────────────────────────────────
//  e.getErrorCode() == 1451  → clave foránea (no puedes borrar, tiene hijos)
//  e.getErrorCode() == 1062  → clave duplicada (ya existe ese valor UNIQUE)
//
// ── TRY-WITH-RESOURCES vs TRY-FINALLY ────────────────────────────────────────
//
//  // CON try-with-resources (RECOMENDADO, cierra solo):
//  try (PreparedStatement stmt = con.prepareStatement(sql);
//       ResultSet rs = stmt.executeQuery()) {
//      while (rs.next()) { ... }
//  } catch (SQLException e) { ... }
//  // stmt y rs se cierran automáticamente aunque haya excepción
//
//  // CON try-finally (forma antigua):
//  PreparedStatement stmt = null;
//  ResultSet rs = null;
//  try {
//      stmt = con.prepareStatement(sql);
//      rs   = stmt.executeQuery();
//      while (rs.next()) { ... }
//  } catch (SQLException e) { ...
//  } finally {
//      if (rs   != null) try { rs.close();   } catch (SQLException e) {}
//      if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
//  }
//
// ── getGeneratedKeys() — obtener el ID creado por AUTO_INCREMENT ──────────────
//
//  String sql = "INSERT INTO critico (nombre) VALUES (?)";
//  try (PreparedStatement stmt = con.prepareStatement(sql,
//           Statement.RETURN_GENERATED_KEYS)) {   // <-- hay que indicarlo aquí
//      stmt.setString(1, "Ana García");
//      stmt.executeUpdate();
//      ResultSet ks = stmt.getGeneratedKeys();
//      if (ks.next()) {
//          int idGenerado = ks.getInt(1); // el ID que asignó MySQL
//      }
//  }
// =============================================================================