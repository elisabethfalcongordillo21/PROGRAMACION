package com.practicajavafx.model;

public class ProductoDO {
   private String nombre;
   private double precio;
   private String descripcion;
   private String tipo;
   private boolean disponible;

    // Getters y Setters
    public String getNombre() 
    { 
        return nombre; 
    }

    public void setNombre(String nombre) 
    { 
        this.nombre = nombre; 
    }

    public double getPrecio() 
    { 
        return precio; 
    }

    public void setPrecio(double precio) 
    { 
        this.precio = precio; 
    }

    public String getDescripcion() 
    { 
        return descripcion; 
    }

    public void setDescripcion(String descripcion) 
    { 
        this.descripcion = descripcion; 
    }

    public String getTipo() 
    { 
        return tipo; 
    }

    public void setTipo(String tipo) 
    { 
        this.tipo = tipo; 
    }

    public boolean isDisponible() 
    { 
        return disponible; 
    }

    public void setDisponible(boolean disponible) 
    { 
        this.disponible = disponible; 
    }

    @Override
    public String toString() {
        return nombre + " | " + String.format("%.2f", precio) + " € | " 
             + descripcion + " | " + tipo + " | " + disponible;
    }  
}
