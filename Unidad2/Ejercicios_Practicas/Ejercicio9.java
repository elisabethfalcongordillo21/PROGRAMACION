package Unidad2.Ejercicios_Practicas;

import java.util.Scanner;

public class Ejercicio9 {
    public static void main(String[] args) {
        int base=12;
        double precio=0;
        int edad=0;
        float descuento=0;
        int numEntradas=0;
        int diaSemana=0;
        Scanner teclado = new Scanner (System.in);
        
        //Preguntar por pantalla
        System.out.println("¿Tiene usted discapacidad? (Si/No)");
        boolean tieneDiscapacidad  = (boolean) (teclado.nextLine().equalsIgnoreCase("si"));
        System.out.println("¿Cuántas entradas vas a comprar?:");
        numEntradas = teclado.nextInt();
        System.out.println("¿Qué día de la semana vas al cine? (Ingresa el numero correspondiente al día, ej Lunes=1...)");
        diaSemana= teclado.nextInt();
        teclado.nextLine();
        System.out.println("¿El día es festivo? (Si/No)");
        boolean esFestivo = (boolean) (teclado.nextLine().equalsIgnoreCase("si"));
        System.out.println("¿Eres estudiante? (Si/No)");
        boolean esEstudiante = (boolean) (teclado.nextLine().equalsIgnoreCase("si"));
        System.out.println("¿Qué edad tienes?");
        edad= teclado.nextInt();

        if (tieneDiscapacidad)
            precio=0;    
        else if(diaSemana==3 &&  edad<25)
            descuento=0;
        else if (edad>60 || esEstudiante && descuento==0) {
            descuento=0.15f;
        }
        else if(esFestivo){
            descuento=0;
        }
        else  if (numEntradas>=2 && diaSemana>=6) {
            descuento=descuento + 0.10f;
        }
        precio = (base - (base * descuento)) * numEntradas;
        System.out.printf("El precio total es: %.2f€%n" , precio);
        //He usado %.2f€%n para que solo salgan dos decimales, lo he buscado en internet.
        teclado.close();
    }
}
