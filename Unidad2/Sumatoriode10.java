package Unidad2;

/**
 * Sacar por pantalla el sumatorio de todos los numeros desde 1 hasta 10
 * siguiendo el siguiente patron
 * suma hasta 1= 1
 * suma hasta 2= 1+2= 3
 * suma hasta 3= 1+2+3 = 6
 * ....
 */

public class Sumatoriode10 {
    public static void main(String[] args) {
        
        
       int suma=0;
        
        for(int i=1; i<11;i++)
        {
            System.out.println("La suma de " + i + ": ");
            
            //para cada calculo de sumatorio reseteamos la suma anterior

            suma = 1;

            //para cada valor de i vamos a realizar la suma 
            //desde 1 hasta i

            for(int j=1;j<=i;j++)
            {
                //vamos mostrando todos los numeros que se suman
                System.out.println(j);
                //mostramos un signo despues de cada numero
                //excepto el ultimo que ya no va a tener mas numeros detras
                if (j!=i) System.out.println("+");
               //vamos sumando en la variable suma
               //los valores de j que van desde 1 hasta la i actual
                suma=suma+j;
            }
            
            System.out.println(suma);

        }
        





    }





}
