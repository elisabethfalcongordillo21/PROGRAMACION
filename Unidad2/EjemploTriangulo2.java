package Unidad2;

import java.util.Scanner;

public class EjemploTriangulo2 {
    public static void main(String[] args) {
        
        Scanner teclado=new Scanner(System.in);
        int baseTriangulo = 0;
        System.out.println("Dime de que tamaño es la base del triángulo: ");
        baseTriangulo= teclado.nextInt();

        //Dibujamos cada nivel del triangulo, en total hay baseTriangulo veces niveles
        for(int i =0 ; i < baseTriangulo; i++)
        {
            //Dibujamos los espacios en blanco para centrar el triangulo
            for (int x = 0; x < baseTriangulo - ( i + 1 ); x++)
            {
                System.out.print(" ");
            }

           //En cada nivel tengo que dibujar 1+2*i asteriscos
           for (int j = 0; j < 1 + 2 * i  ; j++);
           {
            System.out.print("*");
           } 
        
           System.out.println();
        }
        teclado.close();
    }
}
