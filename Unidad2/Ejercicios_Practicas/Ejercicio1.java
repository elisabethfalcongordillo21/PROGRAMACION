package Unidad2.Ejercicios_Practicas;

public class Ejercicio1 {
public static void main(String[] args) {
     int contador = 1;
    do { 
    if (contador % 3 == 0)
        System.out.print("Fizz"); 
    if (contador % 5 == 0) 
        System.out.print("Buzz"); 
    if (contador % 3 != 0 && contador % 5 != 0)
        System.out.print(contador);
     System.out.println();
        contador++;
    }   
    
    while (contador <= 15);
    
}
   
}

