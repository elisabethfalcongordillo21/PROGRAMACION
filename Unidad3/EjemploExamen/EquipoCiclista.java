package Unidad3.EjemploExamen;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Ciclista.java (1.5 puntos)

Está compuesto por los siguientes atributos miembro, los cuales el alumno deberá seleccionar un tipo adecuado al dato:
Identificador, número
nombre
edad
modeloBici
Peso
posición
especialidad (montaña, contrareloj, todoterreno)

Se deberán crear todas las variables privadas, y todas deberán de tener sus métodos de acceso get y set.

Tendrá un constructor vacío que generará los valores numéricos aleatoriamente y pondrá a “” las cadenas de texto y otro constructor que recibirá todos los parámetros y se los asignará a los atributos de la clase.

 */
public class EquipoCiclista {

    public final int ESP_MONTANA = 1;
    public final int ESP_CONTRARELOJ = 2;
    public final int ESP_TODOTERRENO = 0;

    private String id;
    private String nombre;
    private int numCiclistas;
    private ArrayList<Ciclista> listaCiclistas;

    public EquipoCiclista()
    {
        this.id= letraAleatoria()+ String.valueOf((int) (Math.random()*100)+1);
        this.nombre="";
        this.numCiclistas=(int) (Math.random()*90)+18;

        this.listaCiclistas = new ArrayList<Ciclista>();
        for (int i = 0; i < 10; i++) 
        {    
            Ciclista ciclista = new Ciclista();
        }

    }

     public EquipoCiclista( String id,String nombre,int numCiclistas,ArrayList<Ciclista>listaCiclistas)
    {
        this.id =id;
        this.nombre=nombre;
        this.numCiclistas=numCiclistas;
        this.listaCiclistas=listaCiclistas;
       
    }

   /****************************************
     * 
     *              GET Y SET
     * 
     *****************************************/
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getNumCiclistas() {
        return numCiclistas;
    }
    public void setNumCiclistas(int numCiclistas) {
        this.numCiclistas = numCiclistas;
    }
    public ArrayList<Ciclista> getListaCiclistas() {
        return listaCiclistas;
    }
    public void setListaCiclistas(ArrayList<Ciclista> listaCiclistas) {
        this.listaCiclistas = listaCiclistas;
    }


    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return " EQUIPO CICLISTA: \n/************************************\n"
                    +"Id:" + this.id+"\n"
                    +"Nombre"+this.nombre+"\n"
                    +"Numero Ciclistas"+this.numCiclistas+"\n"
                    +"Lista Ciclistas"+this.listaCiclistas.toString()+"\n"
                    +"************************************";
    }


    public static String letraAleatoria()
    {
     String letras ="abcdefghijklmnñopqrstuvwxyz";
    int posicion = (int)Math.random()*letras.length();

        return String.valueOf(letras.charAt(posicion));
    }


    /****************************************
     * 
     *              FUNCIONALIDADES
     * 
     *****************************************/

     public int  numCiclistas()
     {
        return this.listaCiclistas.size();
     }

     public double maxPeso()
     {
        double maxPeso = 0;

        for (Ciclista ciclista : this.listaCiclistas) {
            if (ciclista.getPeso()>maxPeso) {
                
                maxPeso = ciclista.getPeso();
            }
        }
            //Con stream, calculamos el maximo indicandole que compare por el peso
            Ciclista ciclistaPesado =this.listaCiclistas.stream().max(Comparator.comparingDouble(Ciclista :: getPeso)).orElse(null);
            return ciclistaPesado.getPeso();

            //Otra mas Convertimos a stream filtramos solo el peso y calculamos el maximo
           // return listaCiclistas.stream().mapToDouble(Ciclista :: getPeso).max().orElse(0.0);
     }

     public int numCiclistas(int especialidad)
     {
        return ((int)listaCiclistas.stream().filter(c->c.getEspecialidad() == especialidad).count());

    }

}
