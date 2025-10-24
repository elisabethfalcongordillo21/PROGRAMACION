package Unidad2;

public class CuentaAtras {
    
    public static void main(String[] args) {
        
        /*
         * Primero vamos a hacer un programa que haga una cuenta atras de un cohete
         Contara desde 10 hasta 1 y despues dira ignicion, cuando sea 3 ademas
         mostrare el mensaje "preparados" cuando sea 2 "listo" y cuando sea 1 "rezad mucho"
        */
     int i=10;
     /*
      * Al ser cuenta atras vamos a ir restando uno al numero y lo inicializamos a 10
      *Dentro del bucle dependiendo  del valor iremos mostrando  los mensajes
      *que nos pide el enunciado
      */

        while (i>=1) {
            if (i==3) {
                System.out.println("Quedan 3---Preparados");
            }else if (i==2) {
                System.out.println("Quedan 2---Listo");
            }else if (i==1) {
                System.out.println("Queda 1---Rezad mucho");
            }

            System.out.println(i);
            i--;
        }
    
        System.out.println("Ignincioonn!!");
    
    }
}
