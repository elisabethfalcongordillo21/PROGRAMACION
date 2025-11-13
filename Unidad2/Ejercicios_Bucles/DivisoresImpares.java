package Unidad2.Ejercicios_Bucles;

import java.util.Scanner;

public class DivisoresImpares {
public static void main(String[] args) {
    
    Scanner teclado = new Scanner(System.in);

        System.out.print("Introduce un número: ");
        int numero = teclado.nextInt();

        System.out.println("Divisores impares de " + numero + ":");

        for (int i = 1; i <= numero; i += 2) { // solo impares
            if (numero % i == 0) { // si i divide al número
                System.out.println(i);
            }
        }

        teclado.close();
}
}
