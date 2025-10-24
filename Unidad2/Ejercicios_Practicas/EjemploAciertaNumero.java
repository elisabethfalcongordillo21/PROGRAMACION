package Unidad2.Ejercicios_Practicas;

import java.util.Scanner;

public class EjemploAciertaNumero {
    
    public static void main(String[] args) {
      
      //DECLARACION DE VARIABLES
      
        int numBuscado = (int) (Math.random()*100) +1;
        int numJugado = 0;
        int numIntentos= 5;
        boolean encontrado=false;
       //Creamos un objeto de tipo scanner para leer por consola 
        
        Scanner teclado = new Scanner(System.in);
        
        
        
        while (numIntentos>0)   
        {
            System.out.println("Prueba suerte, tienes " + numIntentos + "intentos restantes");
            numJugado = teclado.nextInt();

            //Si el numereo que ha introducido es el buscado
            if (numJugado==numBuscado) 
            {
                System.out.println("Has acertado");
                encontrado=true;//o opcion break para salir del bucle
            }else if (numJugado>numBuscado) {
                System.out.println("El numero  buscado es menor");
            } else  {
                System.out.println("El numero buscado es mayor");
            }

            //Pasar al siguiente elemento
            numIntentos--;
        }
        //Al final sacamos un mensaje 
        if (!encontrado) System.out.println("Lo siento el numero era:" + numBuscado);

        teclado.close();
    }
}
