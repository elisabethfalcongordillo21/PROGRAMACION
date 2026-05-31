package com.unidad4;

import java.util.List;
import java.util.Scanner;

import com.unidad4.model.DirectorDO;
import com.unidad4.model.DirectorDAO;

/**
 * Aplicación principal: Gestión de Directores de Cine (CineManager)
 * Examen práctico Java con JDBC - Examen A
 */
public class App {

    static Scanner scanner = new Scanner(System.in);
    static DirectorDAO directorDAO = new DirectorDAO();

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   Bienvenido a CineManager");
        System.out.println("   Gestión de Directores");
        System.out.println("=================================");

        int opcion = -1;

        do {
            mostrarMenuPrincipal();
            opcion = leerOpcion(1, 5);

            switch (opcion) {
                case 1: añadirDirector();   break;
                case 2: modificarDirector(); break;
                case 3: menuConsultar();     break;
                case 4: eliminarDirector();  break;
                case 5: System.out.println("Hasta luego!"); break;
            }

        } while (opcion != 5);
    }

    // ─────────────────────────────────────────────
    // MENÚS
    // ─────────────────────────────────────────────

    private static void mostrarMenuPrincipal() {
        System.out.println("\n--- Gestión de Directores ---");
        System.out.println("1. Añadir director");
        System.out.println("2. Modificar director");
        System.out.println("3. Consultar directores");
        System.out.println("4. Eliminar director");
        System.out.println("5. Salir");
        System.out.print("Opción (1-5): ");
    }

    private static void mostrarMenuConsultar() {
        System.out.println("\n--- Consultar directores ---");
        System.out.println("1. Mostrar todos ordenados por nombre (A-Z)");
        System.out.println("2. Mostrar todos ordenados por año de nacimiento (mayor a menor)");
        System.out.println("3. Mostrar directores por nacionalidad");
        System.out.println("4. Mostrar películas de un director");
        System.out.println("5. Volver");
        System.out.print("Opción (1-5): ");
    }

    // ─────────────────────────────────────────────
    // OPCIÓN 1 – AÑADIR
    // ─────────────────────────────────────────────

    private static void añadirDirector() {
        System.out.println("\n-- Añadir director --");
        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("Error: el nombre no puede estar vacío.");
                return;
            }

            System.out.print("Nacionalidad: ");
            String nacionalidad = scanner.nextLine().trim();
            if (nacionalidad.isEmpty()) {
                System.out.println("Error: la nacionalidad no puede estar vacía.");
                return;
            }

            System.out.print("Año de nacimiento: ");
            int anyo = Integer.parseInt(scanner.nextLine().trim());

            boolean ok = directorDAO.añadirDirector(nombre, nacionalidad, anyo);
            if (ok) {
                System.out.println("Director añadido correctamente.");
            } else {
                System.out.println("No se pudo añadir el director. Volviendo al menú.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: el año debe ser un número entero. Volviendo al menú.");
        }
    }

    // ─────────────────────────────────────────────
    // OPCIÓN 2 – MODIFICAR
    // ─────────────────────────────────────────────

    private static void modificarDirector() {
        System.out.println("\n-- Modificar director --");
        try {
            System.out.print("ID del director a modificar: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            DirectorDO director = directorDAO.buscarPorId(id);
            if (director == null) {
                System.out.println("No existe ningún director con ID " + id + ". Volviendo al menú.");
                return;
            }

            System.out.println("Datos actuales: " + director);

            System.out.print("Campo a modificar (nombre / nacionalidad / anyo_nacimiento): ");
            String campo = scanner.nextLine().trim().toLowerCase();

            if (!campo.equals("nombre") && !campo.equals("nacionalidad") && !campo.equals("anyo_nacimiento")) {
                System.out.println("Campo no válido. Volviendo al menú.");
                return;
            }

            System.out.print("Nuevo valor: ");
            String nuevoValor = scanner.nextLine().trim();

            boolean ok = directorDAO.modificarDirector(id, campo, nuevoValor);
            if (ok) {
                System.out.println("Director modificado correctamente.");
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
            subOpcion = leerOpcion(1, 5);

            switch (subOpcion) {
                case 1: consultarOrdenadosPorNombre();   break;
                case 2: consultarOrdenadosPorAnyo();     break;
                case 3: consultarPorNacionalidad();      break;
                case 4: consultarPeliculasDeDirector();  break;
                case 5: System.out.println("Volviendo al menú principal..."); break;
            }
        } while (subOpcion != 5);
    }

    private static void consultarOrdenadosPorNombre() {
        System.out.println("\n-- Directores ordenados por nombre (A-Z) --");
        mostrarLista(directorDAO.obtenerTodosOrdenadosPorNombre());
    }

    private static void consultarOrdenadosPorAnyo() {
        System.out.println("\n-- Directores ordenados por año de nacimiento (mayor a menor) --");
        mostrarLista(directorDAO.obtenerTodosOrdenadosPorAnyo());
    }

    private static void consultarPorNacionalidad() {
        System.out.print("Nacionalidad (ej: Española): ");
        String nacionalidad = scanner.nextLine().trim();
        List<DirectorDO> lista = directorDAO.obtenerPorNacionalidad(nacionalidad);
        if (lista.isEmpty()) {
            System.out.println("No se encontraron directores con nacionalidad: " + nacionalidad);
        } else {
            mostrarLista(lista);
        }
    }

    private static void consultarPeliculasDeDirector() {
        System.out.print("Nombre del director: ");
        String nombre = scanner.nextLine().trim();
        List<String> peliculas = directorDAO.obtenerPeliculasDeDirector(nombre);
        if (peliculas.isEmpty()) {
            System.out.println("El director no tiene películas asociadas o no existe.");
        } else {
            System.out.println("\n-- Películas de " + nombre + " --");
            for (String p : peliculas) {
                System.out.println(p);
            }
        }
    }

    // ─────────────────────────────────────────────
    // OPCIÓN 4 – ELIMINAR
    // ─────────────────────────────────────────────

    private static void eliminarDirector() {
        System.out.println("\n-- Eliminar director --");
        try {
            System.out.print("ID del director a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            boolean ok = directorDAO.eliminarDirector(id);
            if (ok) {
                System.out.println("Director eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar el director. Volviendo al menú.");
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
     */
    private static int leerOpcion(int min, int max) {
        while (true) {
            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion >= min && opcion <= max) return opcion;
                System.out.print("Opción no válida. Introduce un número entre " + min + " y " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Opción no válida. Introduce un número entre " + min + " y " + max + ": ");
            }
        }
    }

    /**
     * Muestra una lista de directores por pantalla.
     */
    private static void mostrarLista(List<DirectorDO> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay directores que mostrar.");
        } else {
            for (DirectorDO d : lista) {
                System.out.println(d);
            }
        }
    }
}