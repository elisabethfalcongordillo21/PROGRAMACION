package Unidad2.Ejercicios_Practicas;

public class Ejercicio12 {
    public static void main(String[] args) {
        
        int jugador1_dado1, jugador1_dado2, jugador1_dado3;
        int jugador2_dado1, jugador2_dado2, jugador2_dado3;

        //Tirada jugador 1
        jugador1_dado1=(int)(Math.random()*6)+1;
        jugador1_dado2=(int)(Math.random()*6)+1;
        jugador1_dado3=(int)(Math.random()*6)+1;

        //Tirada jugador 2
        jugador2_dado1=(int)(Math.random()*6)+1;
        jugador2_dado2=(int)(Math.random()*6)+1;
        jugador2_dado3=(int)(Math.random()*6)+1;


        //Comprobamos si j1 tiene trio
        if (jugador1_dado1==jugador1_dado2 && jugador1_dado2==jugador1_dado3) {
            
            //aqui j1 tiene trio, hay q comprobar si j2 lo tiene tmbn
            if (jugador2_dado1==jugador2_dado2 &&jugador2_dado2==jugador2_dado3) {
                if (jugador1_dado1> jugador2_dado1) {
                    //gana jugador 1
                }else {
                    //comprobar si ha sacado lo mismo
                    if (jugador1_dado1==jugador2_dado1) {
                        //empatan
                    }
                }
            }
        
        }


        Boolean blnjugador1_TieneTrio = jugador1_dado1==jugador1_dado2 && jugador1_dado2==jugador1_dado3;
        Boolean blnjugador2_TieneTrio = jugador2_dado1==jugador2_dado2 && jugador2_dado2==jugador2_dado3;

        Boolean blnjugador1_TienePareja = (jugador1_dado1 == jugador1_dado2 && jugador1_dado2 != jugador1_dado3)
                                        || (jugador1_dado1 != jugador1_dado2 && jugador1_dado2 == jugador1_dado3)
                                        || (jugador1_dado1 == jugador1_dado3 && jugador1_dado1 != jugador1_dado2);

        Boolean blnjugador2_TienePareja = (jugador2_dado1 == jugador2_dado2 && jugador2_dado2 != jugador2_dado3)
                                        || (jugador2_dado1 != jugador2_dado2 && jugador2_dado2 == jugador2_dado3)
                                        || (jugador2_dado1 == jugador2_dado3 && jugador2_dado1 != jugador2_dado2);

        if (blnjugador2_TieneTrio)
        {
            if (blnjugador2_TieneTrio)
            {
                // compruebo cual es mas grande                
            }
            else {
                // jugador2 gana
            }
        } 
        else {
            // jugador 1 no tiene trio

            if (blnjugador2_TieneTrio) {
                // jugador2 gana
            }
            else
            {
                // mirar parejas
                if (blnjugador1_TienePareja && blnjugador2_TienePareja) {
                    // los dos tienen, mirar cual mas grande
                }else if (blnjugador1_TienePareja && !blnjugador2_TienePareja)
                    // gana jugador1
                } else if (!blnjugador1_TienePareja && blnjugador2_TienePareja)
                    // gana jugador2
                } 
                else {
                    // gana quien sume mas puntos
                    int sumaJugador1 = jugador1_dado1 + jugador1_dado2 + jugador1_dado3;
                    int sumaJugador2 = jugador2_dado1 + jugador2_dado2 + jugador2_dado3;
                 }
            }
        }






