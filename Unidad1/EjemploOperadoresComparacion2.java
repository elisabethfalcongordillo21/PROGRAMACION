package Unidad1;

import javax.swing.JOptionPane;

public class EjemploOperadoresComparacion2 {
    
public static void main(String[] args) {

    int numVidas=0,numNiveles=0, edadRecomendada=0;
    String nombreJuego = "Mario Brus";
    char nivelDificultad = 'A'; 
    String txtedadRecomendada="";
    numVidas=5;
    numNiveles=19;


/*
 * Usamos un inputDialog para leer datos, de la libreria visual de java Swing
 * El problema es que solo lee de tipo String
 * Si pasamos con el raton por encima de una funcion me sale
 *para que es cada parametro y lo que hace
 */

    txtedadRecomendada = JOptionPane.showInputDialog(
        null, "Introduzca la Edad Recomendada",
        "Edad Del Juego",
        JOptionPane.QUESTION_MESSAGE);

/* 
 * Supongamos que necesitamos convertir a numero la edadRecomendada
 * Para convertir tipos en Java lo más comun es usar 
 * las funciones parse y valueOf
*/
    edadRecomendada= Integer.valueOf(txtedadRecomendada); 

    System.out.println("Valor de edad usando valueOf"+edadRecomendada);

    edadRecomendada= Integer.parseInt(txtedadRecomendada);
    
    System.out.println("Valor de edad usando parseInt"+ edadRecomendada);

//Leemos el nombre del juego

nombreJuego = txtedadRecomendada = JOptionPane.showInputDialog(
        null, "Introduzca el Nombre del Juego",
        "Nombre Del Juego",
        JOptionPane.QUESTION_MESSAGE);


if (edadRecomendada>18){
    String mensaje = "Cuidado que es para mayores de edad";

    //Para unir (concatenar) cadenas de texto usamos el +
    // como si fuese un número

    mensaje = mensaje + "Ten cuidado!!";

    //Es más rápido usando la función concat

    mensaje.concat("Y no la lies!!");

    
    System.out.println("Cuidado que es para mayores de edad");
    System.out.println("Ten cuidado");
}

//Para comparar String no se usa el operador == sino la funcion equals
if (nombreJuego.equals("Mario Bross")) {

    System.out.println("El juego " + nombreJuego + " es el más guay de nintendo y es para mayores de" + edadRecomendada + "años");
    
}
}

}
