package Unidad3;

import java.util.ArrayList;

public class PruebaCalculadora {
    public static void main(String[] args) {
        
        
        System.out.println( "La suma de 2 + 2 es: " + Utilidades.suma(2, 2));

        int[] listaNumeros ={3,4,23,123,145};
        System.out.println( "La suma de los elementos del array es: " + Utilidades.suma(listaNumeros));


        ArrayList<Integer> numeros = new ArrayList<Integer>(10);

        //metemos 10 num aleatorios en el arraylist
        for (int i = 0; i < 1000; i++) {
            
            int aleatorio= (int) (Math.random()*10);
            numeros.add(aleatorio);
        }

        if (numeros.contains(8)) {
            System.out.println("Por el aire te lo eso");
        }
        System.out.println( "La suma de los elementos del array es: " + Utilidades.suma(numeros));


    }
}
