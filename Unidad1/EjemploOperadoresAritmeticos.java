package Unidad1;

public class EjemploOperadoresAritmeticos {
    
public static void main(String[] args) {
    
    int edadHijo = 9;
    int edadPadre = 37;

    int sumaEdades=0;

    sumaEdades =  edadHijo + edadPadre;
    int diferenciaEdades = edadPadre - edadHijo;
    int mediaEdades = sumaEdades /2;
    int dobleHijo = edadHijo *2;
    
    //El operador es el resto de la división entera, en este caso
    //33/5 spn a 6 y sobran 3, por lo cual resto valdría 3
    int resto = 33%5;

    System.out.println("La suma de las edades es: " + sumaEdades);
    System.out.println("La suma de las edades es: " + diferenciaEdades);
    System.out.println("La media de las edades es: " + mediaEdades);
    System.out.println("El doble de la edad del hijo es: " + dobleHijo);

    //Estas dos sirven para lo mismo, pero pablo no recomienda la segunda
    edadHijo = edadHijo +10;
    edadHijo += 10;

    edadPadre = edadPadre +1;
    System.out.println("Edad del Padre:" + edadPadre++);
    System.out.println("Edad del Padre v2:" + ++edadPadre);
     //Cuando ++ está en la izquierda es lo primero que hace, cuando va a la derecha es lo ultimo,
     //que lo saca mas tarde por pantalla

     




}

}
