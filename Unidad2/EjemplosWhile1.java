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

/*Ejerciciov2: leer las edades de 10 personas, que deben de estar entre 18 y 65, 
se debe sacar por pantalla la edad y si esta jubilado o si es menor de edad y calcular la media final
*/

  int edad=0;
  int sumaEdad=0;
  i=1;
  int cantidadPersonas=0;
   String mensaje="";

  //Quiero que se repita 10 veces
  while (i<=10) {

    mensaje=""; 
    //Genero la edad aleatoria entre 1 y 100

    edad = (int)(Math.random()*100)+1;
    
    if (edad < 18) 

       mensaje="La persona es menor de edad, su edad es ";      
     else if (edad > 65 )
        mensaje="La persona es jubilada, su edad es " + edad + ". ";
    else {
       
        sumaEdad= sumaEdad + edad;
        //incrementamos la cantidad de las personas entre 18 y 65 para hacer la media 
        cantidadPersonas= cantidadPersonas + 1;
  
    }
     //mostramos la edad del personaje
        mensaje= "La edad de la persona " + i + " es: " + edad;
     //Pasamos a la siguiente persona
     i++;
     
    
}

    //Calculamos la media
   if (cantidadPersonas !=0) 
     sumaEdad = sumaEdad/10;
    System.out.println("La Media es " + sumaEdad);

   
   



 }   
}
