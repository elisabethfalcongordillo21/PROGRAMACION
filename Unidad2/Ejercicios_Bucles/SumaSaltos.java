package Unidad2.Ejercicios_Bucles;

import java.util.Scanner;

public class SumaSaltos {
    public static void main(String[] args) {
        
     Scanner teclado = new Scanner(System.in);

        System.out.print("Introduce n: ");
        int n = teclado.nextInt();

        System.out.print("Introduce d: ");
        int d = teclado.nextInt();

        int suma = 0;

        System.out.print("Suma de los múltiplos de " + d + " hasta " + n + ": ");

        for (int i = d; i <= n; i += d) {
            suma += i;
            System.out.print(i);
            if (i + d <= n) {
                System.out.print(" + ");
            }
        }

        System.out.println(" = " + suma);

        teclado.close();   


    }
}
