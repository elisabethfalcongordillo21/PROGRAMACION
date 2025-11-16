package Unidad2;

import java.util.Scanner;

public class ExamenActividades {
    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        int numActividades = 0;
        String nombre = "", ubicacion = "", tipo = "";
        int plazas = 0, duracion = 0;
        String dificultad = "";

        // Contadores de dificultad
        int contFacil = 0, contMedio = 0, contDificil = 0;

        // Array para actividades culturales con exceso de plazas
        String listaCulturales[];

        // Variables para Sevilla
        int sumaPlazasSevilla = 0;
        int minDuracion = Integer.MAX_VALUE;
        String nombreMinDuracion = "";
        String nombreMayorDificultad = "";
        int nivelMax = 0; // Fácil=1, Medio=2, Difícil=3

        // Leemos cantidad
        System.out.print("¿Cuántas actividades vas a introducir?: ");
        numActividades = teclado.nextInt();
        teclado.nextLine();

        listaCulturales = new String[numActividades];

        for (int i = 0; i < numActividades; i++) {

            System.out.println("\nIntroduce los datos de la actividad número " + (i + 1));

            System.out.print("Nombre: ");
            nombre = teclado.nextLine();

            System.out.print("Ubicación: ");
            ubicacion = teclado.nextLine();

            // Validar tipo
            do {
                System.out.print("Tipo (acuática, deportiva, cultural): ");
                tipo = teclado.next().toLowerCase();
            } while (
                !tipo.equals("acuática") &&
                !tipo.equals("deportiva") &&
                !tipo.equals("cultural")
            );

            System.out.print("Plazas disponibles: ");
            plazas = teclado.nextInt();

            System.out.print("Duración en horas: ");
            duracion = teclado.nextInt();

            // Validar dificultad
            do {
                System.out.print("Dificultad (fácil, medio, difícil): ");
                dificultad = teclado.next().toLowerCase();
            } while (
                !dificultad.equals("fácil") &&
                !dificultad.equals("medio") &&
                !dificultad.equals("difícil")
            );

            teclado.nextLine(); // limpiar buffer

            // -----------------------------------------------------
            // 1. Actividades culturales con >15 plazas y <2 horas
            // -----------------------------------------------------
            if (tipo.equals("cultural") && plazas > 15 && duracion < 2) {
                listaCulturales[i] = nombre;
            }

            // -----------------------------------------------------
            // 2. Contar por dificultad
            // -----------------------------------------------------
            if (dificultad.equals("fácil")) contFacil++;
            else if (dificultad.equals("medio")) contMedio++;
            else contDificil++;

            // -----------------------------------------------------
            // 3. Duración >4 y tipo acuática/deportiva O ubic. Leon
            // -----------------------------------------------------
            if ((duracion > 4 && (tipo.equals("acuática") || tipo.equals("deportiva")))
                || ubicacion.equalsIgnoreCase("leon")) {

                System.out.println(">> Actividad especial:");
                System.out.println("Nombre: " + nombre + " | Ubicación: " + ubicacion);
            }

            // -----------------------------------------------------
            // 4. Actividades de Sevilla
            // -----------------------------------------------------
            if (ubicacion.equalsIgnoreCase("sevilla")) {

                sumaPlazasSevilla += plazas;

                // Actividad de menor duración
                if (duracion < minDuracion) {
                    minDuracion = duracion;
                    nombreMinDuracion = nombre;
                }

                // Actividad de mayor dificultad
                int nivelActual = 0;
                if (dificultad.equals("fácil")) nivelActual = 1;
                else if (dificultad.equals("medio")) nivelActual = 2;
                else nivelActual = 3;

                if (nivelActual > nivelMax) {
                    nivelMax = nivelActual;
                    nombreMayorDificultad = nombre;
                }
            }

        }

        // ---------------------------
        // RESULTADOS FINALES
        // ---------------------------
        System.out.println("\n========== RESULTADOS ==========");

        System.out.println("\n1. Actividades culturales con exceso de plazas:");
        for (int i = 0; i < listaCulturales.length; i++) {
            if (listaCulturales[i] != null) {
                System.out.println(listaCulturales[i]);
            }
        }

        System.out.println("\n2. Cantidad por nivel de dificultad:");
        System.out.println("Fácil: " + contFacil);
        System.out.println("Medio: " + contMedio);
        System.out.println("Difícil: " + contDificil);

        System.out.println("\n4. Resultados Sevilla:");
        System.out.println("Plazas totales: " + sumaPlazasSevilla);

        if (minDuracion == Integer.MAX_VALUE) {
            System.out.println("No hubo actividades en Sevilla.");
        } else {
            System.out.println("Actividad con menor duración: " + nombreMinDuracion);
            System.out.println("Actividad con mayor dificultad: " + nombreMayorDificultad);
        }

        teclado.close();
    }
}
