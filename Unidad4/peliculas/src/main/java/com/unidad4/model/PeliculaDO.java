package com.unidad4.model;

public class PeliculaDO {
    int id;
    String titulo;
    int duracion;
    int clasificacion;
    String sinopsis;

    public PeliculaDO(){}

    public PeliculaDO(int id, String titulo, int duracion, int clasificacion, String sinopsis)
    {
        this.id=id;
        this.titulo=titulo;
        this.duracion=duracion;
        this.clasificacion=clasificacion;
        this.sinopsis=sinopsis;
    }

    public int getId(){return id;}
    public String getTitulo() { return titulo; }
    public int getDuracion() { return duracion; }
    public int getClasificacion() { return clasificacion;}
    public String getSinopsis(){return sinopsis;}

      public void setId(int id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDuracion(int duracion) { this.duracion = duracion; }
    public void setClasificacion(int clasificacion) { this.clasificacion = clasificacion; }
    public void setSinopsis(String sinopsis){this.sinopsis=sinopsis;}

    @Override
     public String toString() {
        return "[ID: " + id + "] " + titulo + " | Duracion: " + duracion + " | Clasificacion: " + clasificacion + " | Sinopsis: " + sinopsis;
    }



}
