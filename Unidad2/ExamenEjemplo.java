
import java.util.Scanner;

public class ExamenEjemplo {
  public static void main(String[] args) {
    
    int numVideojuegos=0;
    Scanner teclado = new Scanner(System.in);
    String nombre="";
    double precio=0, sumaPrecio=0;
    String categoria="";
    int stock =0, sumaStockPlay=0;
    String plataforma="";
    byte puntuacion=0;
    double maxPrecio = Integer.MIN_VALUE, minPrecio= Integer.MAX_VALUE;
    String nombreMax="", nombreMin="";
    String vdCondicion="";
    String listaNombresZ[];

    //Leemos de teclado la cantidad de videojuegos
    System.out.print("¿Cuantos Videojuegos quieres tratar?");
    numVideojuegos=teclado.nextInt();
    teclado.nextLine();
    //Creamos el array con espacio para numVideojuegos
    listaNombresZ = new String[numVideojuegos];

    //Para cada videojuego 
    for (int i = 0; i < numVideojuegos; i++) 
    {

        System.out.println("Introduce los datos del juego numero " + (i+1));

        
        System.out.print("Nombre: ");
        nombre = teclado.nextLine().toLowerCase();

        //Leemos el enter
        teclado.next();

        System.out.print("Precio: ");
        precio = teclado.nextDouble();

        System.out.print("Stock: ");
        stock = teclado.nextInt();

        //Mientras la plataforma no sea una de las validas, seguimos preguntando de nuevo

        do{
        System.out.print("Plataforma: ");
        //Lo leemos y lo pasamos en minuscula
        plataforma = teclado.next().toLowerCase();
        }while(!plataforma.equals("switch") && !plataforma.equals("xbox") && !plataforma.equals("playstation") && !plataforma.equals("pc"));

        //Mientras la categoria no sea una de las validas, seguimos preguntando de nuevo

        do{
        System.out.print("Categoria: ");
        //Lo leemos y lo pasamos en minuscula
        categoria = teclado.next().toLowerCase();
        }while(!categoria.equals("aventura") && !categoria.equals("terror") && !categoria.equals("shooter") && !categoria.equals("plataformas"));

        //Puntuacion

        do{
        System.out.print("Puntuacion: ");
        //Lo leemos y lo pasamos en minuscula
        puntuacion = teclado.nextByte();
        teclado.nextLine();
        }while(puntuacion<1 || puntuacion>10);

        //La media de precio y el nombre del juego mas caro y mas barato.
    
        if (precio>maxPrecio) 
        {
            maxPrecio=precio; 
            //Cada vez que haya un nuevo maximo guardamos su nombre
            nombreMax = nombre;
        }


        if (precio<minPrecio) 
        {
            minPrecio=precio; 
            //Cada vez que haya un nuevo minimo guardamos su nombre
            nombreMin = nombre;
        }

        //Para la media vamos sumando todos los precios 
        sumaPrecio = sumaPrecio + precio;

        if (plataforma.equalsIgnoreCase("switch") && (categoria.equalsIgnoreCase("aventura") || (puntuacion>9 && precio>39))) {
            
            System.out.println("El videojuego " + nombre+ " cumple las condiciones.");
            vdCondicion= nombre + " ";
        }

        //Sumamos los stocks de playstation

        if (plataforma.equalsIgnoreCase("playstation")) {
            
            sumaStockPlay += stock;
        }
        


        if (stock<10 && puntuacion>6 && nombre.startsWith("z") || nombre.charAt(0)=='z') {
            
            listaNombresZ[i]= nombre;

        }


    }

    System.out.println("La media del precio es: " + (sumaPrecio/numVideojuegos));
    System.out.println("El nombre del videojuego mas caro es " + nombreMax  + " , cuesta " + maxPrecio );
    System.out.println("El nombre del videojuego mas barato es " + nombreMin  + " , cuesta " + minPrecio );
    
    System.out.println("Nombres que empiezan por z");
    for (int i = 0; i < listaNombresZ.length; i++) {
        
        if (listaNombresZ[i] !=null) {
            //si es nulo no se muestra
            System.out.println(listaNombresZ[i]);
        }
       

    }


    teclado.close();
  }  
}
