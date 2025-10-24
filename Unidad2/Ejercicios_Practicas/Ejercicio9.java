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
        System.out.println("¿El día es festivo? (Si/No)");
        boolean esFestivo = (boolean) (teclado.nextLine().equalsIgnoreCase("si"));
        System.out.println("¿Eres estudiante? (Si/No)");
        boolean esEstudiante = (boolean) (teclado.nextLine().equalsIgnoreCase("si"));
        
        
    
    
    
    
    
    
    }
}
