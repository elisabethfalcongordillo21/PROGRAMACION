package Unidad2.Ejercicios_Practicas;

import java.util.Scanner;

public class Problemapizza {

    public static void main(String[] args) {
        
        int PorcionesPorPizza = 8;
        Scanner leer = new Scanner(System.in);
        
        System.out.print("Introduce num de empleados: ");
        int NumEmpleados = leer.nextInt();

        System.out.print("Introduce num pizzas: ");
        int numPizzas = leer.nextInt();

        int NumPizzasPorEmpleado = numPizzas / NumEmpleados;
        int NumPizzasSobrantes = numPizzas % NumEmpleados;

        int NumTrozosPorEmpleado = (NumPizzasSobrantes * PorcionesPorPizza) / NumEmpleados;
        int NumTrozosSobrantes = (NumPizzasSobrantes * PorcionesPorPizza) % NumEmpleados;

        System.out.print( numPizzas + " pizzas, " + NumEmpleados + " empleados -->");
        for( int i = 0; i < NumEmpleados; i++)
        {
            System.out.print("Empleado " + i + ": ");

            //pizzas por empleado
            for (int j = 0; j<NumPizzasPorEmpleado; j++ )
            {
                System.out.print("P");
            }

            //trozo por empleado
            for(int x=0; x<NumTrozosPorEmpleado; x++)
            {
                System.out.print("T");
            }

            System.out.print(", ");
        }

        System.out.print("Sobra " + NumTrozosSobrantes + " trozos.");

    }
    
}