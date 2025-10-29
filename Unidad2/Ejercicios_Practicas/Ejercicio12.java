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

        //variables suma totales
        int suma1=jugador1_dado1+jugador1_dado2+jugador1_dado3;
        int suma2=jugador2_dado1+jugador2_dado2+jugador2_dado3;

        //Sacar por pantalla las tiradas
        System.out.println("El jugador 1 ha sacado " + jugador1_dado1 + "," + jugador1_dado2 + " y " + jugador1_dado3);
        System.out.println("El jugador 2 ha sacado " + jugador2_dado1 + "," + jugador2_dado2 + " y " + jugador2_dado3);


        
        //Comprobar si han hecho trio
        if (jugador1_dado1==jugador1_dado2 && jugador1_dado2==  jugador1_dado3) { 
            //saca trio el jugador 1 y hay que compararlo con el 2
            if (jugador2_dado1==jugador2_dado2&&jugador2_dado2==jugador2_dado3) { 
                //hay empate
                if (jugador1_dado1>jugador2_dado1) {
                    //gana el jugador 1
                    System.out.println("El ganador es el jugador 1");

                } else {System.out.println("El ganador es el jugador 2");}
                //gana jugador 1
            } else {
                System.out.println("Gana el jugador 1");}
        } 
        else if (jugador2_dado1==jugador2_dado2 && jugador2_dado2==jugador2_dado3) {
            //gana jugador 2
            System.out.println("Gana el jugador 2");
        }
        if ((jugador1_dado1==jugador1_dado2&&jugador1_dado2!=jugador1_dado3) || (jugador1_dado1==jugador1_dado3 && jugador1_dado3!= jugador1_dado2) || (jugador1_dado1!=jugador1_dado2&&jugador1_dado2==jugador1_dado3)) {
            //ha sacado pares
            if ((jugador2_dado1==jugador2_dado2&&jugador2_dado2!=jugador2_dado3) || (jugador2_dado1==jugador2_dado3 && jugador2_dado3!= jugador2_dado2) || (jugador2_dado1!=jugador2_dado2&&jugador2_dado2==jugador2_dado3)) {
                //comparar 
                if (suma1>suma2) {
                    System.out.println("Gana el jugador 1");
                }else if (suma1<suma2){System.out.println("Gana el jugador 2");}
                else{System.out.println("Hay empate");
                }
            }else {System.out.println("Gana el jugador 1");}
        }else if ((jugador2_dado1==jugador2_dado2&&jugador2_dado2!=jugador2_dado3) || (jugador2_dado1==jugador2_dado3 && jugador2_dado3!= jugador2_dado2) || (jugador2_dado1!=jugador2_dado2&&jugador2_dado2==jugador2_dado3)) 
        { 
            System.out.println("Ha ganado el jugador 2");
        }
        

        if (suma1>suma2) {
            System.out.println("Gana el jugador 1");
        }else if (suma1<suma2){
            System.out.println("Gana jugador 2");}
        else {
            System.out.println("Hay empate tecnico chavalin"); }
        }

    }
