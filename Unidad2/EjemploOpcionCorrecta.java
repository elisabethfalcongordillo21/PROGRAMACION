package Unidad2;

import java.util.Scanner;

public class EjemploOpcionCorrecta {
      /**
         * Este programa realizara un menu en el cual preguntará al alumno
         * cual es el destino final de la raza hummana, tendra cuatro opciones
         * Si el usuario elige la opcion correcta mostrara un mensaje tipo "Has salvado nuestro destino!"
         * y sino pondra un mesaje tipo "Cataclismo Zombie!! Try Again!!"
         * Pista:usar switch, el usuario responde con una letra o un numero de opcion
         * El programa en caso de que no acierte le seguirá preguntando hasta que de con la buena
         * @param args
         */
    public static void main(String[] args) {
      
        char opcion = ' ';
        Scanner teclado = new Scanner(System.in);

      
        String mensaje="";
        mensaje = "\n\nTienes que ayudarme a salvar el mundo. \n  Cual es el destino final de la humanidad, elige la opcion correcta \n";
        mensaje += "Las opciones son: \n";
        mensaje +="A- Volver a la agricultura y recoleccion y dejar la tecnologia \n";
        mensaje +="B- Encontrar planeta colonizable y dejar la tierra\n";
        mensaje += "C- Dejar que la IA guie nuestros destinos y decida lo mejor para nosotros\n";
        mensaje += "D- paso de tó\n";
        mensaje += "\nElige una opcion: ";


        System.out.println(mensaje);
        String mensajeErroneo = "La cagaste, intentalo de nuevo!!";
        //Leemos de teclado la opcion elegida

        do{
        opcion = teclado.nextLine().toUpperCase().charAt(0);

        switch (opcion) {
            case 'A':
            case 'B':
            case 'D':
            System.out.println(mensajeErroneo);
                break;
            case 'C':
             System.out.println("Felicidades, salvaste la humanidad!!");

            break;
           
            default:
            System.out.println("Escrine una letra de la A a la D");
                break;
        }
    }while (opcion != 'C');
        
        teclado.close();
    }
}
