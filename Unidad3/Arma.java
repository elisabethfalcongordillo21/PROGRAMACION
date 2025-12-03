package Unidad3;

public class Arma {
    
   public static final int Larga_Distancia=1;
   public static final int Corta_Distancia=2;
   public  static final int Melee=3;
   public static final int Explosiva=4;
 
   private String nombre;
   private double peso;
   private int tipo;
   private int danio;
   private boolean estaEquipada;

   public Arma(){

    this.nombre = "Pistola";
      this.peso = 2;
        this.tipo = this.Corta_Distancia;
        this.danio = 20;
    }

    public Arma(String nombre, double peso, int tipo, int danio) {
        this.nombre = nombre;
        this.peso = peso;
        this.tipo = tipo;
        this.danio = danio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPeso() {
        return peso;
    }

    public int getTipo() {
        return tipo;
    }

    public int getDanio() {
        return danio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setDanio(int danio) {
        this.danio = danio;
    }

    public  boolean getEstaEquipada()
    {
        return this.estaEquipada;
    }

    public void setEstaEquipada(boolean estaEquipada) {
        this.estaEquipada = estaEquipada;
    }

    @Override
    public String toString() {
        // TODO cambiar y mostrar los datos del arma
        return super.toString();
    }

    

   



}
