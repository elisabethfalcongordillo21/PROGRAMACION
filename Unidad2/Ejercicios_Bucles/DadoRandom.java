package Unidad2.Ejercicios_Bucles;

import java.util.Scanner;
import java.util.Random;


public class DadoRandom {
   public static void main(String[] args) {
    
        Scanner teclado = new Scanner(System.in);
        Random rand = new Random();

        // Pedir al usuario el número de dados y caras
        System.out.print("¿Cuantos dados quieres lanzar? ");
        int numDados = teclado.nextInt();

        System.out.print("¿Cuantas caras tiene cada dado? ");
        int numCaras = teclado.nextInt();

        int sumaTotal = 0;
        int criticos = 0;
        boolean tiradaEpica = true;
        int primerDado = 0;

        // Lanzar los dados
        for (int i = 1; i <= numDados; i++) {
            int tirada = rand.nextInt(numCaras) + 1; // valor entre 1 y numCaras
            System.out.println("Dado " + i + ": " + tirada);

            // Guardar la primera tirada para comparar luego
            if (i == 1) {
                primerDado = tirada;
            } else if (tirada != primerDado) {
                tiradaEpica = false;
            }

            // Sumar al total
            sumaTotal += tirada;

            // Contar críticos
            if (tirada == numCaras) {
                criticos++;
            }
        }

        // Mostrar resultados
        System.out.println("Suma total: " + sumaTotal);
        System.out.println("Criticos: " + criticos);
        if (tiradaEpica) {
            System.out.println("¡Tirada epica! Todos los dados sacaron el mismo número.");
        } else {
            System.out.println("No es una tirada épica.");
        }

        teclado.close();

   } 
}
