package Unidad3;
public class Personaje {
   
    private String nombre;
    private int vida;
    private int armadura;
    protected int credito;

    public Personaje()
    {
        this.nombre="Generado";
        this.vida =(int) (Math.random()*100)+1;
        this.armadura=100;
        this.credito=30;
    }

    public Personaje(String nombre, int vida, int armadura, int credito)
    {
        this.nombre=nombre;
        this.vida=vida;
        this.armadura=armadura;
        this.credito=credito;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombreRecibido)
    {
        nombre=nombreRecibido;
    }

    public int getCreditos()
    {
        return this.credito;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub

        String salida = "***************************************";
        salida = "Nombre: " + this.nombre + "\n";
        salida += " Vida : " + this.vida + "\n";
        salida += " Armadura : " + this.armadura + "\n";
        salida += " Creditos : " + this.credito + "\n";

        salida += "***************************************";

        return salida;

    }

    //TODO
    public int compareTo (Personaje p)
    {
         if  (this.vida>p.vida) return 1;
         else if (this.vida<p.vida) return -1;
        else return 0;
        }
}
