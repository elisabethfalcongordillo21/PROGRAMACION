package Unidad3;

import java.util.Arrays;
import java.util.Collection;

public class PruebaValorant {
    
    public static void main(String[] args) {
        
        Arma punial = new Arma("Puñal", 2, Arma.Corta_Distancia,10);
        Arma vandal = new Arma("Vandal", 5, Arma.Larga_Distancia,100);
        Arma granada = new Arma("Granada", 1, Arma.Explosiva,200);

        Personaje richton  = new Personaje("Richton",100,0,0,Personaje.francotirador);
        Personaje xamber  = new Personaje("Xamber",40,20,0,Personaje.francotirador);
        Personaje race  = new Personaje("Race",100,0,0,Personaje.francotirador);

        //Añadimos las armas al personaje con addAll y Arrays.asList
        xamber.getListaArmas().addAll(Arrays.asList(punial,vandal,granada));
        richton.getListaArmas().addAll(Arrays.asList(punial));
        race.getListaArmas().addAll(Arrays.asList(punial,vandal,granada));

        Gremio pescadores  = new Gremio("QuestFisher",2,xamber);

        pescadores.getMiembros().addAll(Arrays.asList(richton,xamber,race));

        System.out.println("El arma mas pesada de race es " + race.armaMasPesada());
        System.out.println("Los Francotiradores mas ar mados son " + pescadores.muyArmados(Personaje.francotirador));

    }
}
