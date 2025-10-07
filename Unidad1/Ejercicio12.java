package Unidad1;
import java.util.Scanner;
public class Ejercicio12 {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        System.out.println("Resolvamos la ecuación de segundo grado");
        System.out.println("Forma: ax² + bx + c = 0 ");
        System.out.println("Introduce el valor de a:");
        double a = scanner.nextDouble();
        System.out.println("Introduce el valor de b: ");
        double b = scanner.nextDouble();
        System.out.println("Introduce el valor de c: ");
        double c = scanner.nextDouble();

        System.out.println("Ecuación: " + a + "x² + " + b + "x + " + c + " = 0");
        
        double raiz = b * b - 4 * a * c;
        double x1 = (-b + Math.sqrt(raiz)) / (2 * a );
        double x2 = (-b - Math.sqrt(raiz)) / (2 * a );

        System.out.println("Soluciones");
        System.out.println("x1 = " + x1);
        System.out.println("x2 = " + x2);

        scanner.close();
        
    }
}
