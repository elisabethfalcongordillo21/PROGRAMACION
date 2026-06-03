package com.unidad4.model;

public class PremioDO {

    private int id;
    private String nombre;
    private String categoria;
    private int anyio_prem;

    public PremioDO(){}

    public PremioDO(int id, String nombre, String categoria, int anyio_prem){
        this.id=id;
        this.nombre=nombre;
        this.categoria= categoria;
        this.anyio_prem= anyio_prem;
    }
    
    //Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public int getAnyio_prem() { return anyio_prem; }

    //Setters
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setAnyio_prem(int anyio_prem) { this.anyio_prem = anyio_prem; }


 @Override
    public String toString() {
        return "[ID: " + id + "] " + nombre + " | Categoria: " + categoria + " | Año Premio: " + anyio_prem;
    }


}
