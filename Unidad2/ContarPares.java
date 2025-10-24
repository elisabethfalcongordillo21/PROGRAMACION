package Unidad2;

/**
 * El programa generara 20 nunmeros aleatorios y verificara si son pares o impares
 * mostrara un mensaje del tipo el numero x es par/impar
 * despues sacaremos la cantidad de cada tipo y el porcentaje
 */

public class ContarPares {
    public static void main(String[] args) {
    
        //Contadores para la cantidad de numeros pares e impares
        int numPares=0, numImpares=0;
      //Creamos un bucle for que se repita 20 veces
        for (int i = 0; i <= 19; i++){
        //Creamos un numero aleatorio entre 1 y 100
         int numAleatorio = (int) (Math.random()*100+1); 
         //Si el resto de la division entera entre 2 es 0 implica que es par
         if (numAleatorio %2 ==0 ) {
            System.out.println("El número " +  numAleatorio + " es par");
            numPares++;
        }

         else {
            System.out.println("El número " + numAleatorio + " es impar");
            numImpares++;
            
        }
        
        }

        System.out.println("El número de pares es: " +  numPares + "y el número de impares es: " + numImpares);
    
        System.out.println("El porcentaje de pares ha sido " + (numPares*100/20));
        System.out.println("El porcentaje de pares ha sido " + (numImpares*100/20));

    }
    
}
