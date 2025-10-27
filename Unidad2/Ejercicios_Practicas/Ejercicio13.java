package Unidad2.Ejercicios_Practicas;

import java.util.Scanner;

public class Ejercicio13 {
    public static void main(String[] args) {
        
        //Definir variables

        float cantidad;
        float origen;
        float destino;
        float conversion;
        Scanner teclado = new Scanner(System.in);

        //Preguntar por pantalla
        System.out.println("Introduce cual es la moneda de origen: 1 (Euro), 2 (Dolar) o 3 (Peso Argentino)");
        origen= teclado.nextFloat();
        System.out.println("Introduce la cantidad deseada a convertir: "); 
        cantidad = teclado.nextFloat();
        System.out.println("Introduce cual es la moneda de destino:1 (Euro), 2 (Dolar) o 3 (Peso Argentino)");
        destino= teclado.nextFloat();

        //Establecer condiciones

        if (origen==1) {
            
            if (destino==2) {
                conversion=1.16f;
                cantidad=(cantidad * conversion);
                System.out.println("El total de la conversion es: " + String.format("%.2f", cantidad ));
            }
            if (destino==3) {
                conversion=1638f;
                cantidad=(cantidad * conversion);
                System.out.println("El total de la conversion es: " + String.format("%.2f", cantidad ));
            }
        }
         if (origen==2) {
            
            if (destino==1) {
                conversion=0.86f;
                cantidad=(cantidad * conversion);
                System.out.println("El total de la conversion es: " + String.format("%.2f", cantidad ));
            }
            if (destino==3) {
                conversion=1638f;
                cantidad=(cantidad * conversion);
                System.out.println("El total de la conversion es: " + String.format("%.2f", cantidad ));
            }
        }
         if (origen==3) {
            
            if (destino==2) {
                conversion=0.00067f;
                cantidad=(cantidad * conversion);
                System.out.println("El total de la conversion es: " + String.format("%.2f", cantidad ));
            }
            if (destino==1) {
                conversion=0.00058f;
                cantidad=(cantidad * conversion);
                System.out.println("El total de la conversion es: " + String.format("%.2f", cantidad ));
            }
        }


        teclado.close();
    }

}
