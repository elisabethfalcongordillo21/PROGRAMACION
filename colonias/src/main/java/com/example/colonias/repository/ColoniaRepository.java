package com.example.colonias.repository;

import com.example.colonias.model.Colonia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColoniaRepository extends JpaRepository<Colonia, Long> {

    // --- QUERY 1: Filtrado por nombre (contiene texto, ignorando mayúsculas) ---
    List<Colonia> findByNombreContainingIgnoreCase(String nombre);

    // --- QUERY 2: Filtrado por nombre ordenado por precio ascendente ---
    List<Colonia> findByNombreContainingIgnoreCaseOrderByPrecioAsc(String nombre);

    // --- QUERY 3: Filtrado por nombre ordenado por precio descendente ---
    List<Colonia> findByNombreContainingIgnoreCaseOrderByPrecioDesc(String nombre);

    // --- QUERY PERSONALIZADA 1: Colonias más baratas que un precio dado ---
    @Query("SELECT c FROM Colonia c WHERE c.precio < :precio ORDER BY c.precio ASC")
    List<Colonia> findColoniasMasBaratasQue(@Param("precio") Double precio);

    // --- QUERY PERSONALIZADA 2: Buscar por marca (ignorando mayúsculas) ---
    @Query("SELECT c FROM Colonia c WHERE LOWER(c.marca) LIKE LOWER(CONCAT('%', :marca, '%'))")
    List<Colonia> buscarPorMarca(@Param("marca") String marca);

    // --- QUERY PERSONALIZADA 3: Colonias de un género concreto ordenadas por nombre ---
    @Query("SELECT c FROM Colonia c WHERE c.genero = :genero ORDER BY c.nombre ASC")
    List<Colonia> findByGeneroOrdenado(@Param("genero") String genero);

    // --- Ordenar todos por precio ---
    List<Colonia> findAllByOrderByPrecioAsc();
    List<Colonia> findAllByOrderByPrecioDesc();

    // --- Ordenar todos por nombre ---
    List<Colonia> findAllByOrderByNombreAsc();
    List<Colonia> findAllByOrderByNombreDesc();
}
