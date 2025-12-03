package Unidad3;

import java.util.ArrayList;
import java.util.Iterator;

public class Personaje {
   
   
  /****************
     * CONSTANTES *
     *****************/
    public static final int MAX_ARMAS = 3;
    public static final int exito = 0;
    public static final int fracaso = -1;
    public static final int error_cargado = -2;

    public static final int guerrero = 1;
    public static final int ladron = 2;
    public static final int francotirador = 3;



    /************************
     * VARIABLES MIEMBRO *
     *************************/
    // Atributos de la clase (encapsulación)
    // Son privados para restringir el acceso directo desde fuera de la clase.
    // Solo se pueden modificar o acceder a través de métodos públicos (getters y
    // setters).
    private String nombre; // Variable de instancia: cada objeto Personaje tendrá su propio nombre
    private int vida; // Variable de instancia: cada objeto Personaje tendrá su propia vida
    private int armadura; // Variable de instancia: cada objeto Personaje tendrá su propia armadura

    private int clase;
    // Modificador de acceso 'protected': Permite el acceso a esta variable
    // desde la misma clase, clases del mismo paquete y subclases (herencia).
    protected int creditos; // Variable de instancia
    // Constructor por defecto o sin argumentos.
    // Se invoca cuando se crea un objeto sin especificar valores iniciales.
    // Inicializa los atributos con valores predeterminados.

    private ArrayList<Arma> listaArmas;

    /************************
     * CONSTRUCTORES *
     *************************/

    public Personaje() {
        this.nombre = "Generado"; // 'this' se refiere a la instancia actual del objeto.
        this.vida = (int) (Math.random() * 100) + 1; // Asigna una vida aleatoria.
        this.armadura = 100;
        this.creditos = 30;
        this.listaArmas = new ArrayList<Arma>();
    }

    // Constructor con argumentos.
    // Permite crear un objeto Personaje con valores específicos para sus atributos.
    // El ámbito de las variables 'nombre', 'vida', 'armadura', 'creditos'
    // (parámetros)
    // es local a este constructor.
    public Personaje(String nombre, int vida, int armadura, int creditos,int clase) {
        this.nombre = nombre; // Asigna el valor del parámetro 'nombre' al atributo 'nombre' del objeto.
        this.vida = vida;
        this.armadura = armadura;
        this.creditos = creditos;
        this.clase=clase;


    }

    /************************
     * FUNCIONES *
     *************************/
    /**
     * Esta funcion recibe un arma y se la intenta asignar al personaje, los magos solo equipan
     * armas de largo alcance, los francotiradores largo alcance o explosiva si no lleva armmadura
     * y los guerreros corto alcance y explosiva si tiene mas de 20 puntos de vida
     * solo puede equipar el arma si no a alcanzado la cantidad de armas maxima
     * @param arma el arma que quiere equiparse
     * @return exito 1 o fracaso -1 o maxima cantidad -2
     */
    public int euiparArma(Arma arma) {

        //comprobamos si el arma es valida para el personaje
        if ((clase == ladron && arma.getTipo() == Arma.Larga_Distancia ) ||
         (clase == francotirador && (arma.getTipo() == Arma.Larga_Distancia)|| (arma.getTipo() == Arma.Explosiva && this.armadura==0) )|| 
         (clase == guerrero && (arma.getTipo() == arma.Corta_Distancia || (arma.getTipo() == arma.Explosiva && this.vida>20))))
        {
            //si no se pasa de capacidad, se equipa el arma
            if (listaArmas.size()<MAX_ARMAS){
            this.listaArmas.add(arma);
            }else return error_cargado;

        } else return fracaso;

        return exito;
    }

    /**
     * busca el arma entre el arsenal del personaje y  si esta  en su lista de armas la elimina
     * 
     * @param arma
     * @return exito si existe el arma y la ha podido quitar y fracaso si no 
     */

    public int desarmar(Arma arma)
    {
        if (arma==null || this.listaArmas.isEmpty()) 
            return fracaso;

        //ejemplo de eliminar elementos de un arraylist con condiciones usando removeIf
        //listaArmas.removeIf(a -> a.equals(arma) );

        //comprobamos si el arma esta en el array 

        if (listaArmas.contains(arma)) 
        {
            listaArmas.remove(arma);
            return exito;

        } else 
        return fracaso;
            
    }

    /**
     * Funcion que devuelve el arma que mas pesa
     * @return  el arma que mas pesa o null siu no hay armas
     */

    public Arma armaMasPesada()
    {
        //Creamos un iterator para recorrer las armass
        //El iterator contiene al  arraylist de armas y funciones para recorrerlo
        Iterator<Arma> it  = this.listaArmas.iterator();

        double maxPeso= Double.MIN_VALUE;
        Arma armaMax = null;

        //hasNext is true mientras queden elementos por recorrer en la lista
        while (it.hasNext())
        {
            //next() nos devuelve el elemento de la posicion actual y pasa al siguiente elemento
            Arma arma = it.next();

            if(arma.getPeso()> maxPeso)
            {
                maxPeso = arma.getPeso();
                armaMax = arma;
            }
        }

        return  armaMax;
    }


    // Métodos "getter" (accesores) para obtener el valor de los atributos privados.
    // Son públicos para permitir el acceso controlado a los atributos desde otras
    // clases (encapsulación).
    public int getVida() {
        return vida; // Devuelve el valor del atributo 'vida'.
    }

    // Método "setter" (mutador) para establecer el valor del atributo privado
    // 'vida'.
    // Permite modificar el atributo de forma controlada.
    public void setVida(int vida) { // El parámetro 'vida' es una variable local al método.

        this.vida = vida; // Asigna el valor del parámetro 'vida' al atributo 'vida' del objeto.
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombreRecibido) { // 'nombreRecibido' es el parámetro local.
        nombre = nombreRecibido; // Asigna el valor del parámetro 'nombreRecibido' al atributo 'nombre'.
    }

    public int getCreditos() {
        return this.creditos;
    }

    public int getArmadura() {
        return armadura;
    }
    public void setArmadura(int armadura) {
        this.armadura = armadura;
    }

    public ArrayList<Arma> getListaArmas() {
        return listaArmas;
    }
    public void setListaArmas(ArrayList<Arma> listaArmas) {
        this.listaArmas = listaArmas;
    }
    public int getClase() {
        return clase;
    }
    public void setClase(int clase) {
        this.clase = clase;
    }

    // Sobrescritura del método toString() de la clase Object.
    // Proporciona una representación en cadena del objeto Personaje,
    // útil para depuración y visualización.
    @Override // Indica que este método sobrescribe un método de la clase padre.
    public String toString() {

        // TODO Auto-generated method stub
        String salida = "*************************************\n";
        salida += "Nombre: " + this.nombre + "\n"; // Accede al atributo 'nombre' del objeto actual.
        salida += " Vida: " + this.vida + "\n";
        salida += " Armadura: " + this.armadura + "\n";
        salida += " Creditos: " + this.creditos + "\n";
        salida += "*************************************";

        return salida; // Devuelve la cadena formateada.

    }

    // Método para comparar dos objetos Personaje basándose en su vida.
    // Implementa una lógica similar a la interfaz Comparable (aunque no la
    // implementa explícitamente aquí).
    public int compareTo(Personaje p) { // 'p' es una variable local al método.
        if (this.vida > p.vida) // Compara la vida del objeto actual con la vida del objeto 'p'.
            return 1; // Retorna 1 si el objeto actual tiene más vida.
        else if (this.vida < p.vida)
            return -1; // Retorna -1 si el objeto actual tiene menos vida.
        else
            return 0; // Retorna 0 si tienen la misma vida.
    }
}
