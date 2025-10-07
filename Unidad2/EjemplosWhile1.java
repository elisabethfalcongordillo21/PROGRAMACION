package Unidad2;

public class EjemplosWhile1 {
    public static void main(String[] args) {
     
        int dado=10;
    while (dado <= 25)
    {
        System.out.println("Has sacado" + dado + ". Una tirada pasable,voy a regalarte un punto");
        dado=dado+1;
        
    }

    

    
//Creamos un numero aleatorio para hacer su tabla de multiplicar
    int numTabla =(int) (Math.random()*10) +1;
    int i= 1;
/*
    Para sacar la tabla de multiplicar mostramos por pantalla la expresion de cada multplicacion
    la variable i la vamos incrementando para usarla en la multiplicacion y que cada vez sea por el siguiente 
    numero
*/    
    while (i!=11)
    {
        System.out.println(numTabla + " x " + i + " = " + (numTabla*i));
        i++;
  }  

  int edad=0;
  int sumaEdad=0;
  i=1;

  //Quiero que se repita 10 veces
  while (i<=10) {
    

    //Genero la edad aleatoria

    edad =18 + (int)(Math.random()*48);
    
    //mostramos la edad del personaje

    System.out.println("La edad de la persona " + i + " es: " + edad);

    //Pasamos a la siguiente persona

    i++;

    sumaEdad= sumaEdad + edad;
}

    //Calculamos la media
    sumaEdad = sumaEdad/10;
    System.out.println("La Media es " + sumaEdad);



 }   
}
