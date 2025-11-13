package Unidad2.Ejercicios_Bucles;

import java.util.Scanner;

public class HundirLaFlota {
    public static void main(String[] args) {
        
         Scanner teclado = new Scanner(System.in);

        // Tablero 8x8: 0 = agua, 1 = barco
        int[][] tablero = {
            {0,0,0,0,1,0,0,0},
            {0,1,0,0,0,0,1,0},
            {0,0,0,1,0,0,0,0},
            {0,0,1,0,0,1,0,0},
            {1,0,0,0,0,0,0,0},
            {0,0,0,0,1,0,0,1},
            {0,1,0,0,0,0,0,0},
            {0,0,0,1,0,0,0,0}
        };

        // Contar barcos
        int barcos = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tablero[i][j] == 1) {
                    barcos++;
                }
            }
        }
        System.out.println("Número de barcos en el tablero: " + barcos);

        while (true) {
            System.out.print("Introduce fila (1-8) o 0 para salir: ");
            int fila = teclado.nextInt();
            if (fila == 0) break;

            System.out.print("Introduce columna (1-8): ");
            int col = teclado.nextInt();

            // Validar coordenadas
            if (fila < 1 || fila > 8 || col < 1 || col > 8) {
                System.out.println("Coordenadas inválidas, intenta de nuevo.");
                continue;
            }

            // Ajustar a índice 0-7
            int f = fila - 1;
            int c = col - 1;

            if (tablero[f][c] == 1) {
                System.out.println("¡Tocado!");
                tablero[f][c] = 2; // marcar como tocado
            } else if (tablero[f][c] == 0) {
                System.out.println("Agua!");
                tablero[f][c] = 3; // marcar agua disparada
            } else {
                System.out.println("Ya disparaste ahí, prueba en otra coordenada.");
            }
        }

        System.out.println("Juego terminado.");
        teclado.close();

    }
}
