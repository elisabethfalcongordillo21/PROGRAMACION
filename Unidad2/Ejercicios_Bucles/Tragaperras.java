package Unidad2.Ejercicios_Bucles;

import java.util.Scanner;
import java.util.Random;

public class Tragaperras {
   public static void main(String[] args) {
    
    Scanner teclado = new Scanner(System.in);
    Random rand = new Random();

    // Valores disponibles en los rodillos
        int[] valores = {1, 2, 3, 4, 5, 7};

        int monedas = 100;

        System.out.println("¡Bienvenido a la máquina tragaperras!");
        System.out.println("Empiezas con 100 monedas. Cada tirada cuesta 5 monedas.");

        while (monedas >= 5) {
            System.out.print("Escribe ENTER para tirar o 'salir' para terminar: ");
            String entrada = teclado.nextLine();

            if (entrada.equalsIgnoreCase("salir")) {
                break;
            }

            // Restar el coste de la tirada
            monedas -= 5;

            // Tirar los 3 rodillos
            int[] tirada = new int[3];
            for (int i = 0; i < 3; i++) {
                tirada[i] = valores[rand.nextInt(valores.length)];
            }

            // Mostrar el resultado
            System.out.println("Resultado: " + tirada[0] + " | " + tirada[1] + " | " + tirada[2]);

            // Comprobar premios
            int premio = 0;
            if (tirada[0] == tirada[1] && tirada[1] == tirada[2]) {
                premio = tirada[0] * 10;
                System.out.println("¡3 iguales! Ganaste " + premio + " monedas.");
            } else if (tirada[0] == tirada[1] || tirada[0] == tirada[2]) {
                premio = tirada[0] * 2;
                System.out.println("¡2 iguales! Ganaste " + premio + " monedas.");
            } else if (tirada[1] == tirada[2]) {
                premio = tirada[1] * 2;
                System.out.println("¡2 iguales! Ganaste " + premio + " monedas.");
            } else {
                System.out.println("No ganaste esta tirada.");
            }

            monedas += premio;
            System.out.println("Monedas restantes: " + monedas);
        }

        System.out.println("Juego terminado. Te quedan " + monedas + " monedas.");
        teclado.close();


   } 
}
