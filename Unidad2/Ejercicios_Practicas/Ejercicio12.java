package Unidad2.Ejercicios_Practicas;

public class Ejercicio12 {
<<<<<<< HEAD
     public static void main(String[] args) {
=======
    public static void main(String[] args) {
>>>>>>> 1d1b7bda8a710ec162d1f1f7bde2df0d898df41c
        
        int jugador1_dado1, jugador1_dado2, jugador1_dado3;
        int jugador2_dado1, jugador2_dado2, jugador2_dado3;

        //Tirada jugador 1
        jugador1_dado1=(int)(Math.random()*6)+1;
        jugador1_dado2=(int)(Math.random()*6)+1;
        jugador1_dado3=(int)(Math.random()*6)+1;
<<<<<<< HEAD
        
=======
>>>>>>> 1d1b7bda8a710ec162d1f1f7bde2df0d898df41c

        //Tirada jugador 2
        jugador2_dado1=(int)(Math.random()*6)+1;
        jugador2_dado2=(int)(Math.random()*6)+1;
        jugador2_dado3=(int)(Math.random()*6)+1;

<<<<<<< HEAD
        //variables suma totales
        int suma1=jugador1_dado1+jugador1_dado2+jugador1_dado3;
        int suma2=jugador2_dado1+jugador2_dado2+jugador2_dado3;

        //Sacar por pantalla las tiradas
        System.out.println("El jugador 1 ha sacado " + jugador1_dado1 + "," + jugador1_dado2 + " y " + jugador1_dado3);
        System.out.println("El jugador 2 ha sacado " + jugador2_dado1 + "," + jugador2_dado2 + " y " + jugador2_dado3);


        
        //Comprobar si han hecho trio
        if (jugador1_dado1==jugador1_dado2 && jugador1_dado2==  jugador1_dado3) { 
            //saca trio el jugador 1 y hay que compararlo con el 2
            if (jugador2_dado1==jugador2_dado2 && jugador2_dado2==jugador2_dado3) { 
                //hay empate
                if (jugador1_dado1>jugador2_dado1) {
                    //gana el jugador 1
                    System.out.println("El ganador es el jugador 1");

                } else {
                    System.out.println("El ganador es el jugador 2");
                }
                //gana jugador 1
            } else {
                System.out.println("Gana el jugador 1");
                }
        } else if (jugador2_dado1==jugador2_dado2 && jugador2_dado2==jugador2_dado3) {
            //gana jugador 2
            System.out.println("Gana el jugador 2");
        }
        if ((jugador1_dado1==jugador1_dado2 && jugador1_dado2!=jugador1_dado3) || (jugador1_dado1==jugador1_dado3 && jugador1_dado3!= jugador1_dado2) || (jugador1_dado1!=jugador1_dado2&&jugador1_dado2==jugador1_dado3)) {
            //ha sacado pares
            if ((jugador2_dado1==jugador2_dado2 && jugador2_dado2!=jugador2_dado3) || (jugador2_dado1==jugador2_dado3 && jugador2_dado3!= jugador2_dado2) || (jugador2_dado1!=jugador2_dado2&&jugador2_dado2==jugador2_dado3)) {
                //comparar 
                if (suma1>suma2) {
                    System.out.println("Gana el jugador 1");
                }else if (suma1<suma2){System.out.println("Gana el jugador 2");}
                else{System.out.println("Hay empate");
                }
            }else {System.out.println("Gana el jugador 1");}
        }else if ((jugador2_dado1==jugador2_dado2 && jugador2_dado2!=jugador2_dado3) || (jugador2_dado1==jugador2_dado3 && jugador2_dado3!= jugador2_dado2) || (jugador2_dado1!=jugador2_dado2 && jugador2_dado2==jugador2_dado3)) 
        { 
            System.out.println("Ha ganado el jugador 2");
        }
        

        if ((jugador1_dado1!=jugador1_dado2 && jugador1_dado2!=jugador1_dado3) && (jugador2_dado1!=jugador2_dado2 && jugador2_dado2!=jugador2_dado3)) {
            if (suma1>suma2) {
                System.out.println("Gana jugador 1");
            }else if (suma1<suma2) {
            System.out.println("Gana jugador 2");
            } else {
            System.out.println("Hay empate tecnico chavalin"); 
            }
        }

=======

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
>>>>>>> 1d1b7bda8a710ec162d1f1f7bde2df0d898df41c
            }
        }


<<<<<<< HEAD
=======




>>>>>>> 1d1b7bda8a710ec162d1f1f7bde2df0d898df41c
