package Unidad1;

import java.util.Scanner;

public class Ejercicio10 {
  public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        final double PI = 3.14159265359;
        
        System.out.print("Introduce el radio del círculo: ");
        double radio = scanner.nextDouble();
        
        double area = PI * radio * radio;
        
        System.out.println("El área del círculo con radio " + radio + " es: " + area);
        
        scanner.close();
    }
}   
