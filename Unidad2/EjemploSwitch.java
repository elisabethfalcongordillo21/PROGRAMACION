package Unidad2;

import java.util.Scanner;

public class EjemploSwitch {
    public static void main(String[] args) {
        

        Scanner teclado = new Scanner(System.in);

        int codPostal = 0;

        System.out.println("Introduce los dos primeros digitos del código postal de tu ciudad y te digo la comunidad autonoma");

        //Leemos el código postal

        codPostal= teclado.nextInt();
        //Con if seria de la siguiente forma

        //if (codPostal == 28 ) System.out.println("Eres de la comunidad de Madrid");
        //else if (codPostal == 8 ) System.out.println("Eres de la comunidad de Barcelona ");
        //....

        String comunidad="";
        //Con switch

        switch (codPostal) 
        {
            case 8:
             comunidad = "Barcelona";
            break;
            case 28:
             comunidad = "Madrid";
            break;
            case 51: 
            comunidad ="Ceuta";
            break;
            case 52: 
            comunidad ="Melilla";
            break;
            case 1: 
             comunidad ="Alava";
            break;
            case 50: 
            comunidad = "Zaragoza";
            break;
            case 11: 
            comunidad = "Cadiz";
            break;
            default:
            comunidad = "Ninguna parte";
            

        }
        System.out.println("Eres de la comunidad de " + comunidad);
        teclado.close();

        //Se genera aleatoriamente un numero de la semana entre 1 y 7 
        // y se saca su es lunes,martes.... o fin de semana para el 6 y el 7 

        int numSemana = (int) (Math.random()*7 +1);
        String diaSemana = "";
        switch (numSemana) {
            case 1 :
                diaSemana="Lunes";
            break;
           
            case 2:
                diaSemana="Martes";
            break;
           
            case 3:
                diaSemana="Miercoles";
            break;
            
            case 4:
             diaSemana="Jueves";
            break;
           
            case 5:
                diaSemana="Viernes";
            break;
            
            case 6:
            case 7:
                diaSemana="Fin de Semana";
            break;
           
        }
        System.out.println("El dia de la semana es " + diaSemana);
        
    }
}
