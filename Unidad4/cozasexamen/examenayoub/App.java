
import java.util.List;
import java.util.Scanner;

import com.unidad4.model.CriticoDO;
import com.unidad4.model.CriticoDAO;

/**
 * Aplicación principal: Gestión de Críticos de Cine (CineManager)
 * Examen práctico Java con JDBC
 */
public class App {

    static Scanner scanner = new Scanner(System.in);
    static CriticoDAO criticoDAO = new CriticoDAO();

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   Bienvenido a CineManager");
        System.out.println("   Gestión de Críticos");
        System.out.println("=================================");

        int opcion = -1;

        // Bucle principal del menú
        do {
            mostrarMenuPrincipal();
            opcion = leerOpcion(1, 5);

            switch (opcion) {
                case 1:
                    añadirCritico();
                    break;
                case 2:
                    modificarCritico();
                    break;
                case 3:
                    menuConsultar();
                    break;
                case 4:
                    eliminarCritico();
                    break;
                case 5:
                    System.out.println("Hasta luego!");
                    break;
            }

        } while (opcion != 5);
    }

    // ─────────────────────────────────────────────
    // MENÚS
    // ─────────────────────────────────────────────

    private static void mostrarMenuPrincipal() {
        System.out.println("\n--- Gestión de Críticos ---");
        System.out.println("1. Añadir crítico");
        System.out.println("2. Modificar crítico");
        System.out.println("3. Consultar críticos");
        System.out.println("4. Eliminar crítico");
        System.out.println("5. Salir");
        System.out.print("Opción (1-5): ");
    }

    private static void mostrarMenuConsultar() {
        System.out.println("\n--- Consultar críticos ---");
        System.out.println("1. Mostrar todos ordenados por nombre (A-Z)");
        System.out.println("2. Mostrar todos ordenados por año de inicio (mayor a menor)");
        System.out.println("3. Mostrar críticos por medio");
        System.out.println("4. Mostrar reseñas de un crítico");
        System.out.println("5. Mostrar número de críticos de un medio");
        System.out.println("6. Volver");
        System.out.print("Opción (1-6): ");
    }

    // ─────────────────────────────────────────────
    // OPCIÓN 1 – AÑADIR
    // ─────────────────────────────────────────────

    private static void añadirCritico() {
        System.out.println("\n-- Añadir crítico --");

        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("Error: el nombre no puede estar vacío.");
                return;
            }

            System.out.print("Medio (periódico/revista/web): ");
            String medio = scanner.nextLine().trim();
            if (medio.isEmpty()) {
                System.out.println("Error: el medio no puede estar vacío.");
                return;
            }

            System.out.print("Año de inicio: ");
            String anyoStr = scanner.nextLine().trim();
            int anyo = Integer.parseInt(anyoStr); // Lanza excepción si no es número

            boolean ok = criticoDAO.añadirCritico(nombre, medio, anyo);

            if (ok) {
                System.out.println("Crítico añadido correctamente.");
            } else {
                System.out.println("No se pudo añadir el crítico. Volviendo al menú.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: el año de inicio debe ser un número entero. Volviendo al menú.");
        }
    }

    // ─────────────────────────────────────────────
    // OPCIÓN 2 – MODIFICAR
    // ─────────────────────────────────────────────

    private static void modificarCritico() {
        System.out.println("\n-- Modificar crítico --");

        try {
            System.out.print("ID del crítico a modificar: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            // Buscamos y mostramos los datos actuales
            CriticoDO critico = criticoDAO.buscarPorId(id);
            if (critico == null) {
                System.out.println("No existe ningún crítico con ID " + id + ". Volviendo al menú.");
                return;
            }

            System.out.println("Datos actuales: " + critico);

            // Pedimos qué campo modificar
            System.out.print("Campo a modificar (nombre / medio / anyo_inicio): ");
            String campo = scanner.nextLine().trim().toLowerCase();

            if (!campo.equals("nombre") && !campo.equals("medio") && !campo.equals("anyo_inicio")) {
                System.out.println("Campo no válido. Volviendo al menú.");
                return;
            }

            System.out.print("Nuevo valor: ");
            String nuevoValor = scanner.nextLine().trim();

            boolean ok = criticoDAO.modificarCritico(id, campo, nuevoValor);

            if (ok) {
                System.out.println("Crítico modificado correctamente.");
            } else {
                System.out.println("No se pudo modificar. Volviendo al menú.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: el ID debe ser un número entero. Volviendo al menú.");
        }
    }

    // ─────────────────────────────────────────────
    // OPCIÓN 3 – CONSULTAR (submenú)
    // ─────────────────────────────────────────────

    private static void menuConsultar() {
        int subOpcion = -1;

        do {
            mostrarMenuConsultar();
            subOpcion = leerOpcion(1, 6);

            switch (subOpcion) {
                case 1:
                    consultarOrdenadosPorNombre();
                    break;
                case 2:
                    consultarOrdenadosPorAnyo();
                    break;
                case 3:
                    consultarPorMedio();
                    break;
                case 4:
                    consultarResenyasCritico();
                    break;
                case 5:
                    contarCriticosPorMedio();
                    break;
                case 6:
                    System.out.println("Volviendo al menú principal...");
                    break;
            }

        } while (subOpcion != 6);
    }

    private static void consultarOrdenadosPorNombre() {
        System.out.println("\n-- Críticos ordenados por nombre (A-Z) --");
        List<CriticoDO> lista = criticoDAO.obtenerTodosOrdenadosPorNombre();
        mostrarLista(lista);
    }

    private static void consultarOrdenadosPorAnyo() {
        System.out.println("\n-- Críticos ordenados por año de inicio (mayor a menor) --");
        List<CriticoDO> lista = criticoDAO.obtenerTodosOrdenadosPorAnyo();
        mostrarLista(lista);
    }

    private static void consultarPorMedio() {
        System.out.print("Nombre del medio (ej: El País): ");
        String medio = scanner.nextLine().trim();
        List<CriticoDO> lista = criticoDAO.obtenerPorMedio(medio);
        if (lista.isEmpty()) {
            System.out.println("No se encontraron críticos en el medio: " + medio);
        } else {
            mostrarLista(lista);
        }
    }

    private static void consultarResenyasCritico() {
        System.out.print("Nombre del crítico: ");
        String nombre = scanner.nextLine().trim();
        List<String> resenyas = criticoDAO.obtenerResenyas(nombre);
        if (resenyas.isEmpty()) {
            System.out.println("El crítico no tiene reseñas o no existe.");
        } else {
            System.out.println("\n-- Reseñas de " + nombre + " --");
            for (String r : resenyas) {
                System.out.println(r);
            }
        }
    }

    private static void contarCriticosPorMedio() {
        System.out.print("Nombre del medio: ");
        String medio = scanner.nextLine().trim();
        int total = criticoDAO.contarCriticosPorMedio(medio);
        System.out.println("Número de críticos en '" + medio + "': " + total);
    }

    // ─────────────────────────────────────────────
    // OPCIÓN 4 – ELIMINAR
    // ─────────────────────────────────────────────

    private static void eliminarCritico() {
        System.out.println("\n-- Eliminar crítico --");

        try {
            System.out.print("ID del crítico a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            boolean ok = criticoDAO.eliminarCritico(id);

            if (ok) {
                System.out.println("Crítico eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar el crítico. Volviendo al menú.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: el ID debe ser un número entero. Volviendo al menú.");
        }
    }

    // ─────────────────────────────────────────────
    // UTILIDADES
    // ─────────────────────────────────────────────

    /**
     * Lee una opción del usuario y valida que esté entre min y max.
     * Si no es válida, vuelve a pedirla.
     */
    private static int leerOpcion(int min, int max) {
        while (true) {
            try {
                String entrada = scanner.nextLine().trim();
                int opcion = Integer.parseInt(entrada);
                if (opcion >= min && opcion <= max) {
                    return opcion;
                } else {
                    System.out.print("Opción no válida. Introduce un número entre " + min + " y " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Opción no válida. Introduce un número entre " + min + " y " + max + ": ");
            }
        }
    }

    /**
     * Muestra una lista de críticos por pantalla.
     */
    private static void mostrarLista(List<CriticoDO> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay críticos que mostrar.");
        } else {
            for (CriticoDO c : lista) {
                System.out.println(c);
            }
        }
    }
}