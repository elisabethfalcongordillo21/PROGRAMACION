package Unidad3.EjemploExamen;

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
public class Ciclista {

    public final int ESP_MONTANA = 1;
    public final int ESP_CONTRARELOJ = 2;
    public final int ESP_TODOTERRENO = 0;

    private int id;
    private String nombre;
    private int edad;
    private String modeloBici;
    private double peso;
    private  int posición;
    private int especialidad;


    public Ciclista()
    {
        this.id =(int) (Math.random()*100)+1;
        this.nombre="";
        this.edad=(int) (Math.random()*90)+18;
        this.modeloBici="";
        this.peso=Math.random()*40+40;
        this.posición =(int) (Math.random()*100)+1;
        this.especialidad= (int) (Math.random()*3);

    }

     public Ciclista( int id,String nombre,int edad,String modeloBici,double peso,int posición,int especialidad)
    {
        this.id =id;
        this.nombre=nombre;
        this.edad=edad;
        this.modeloBici=modeloBici;
        this.peso=peso;
        this.posición =posición;
        this.especialidad=especialidad;

    }

    /***
     * 
     * GETTERS Y SETTER 
     */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public String getModeloBici() {
        return modeloBici;
    }
    public void setModeloBici(String modeloBici) {
        this.modeloBici = modeloBici;
    }
    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    public int getPosición() {
        return posición;
    }
    public void setPosición(int posición) {
        this.posición = posición;
    }
    public int getEspecialidad() {
        return especialidad;
    }
    public void setEspecialidad(int especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "CICLISTA: \n/************************************\n"
                    +"Id:" + this.id+"\n"
                    +"Nombre"+this.nombre+"\n"
                    +"Edad"+this.edad+"\n"
                    +"ModeloBici"+this.modeloBici+"\n"
                    +"Peso"+this.peso+"\n"
                    +"Posición"+this.posición+"\n"
                    +"Especialidad"+this.especialidad+"\n"
                    +"************************************";
    }
    
}
