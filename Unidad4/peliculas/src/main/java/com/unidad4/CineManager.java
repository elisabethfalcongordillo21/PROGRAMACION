package com.unidad4;

import java.util.List;
import java.util.Scanner;

import com.unidad4.model.PeliculaDO;
import com.unidad4.model.PremioDAO;
import com.unidad4.model.PremioDO;

public class CineManager {
    static Scanner scanner = new Scanner(System.in);
    static PremioDAO premioDAO = new PremioDAO();

    public static void main(String[] args) {
     System.out.println("=================================");
        System.out.println("   Bienvenido a CineManager");
        System.out.println("   Gestión de Premios");
        System.out.println("=================================");

        int opcion = -1;

        // Bucle principal del menú
        do {
            mostrarMenu();
            opcion = leerOpcion(1, 5);

            switch (opcion) {
                case 1:
                    anadirPremio();
                    break;
                case 2:
                    modificarPremio();
                    break;
                case 3:
                    menuConsultar();
                    break;
                case 4:
                    eliminarPremio();
                    break;
                case 5:
                    System.out.println("Hasta luego!");
                    break;
            }

        } while (opcion != 5);
    
    
    }

     private static void mostrarMenu() {
        System.out.println("\n--- Gestión de Premios ---");
        System.out.println("1. Añadir premio");
        System.out.println("2. Modificar premio");
        System.out.println("3. Consultar premio");
        System.out.println("4. Eliminar premio");
        System.out.println("5. Salir");
        System.out.print("Opción (1-5): ");
    }
    
    //añadir

    private static void anadirPremio() {
        System.out.println("\n-- Añadir premio --");

        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();
            if (nombre.isEmpty()) {
                System.out.println("Error: el nombre no puede estar vacío.");
                return;
            }

            System.out.print("Categoria: ");
            String categoria = scanner.nextLine().trim();
            if (categoria.isEmpty()) {
                System.out.println("Error: la catgoria no puede estar vacía.");
                return;
            }

            System.out.print("Año del premio: ");
            String anyoStr = scanner.nextLine().trim();
            int anyio_prem = Integer.parseInt(anyoStr); // Lanza excepción si no es número

            boolean ok = premioDAO.anadirPremio(nombre, categoria, anyio_prem);

            if (ok) {
                System.out.println("Premio añadido correctamente.");
            } else {
                System.out.println("No se pudo añadir el premio. Volviendo al menú.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: el año del premio debe ser un número entero. Volviendo al menú.");
        }
    }
    
    // modificar
    private static void modificarPremio(){
         System.out.println("\n-- Modificar premio --");

        try {
            System.out.print("ID del premio a modificar: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            // Buscamos y mostramos los datos actuales
            Object[] premio = premioDAO.buscarPremioPorId(id);
            if (premio == null) {
                System.out.println("No existe ningún premio con ID " + id + ". Volviendo al menú.");
                return;
            }

            System.out.println("Datos actuales: " + premio);

            // Pedimos qué campo modificar
            System.out.print("Campo a modificar (nombre / categoria / anyio_prem): ");
            String campo = scanner.nextLine().trim().toLowerCase();

            if (!campo.equals("nombre") && !campo.equals("categoria") && !campo.equals("anyio_prem")) {
                System.out.println("Campo no válido. Volviendo al menú.");
                return;
            }

            System.out.print("Nuevo valor: ");
            String nuevoValor = scanner.nextLine().trim();

            boolean ok = premioDAO.modificarPremio(id, campo, nuevoValor);

            if (ok) {
                System.out.println("Premio modificado correctamente.");
            } else {
                System.out.println("No se pudo modificar. Volviendo al menú.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: el ID debe ser un número entero. Volviendo al menú.");
        }
    }
    
    // consultar premios con submenu 

    private static void menuConsultar() {
        int subOpcion = -1;

        do {
            menuConsultar();
            subOpcion = leerOpcion(1, 5);

            switch (subOpcion) {
                case 1:
                    obtenerTodosOrdenadosPorAnyio();
                    break;
                case 2:
                    obtenerPorCategoria();
                    break;
                case 3:
                    obtenerPeliculasPremio();
                    break;
                case 4:
                    obtenerPeliculaMas1();
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal...");
                    break;
            }

        } while (subOpcion != 5);
    }
    
    private static void obtenerTodosOrdenadosPorAnyio() {
        System.out.println("\n-- Premios  ordenados por año (más reciente primero) --");
        List<PremioDO> lista = premioDAO.obtenerTodosOrdenadosPorAnyio();
        mostrarLista(lista);
    }
    
     private static void  obtenerPorCategoria( ) {
        System.out.println("\n-- Premios ordenados por categoria --");
        List<PremioDO> lista = premioDAO.obtenerPorCategoria("categoria");
        mostrarLista(lista);
    }
    
     private static void obtenerPeliculasPremio() {
        System.out.print("Id de la pelicula: ");
        String nombre = scanner.nextLine().trim();
        List<String> premio = premioDAO.obtenerPeliculasPremio(nombre);
        if (premio.isEmpty()) {
            System.out.println("La pelicula no tiene premios o no existe.");
        } else {
            System.out.println("\n-- Premios de " + nombre + " --");
            for (String p : premio) {
                System.out.println(p);
            }
        }
    }

    private static void obtenerPeliculaMas1(){
        System.out.print("Nombre de la pelicucla: ");
        int duracion = scanner.nextInt();
        List<PeliculaDO> total = premioDAO.obtenerPeliculaMas1(duracion);
        System.out.println("Número de preliculas con '" + duracion + "': " + total);
    }
    
    // eliminar

    private static void eliminarPremio() {
        System.out.println("\n-- Eliminar premio --");

        try {
            System.out.print("ID del premio a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            boolean ok = premioDAO.eliminarPremio(id);

            if (ok) {
                System.out.println("Premio eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar el premio. Volviendo al menú.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: el ID debe ser un número entero. Volviendo al menú.");
        }
    }
    
    private static int leerOpcion(int min , int max) {
        while (true) {
            try {
                String entrada = scanner.nextLine().trim();
                int opcion1 = Integer.parseInt(entrada);
                if (opcion1 >= min && opcion1 <= max) {
                    return opcion1;
                } else {
                    System.out.print("Opción no válida. Introduce un número entre " + min + " y " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Opción no válida. Introduce un número entre " + min + " y " + max + ": ");
            }
        }
    }
     private static void mostrarLista(List<PremioDO> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay críticos que mostrar.");
        } else {
            for (PremioDO p : lista) {
                System.out.println(p);
            }
        }
    }


}
