package Unidad3;

import java.util.Scanner;

public class PruebasObjetos {
    public static void main(String[] args) {

        String categoria;

        //Para  crear un objeto de una clase, es igual que una variable normal.
        //Primero ponemos el nombre de la clase, en este caso "Persobaje" y despue  el nombre
        //con el que queremos llamar al objeto, en este caso "fran"
        //para poder usar un objeto primero hay que crearlo para ello se utilizaa la palabra reservada
        //"new" seguida por el nombre de la clase y ()
        //esto llama al constructor por defecto.
        Personaje fran = new Personaje();
        Personaje maria = new Personaje("Maria",95,0,50);

        fran.setNombre("Francisco");
        fran.setVida(80);


        System.out.println("Fran tiene " + fran.getVida() + " puntos de vida");
        
        fran.setVida(300);
        System.out.println("Fran tiene " + fran.getVida() + " puntos de vida");

       

    
    }
}
