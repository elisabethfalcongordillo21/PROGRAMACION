package Unidad2.Ejercicios_Practicas;

import java.util.Scanner;


public class Ejercicio10 {
    
    public static void main(String[] args) {
     int num1=0, num2=0;
     double resultado=0;
     Scanner teclado = new Scanner (System.in);
    
    System.out.println("Introduzca el primer numero: " );
    num1=teclado.nextInt();

    System.out.println("Introduzca el segundo numero: ");
    num2=teclado.nextInt();

    System.out.println("Introduzca el resultado obtenido al realizar una operacion: ");
    resultado=teclado.nextInt();

    if (num1+num2==resultado) {
        System.out.println("La operación realizada es una suma");
    }

    else  if (num1-num2==resultado) {
        System.out.println("La operación realizada es una resta");
    }

   else  if (num1*num2==resultado) {
        System.out.println("La operación realizada es una multiplicación");
    }

   else if (num1/num2==resultado) {
        System.out.println("La operación realizada es una división");
    }

     else if (num1%num2==resultado) {
        System.out.println("La operación realizada es el resto");
    }

   else if (num2==2) {
        if (Math.sqrt(num1)==resultado) {
           System.out.println("La operación realizada es la raiz cuadrada"); 
        }
        if ((num1^2)==resultado) {
            System.out.println("La operación realizada es la potencia de 2");
        }
    }
    else{
        System.out.println("La operación no está definida");
    }   
    
    
    teclado.close();
}


}
