package Unidad2.Ejercicios_Practicas;

import java.util.Scanner;

public class BuscaMinas {
    public static void main(String[] args) {
        
        /**
         * Definimos el mapa del buscaminas de 3x3
         * De cara a entenderlo se puede interpretar que cada elemento
         * del primer corchete es a su vez un arrays, es decir mapa[0] no es un numero
         * sino un array de numeros.
         * Para hundir la flota es igual pero de caracteres y 8 posiciones
         */
        int[][] mapaBombas = {{0,0,1},{0,1,0},{0,0,0,}};
        int[][] mapaJugador = {{9,9,9},{9,9,9},{9,9,9,}};

        Scanner teclado = new Scanner(System.in);

        System.out.println("En que posiciones quieres comprobar (x y)");

        int posx = teclado.nextInt();
        int posy = teclado.nextInt();
        int cantBombas=0;

   
         //Si hay una bomba en esa posicion explota y se acabo
        if(mapaBombas[posy][posx]==1) System.out.println("Bomba!!");
        else{
            /**
             * Recorremos con la i las posiciones anteriores y posteriores a la introducida
             * y con la j las verticales anteriores y posteriores
             */
             for(int i=posx-1;i<=posx+1;i++)
             {
                for (int j=posy-1;j<=posy-1;j++)
                {
                    //Comprobamos si las coordenadas estan dentro del mapa 
                    if (i >= 0 && i<=2 && j>=0 && j<=2) 
                    
                    if (mapaBombas[i][j]==1) {cantBombas++;
                }
             }


        }
        System.out.println( "Cerca de esa posicion (" + posx + "," + posy + ") hay" + cantBombas + "bombas");






        teclado.close();

        }
    }
}
