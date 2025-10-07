package Unidad1;
 import java.util.Scanner;

public class Ejercicio11 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Introduce tu sueldo bruto: ");
        double sueldoBruto = scanner.nextDouble();
        
        System.out.print("Introduce el porcentaje de impuestos (%): ");
        double porcentajeImpuestos = scanner.nextDouble();
        
        double impuestos = sueldoBruto * (porcentajeImpuestos / 100);
        

        double sueldoNeto = sueldoBruto - impuestos;
        

        System.out.println("\n--- RESULTADO ---");
        System.out.println("Cobras " + sueldoBruto + "euros pero hacienda se lleva " + 
                          impuestos + "euros por lo cual te quedan unos miseros " +  sueldoNeto + "euros");
        

        scanner.close();
    }
}
