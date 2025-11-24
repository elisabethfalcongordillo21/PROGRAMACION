package Unidad2;

import java.util.Scanner;

/*
 * Datos de cada smartphone:
Marca (String)
Modelo (String)
Gama (Premium, MediaGama, Economico)
Precio (double)
Stock (int)
RAM en GB (int): valores válidos: 4, 6, 8, 12, 16
Sistema Operativo (Android, iOS)
El programa deberá:
Solicitar al usuario el número de smartphones a introducir (mínimo 2, máximo 40)(HECHO)
Leer y validar los datos:
La Gama y Sistema Operativo deben validarse durante la entrada(HECHO)
La RAM debe estar entre los valores permitidos(HECHO)
El precio debe ser positivo(HECHO)
El stock no puede ser negativo(HECHO)
(Repetir hasta que los datos sean válidos)

a) Listar marca y modelo de todos los smartphones Android con RAM >= 8GB y precio
inferior a 400€(HECHO)
b) Mostrar cuántos smartphones hay de cada gama y el precio medio de cada gama.(HECHO)
c) Identificar y guardar en un array los modelos con stock inferior a 5 unidades.(HECHO)
d) Del conjunto de smartphones Premium: (HECHO)
El más caro (marca, modelo y precio)
El que tiene más RAM
e) Mostrar qué porcentaje del inventario total (en unidades) corresponde a iOS vs Android

La correcta lectura y cada uno de los apartados tienen valores iguales, se utilizará la rúbrica
de classroom para valorar el examen.
 */
 
public class ExamenA {
    public static void main(String[] args) {
        
    String marca ="", modelo ="",sistemaOperativo="",gama="";
    double precio=0;
    int ram=0;
    int numTlf=0;
    int stock=0;
    Scanner teclado = new Scanner(System.in);
    int contPrem =0 , contMed= 0, contEcn=0;
    double precPremium=0, precMedio=0, precEcon=0;
    String stockMenor5[] ;
    //Para premium
    String PremiumMasCaro="", PremiumMasRam="";
    int maxRam = Integer.MIN_VALUE;
    double maxPrecio = Integer.MIN_VALUE;
   
    //Leemos por teclado la cantidad de smartphones a tratar
    System.out.println("¿Cuantos smartphones vas a tratar?");
    numTlf=teclado.nextInt();
    teclado.nextLine();
    stockMenor5 = new String[numTlf];
    if (numTlf>=2 && numTlf<=40) {
        
        //Para cada smartphone
        for (int i = 0; i < numTlf; i++) {
        
        System.out.println("Introduce los datos del telefono numero: " + (i+1));
        
        System.out.println("Marca del dispositivo: ");
        marca =  teclado.next().toLowerCase();

        System.out.println("Modelo del dispositivo: ");
        modelo = teclado.next();
        

        //mientras la gama no sea una de las validas, se sigue preguntando de nuevo 
        //Gama (Premium, MediaGama, Economico)

        do{
            System.out.print("¿Cual es la gama del dispositivo?: ");
            gama=teclado.next().toLowerCase();
        }while(!gama.equals("premium") && !gama.equals("mediagama")&& !gama.equals("economico"));
        
        System.out.println("Precio del dispositivo: ");
        precio= teclado.nextDouble();
        
    
        System.out.println("Stock del dispositivo: ");
        stock = teclado.nextInt();
        if (stock == 5 ) {
            stockMenor5[i] = modelo;
        } 
     
       
        //mientras la ram no sea una de las validas, se sigue preguntando de nuevo 
        //RAM en GB (int): valores válidos: 4, 6, 8, 12, 16

        do {
            System.out.println("¿Cual es la ram del dispositivo?");
            ram= teclado.nextInt();
        } while (ram !=4 && ram !=6 && ram !=8 && ram !=12  && ram !=16);

        //mientras que el so no sea uno de los validos, seguir preguntando
        //Sistema Operativo (Android, iOS)
       do{
            System.out.print("¿Cual es el sistema operativo del dispositivo?: ");
            sistemaOperativo=teclado.next().toLowerCase();
        }while(!sistemaOperativo.equals("android") && !sistemaOperativo.equals("ios"));


       //Mostrar cuántos smartphones hay de cada gama y el precio medio de cada gama
       if (gama.equals("premium")) {
            precPremium += precio;
            contPrem++;
       } 
        if (gama.equals("mediagama")) {
            precMedio += precio;
            contMed++;

       }
       if (gama.equals("economico")) {
            precEcon += precio;
            contEcn++;
        
       }
        
       
       //Del conjunto de smartphones Premium:
       //El más caro (marca, modelo y precio)
       //El que tiene más RAM
       
       if (gama.equals("premium")) {
        
        if (precio> maxPrecio ) {
            maxPrecio=precio;
            PremiumMasCaro = modelo;
        }

        if (ram> maxRam) {
            maxRam=ram;
            PremiumMasRam = modelo;
        }

       }





        //PARENTESIS FOR
        }

       
       

    //PARENTESIS IF
     }
    
     //Listar marca y modelo de todos los smartphones Android con RAM >= 8GB y precio
    //inferior a 400€
        
        if (sistemaOperativo.equals("android") && ram>=8 && precio<400) {
            
            System.out.println("Marca y modelo de los Android con ram mayor o igual a 8 y precio menor de 400");
            System.out.println("Marca " + marca + " Modelo " + modelo);
        }

     //mostrar cantidad de cada gama 
     System.out.println("Cantidad de smartphones por categoría: ");
     System.out.println("Premium: " + contPrem);
     System.out.println("MediaGama : " + contMed);
     System.out.println("Economico: " + contEcn);

     //mostrar media precio por gama
     System.out.println("Media de precio por cada gama: ");
     System.out.println("Premium: " + (precEcon/contEcn));
     System.out.println("MediaGama: " + (precMedio/contMed));
     System.out.println("Economico: " + (precEcon/contEcn));


     //mostrar dispositivos con stock menor de 5
      System.out.println("Dispositivos con stock menor de 5: ");
        for (int i = 0; i < stockMenor5.length; i++) {
            if (stockMenor5[i] !=null) {
                System.out.println(stockMenor5[i]);
            }
        }
    
        //mostrar premium mas caro y premimum con mas ram
        System.out.println("El smartphone premium mas caro es: " + PremiumMasCaro + " ,con precio: " + maxPrecio)  ;
        System.out.println("El smartphone premium con mas ram es: " + PremiumMasRam + " , con ram: " + maxRam);
    

     teclado.close();
    }
}
