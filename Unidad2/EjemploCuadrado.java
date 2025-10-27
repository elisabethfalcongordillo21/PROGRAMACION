package Unidad2;

import java.util.Scanner;

public class EjemploCuadrado {
    public static void main(String[] args) {
        
        /**
         * El programa debera dibujar un cuadrado
         * Habra 2 variantes, la primera solo un cuadrado de x espacios de lado
         * La segunda igual pero hueco
         */

        Scanner teclado=new Scanner(System.in);
        int tamCuadrado = 0;

        System.out.println("¿De que tamaño quieres el cuadrado? ");

        tamCuadrado= teclado.nextInt();

        for(int j =0; j<tamCuadrado; j++){

            for(int i =0; i<tamCuadrado; i++){

                System.out.print("*");
            }
            System.out.println();
        }

        //ahora version  ahuecado 

         for(int j =0; j<tamCuadrado; j++){

            if (j ==0 || j == (tamCuadrado - 1)) {
               for(int i =0; i<tamCuadrado; i++){

                System.out.print("*");
            }  
            }else {
                System.out.print("*");
                for(int k = 0; k<tamCuadrado-2; k++){
                 System.out.print(" ");

                }
                System.out.print("*");
            }


           
            System.out.println();
        }


            teclado.close();

    }
}
