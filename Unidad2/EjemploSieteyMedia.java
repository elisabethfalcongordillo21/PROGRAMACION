package Unidad2;

import java.util.Scanner;

/*
*El programa simulara el juego de cartas de kas 7 y 1/2 primereo el jugador sacara cartas
*hasta que quiera parar, si se pasa de 7 y media directamente pierde
*Si no pierde entonces juega la banca contra la suma de las cartas del jugador
*La banca querra siempre más  cartas mientras su  suma sea menor de 5
*/ 

public class EjemploSieteyMedia {

public static void main(String[] args) {
    
    float carta =0;
    float sumaJugador =0;
    String quiereContinuar ="si";
    Scanner teclado = new Scanner(System.in);
    float sumaBanca=0;
    while (quiereContinuar.equalsIgnoreCase("si")&& sumaJugador<7.5f) {
        
        //Sacamos una carta aleatoria de la baraja.
        carta = (int)(Math.random()*10+1);

        //Es media si sale un 8 un 9 o un 10

        if (carta>7) carta=0.5f;

        //Sumamos la carta que hemos sacado al conjunto de cartas
        sumaJugador = sumaJugador + carta;

        //Le preguntamos al jugador si quiere seguir

        System.out.println("Ha salido un " + carta + ", en total llevas " + sumaJugador);
        
        //Si se ha pasado le mostramos un mensaje
        //En caso contrario le preguntamos si se atreve a seguir
        
        if(sumaJugador>7.5f)
        System.out.println("Te has pasado");
        else{
        System.out.print("¿Quieres seguir jugando? (Si/No):");
        quiereContinuar=teclado.next();
        }
    }

    //La banca empieza solo si el jugador no se ha pasado
    if(sumaJugador<=7.5f){
        while (sumaBanca<5 && sumaBanca < 7.5f) {
        
        //Sacamos una carta aleatoria de la baraja.
        carta = (int)(Math.random()*10+1);

        //Es media si sale un 8 un 9 o un 10

        if (carta>7) carta=0.5f;

        //Sumamos la carta que hemos sacado al conjunto de cartas
        sumaBanca = sumaBanca + carta;

        //Sacamos por pantalla la carta de la banca
        System.out.println("Ha salido un " + carta + ", en total la banca lleva " + sumaBanca);      
    }

     //Si se ha pasado le mostramos un mensaje
    //En caso contrario le preguntamos si se atreve a seguir
        if(sumaJugador>7.5f)
        System.out.println("La banca ha perdido");
        else{
            //En este else la banca no se ha pasado
            //Si el jugador tampoco se ha pasado
            if(sumaJugador<=7.5){
                //Comprobamos si el jugador ha ganado
                if(sumaJugador>sumaBanca){
                System.out.println("Ha ganado el jugador");
                if(sumaJugador==7.5f){
                    System.out.println("Felicidades por sacar 7.5 te pagamos el doble");
                }
                }else
                //Si ha sacado lo mismo o la banca mas, gana la banca
                System.out.println("Ha ganado la banca");
        }


    }  
    teclado.close();
}

}
}