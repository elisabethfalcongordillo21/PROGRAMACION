package Unidad2.Ejercicios_Practicas;

import java.time.LocalDate;
import java.util.Scanner;

public class Ejercicio11 {
    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        int numMes = 0;
        LocalDate fechaActual = LocalDate.now();
        int  mesActual = fechaActual.getMonthValue();
        String nombreMes ="";
        int diaMes =0;
        int diferenciaMes =0;

        System.out.println("Ingresa un número de mes (1-12): ");
        numMes = teclado.nextInt();

        switch (numMes) {
            case 1:
            nombreMes= "Enero";
            diaMes= 31;
            break;
            case 2:
            nombreMes= "Febrero";
             if (fechaActual.isLeapYear()) {
                        diaMes = 29;
                    } else {
                        diaMes = 28;
                    }
            break;
             case 3:
            nombreMes= "Marzo";
            diaMes= 31;
            break;
             case 4:
            nombreMes= "Abril";
            diaMes= 30;
            break;
             case 5:
            nombreMes= "Mayo";
            diaMes= 31;
            break;
             case 6:
            nombreMes= "Junio";
            diaMes= 30;
            break;
             case 7:
            nombreMes= "Julio";
            diaMes= 31;
            break;
             case 8:
            nombreMes= "Agosto";
            diaMes= 31;
            break;
             case 9:
            nombreMes= "Septiembre";
            diaMes= 30;
            break;
             case 10:
            nombreMes= "Octubre";
            diaMes= 31;
            break;
             case 11:
            nombreMes= "Noviembre";
            diaMes= 30;
            break;
             case 12:
            nombreMes= "Diciembre";
            diaMes= 31;
            break;
            default:
                    System.out.println("Número de mes inválido");
                return;
        }
       if (numMes> mesActual) {
        
        diferenciaMes= numMes - mesActual;
        System.out.println("Faltan " + diferenciaMes + " meses para " + nombreMes);
       }
       else if (numMes< mesActual) {
        
        diferenciaMes = mesActual-numMes;
        System.out.println(nombreMes + " fue hace " + diferenciaMes + " meses");
       }
       else{
        System.out.println("¡Estamos en " + nombreMes + "!");
       }
        
        System.out.println(nombreMes + " tiene " + diaMes + " días");
    
        teclado.close();
    }
}
