package Unidad2;

import java.util.Scanner;

public class ExamenCoches {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        int numCoches = 0;
        String modelo = "", marca = "", categoria = "", color = "";
        int stock = 0, potencia = 0;

        // Contadores
        int contTT = 0, contDep = 0, contUti = 0;

        // Array para deportivos sin stock y potencia > 200
        String listaDepSinStock[];

        // Para Citroen
        int sumaStockCitroen = 0;
        double precio = 0, maxPrecio = Integer.MIN_VALUE;
        int minPotencia = Integer.MAX_VALUE;
        String citroenMasCaro = "", citroenMenosPotente = "";

        // Leer cantidad
        System.out.print("¿Cuántos coches vas a introducir?: ");
        numCoches = teclado.nextInt();
        teclado.nextLine();

        listaDepSinStock = new String[numCoches];

        for (int i = 0; i < numCoches; i++) {

            System.out.println("\nIntroduce datos del coche número " + (i + 1));

            System.out.print("Modelo: ");
            modelo = teclado.nextLine();

            System.out.print("Marca: ");
            marca = teclado.nextLine();

            // Validar categoría
            do {
                System.out.print("Categoría (todoterreno, deportivo, utilitario): ");
                categoria = teclado.next().toLowerCase();
            } while (
                !categoria.equals("todoterreno") &&
                !categoria.equals("deportivo") &&
                !categoria.equals("utilitario")
            );

            System.out.print("Stock: ");
            stock = teclado.nextInt();

            System.out.print("Potencia (cv): ");
            potencia = teclado.nextInt();

            // Validar color
            do {
                System.out.print("Color (rojo, blanco, negro, azul): ");
                color = teclado.next().toLowerCase();
            } while (
                !color.equals("rojo") &&
                !color.equals("blanco") &&
                !color.equals("negro") &&
                !color.equals("azul")
            );

            teclado.nextLine(); // limpiar buffer

            // -------------------------------
            // APLICAR LAS CONDICIONES
            // -------------------------------

            // 1. Contadores por categoría
            if (categoria.equals("deportivo")) {
                contDep++;

                if (stock == 0 && potencia > 200) {
                    listaDepSinStock[i] = modelo;
                }
            }
            else if (categoria.equals("todoterreno")) {
                contTT++;
            }
            else {
                contUti++;
            }

            // 2. Coches entre 60 y 100cv y color rojo/negro/amarillo
            if ((potencia >= 60 && potencia <= 100) &&
                (color.equals("rojo") || color.equals("negro") || color.equals("amarillo"))) {
                
                System.out.println(">> COCHE ENTRE 60 Y 100 CV:");
                System.out.println("Modelo: " + modelo + " | Marca: " + marca);
            }

            // 3. Datos para Citroen
            if (marca.equalsIgnoreCase("citroen")) {

                sumaStockCitroen += stock;

                System.out.print("Introduce precio del Citroen: ");
                precio = teclado.nextDouble();
                teclado.nextLine();

                if (precio > maxPrecio) {
                    maxPrecio = precio;
                    citroenMasCaro = modelo;
                }

                if (potencia < minPotencia) {
                    minPotencia = potencia;
                    citroenMenosPotente = modelo;
                }
            }
        }

        // -------------------------------
        // RESULTADOS
        // -------------------------------
        System.out.println("\n=========== RESULTADOS ===========");

        System.out.println("\nDeportivos sin stock y potencia > 200:");
        for (int i = 0; i < listaDepSinStock.length; i++) {
            if (listaDepSinStock[i] != null) {
                System.out.println(listaDepSinStock[i]);
            }
        }

        System.out.println("\nCantidad de coches por categoría:");
        System.out.println("TodoTerreno: " + contTT);
        System.out.println("Deportivo: " + contDep);
        System.out.println("Utilitario: " + contUti);

        System.out.println("\nStock total de Citroen: " + sumaStockCitroen);

        if (maxPrecio == Integer.MIN_VALUE) {
            System.out.println("No se introdujeron coches Citroen.");
        } else {
            System.out.println("Citroen más caro: " + citroenMasCaro + " (" + maxPrecio + "€)");
            System.out.println("Citroen menos potente: " + citroenMenosPotente + " (" + minPotencia + "cv)");
        }

        teclado.close();
    }
}
