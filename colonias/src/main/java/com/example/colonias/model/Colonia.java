package com.example.colonias.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "colonias")
public class Colonia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "La familia olfativa es obligatoria")
    private String familiaOlfativa;   // floral, oriental, amaderado, fresco, cítrico

    @NotNull(message = "El volumen es obligatorio")
    @Positive(message = "El volumen debe ser positivo")
    private Integer volumenMl;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private Double precio;

    @NotBlank(message = "El género es obligatorio")
    private String genero;            // hombre, mujer, unisex

    // ---- Getters y Setters ----

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getFamiliaOlfativa() { return familiaOlfativa; }
    public void setFamiliaOlfativa(String familiaOlfativa) { this.familiaOlfativa = familiaOlfativa; }

    public Integer getVolumenMl() { return volumenMl; }
    public void setVolumenMl(Integer volumenMl) { this.volumenMl = volumenMl; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
}
