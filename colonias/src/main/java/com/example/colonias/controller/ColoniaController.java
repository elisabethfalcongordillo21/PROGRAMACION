package com.example.colonias.controller;

import com.example.colonias.model.Colonia;
import com.example.colonias.repository.ColoniaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ColoniaController
 *
 * Mapeo de URLs:
 *   GET  /colonias            → listar todas (con filtro y orden opcionales)
 *   GET  /colonias/nueva      → mostrar formulario de creación
 *   POST /colonias/nueva      → guardar nueva colonia
 *   GET  /colonias/{id}       → vista detallada
 *   GET  /colonias/editar/{id}→ mostrar formulario de edición
 *   POST /colonias/editar/{id}→ guardar cambios
 *   GET  /colonias/eliminar/{id} → eliminar colonia
 *   GET  /colonias/buscar     → query personalizada por precio máximo
 */
@Controller
@RequestMapping("/colonias")
public class ColoniaController {

    @Autowired
    private ColoniaRepository coloniaRepository;

    // ──────────────────────────────────────────
    // LISTADO con filtro por nombre y ordenación
    // ──────────────────────────────────────────
    @GetMapping
    public String listar(
            @RequestParam(required = false, defaultValue = "") String nombre,
            @RequestParam(required = false, defaultValue = "nombreAsc") String orden,
            Model model) {

        List<Colonia> colonias;

        boolean hayFiltro = !nombre.trim().isEmpty();

        if (hayFiltro) {
            // Filtrado por nombre + orden
            switch (orden) {
                case "precioAsc"  -> colonias = coloniaRepository.findByNombreContainingIgnoreCaseOrderByPrecioAsc(nombre);
                case "precioDesc" -> colonias = coloniaRepository.findByNombreContainingIgnoreCaseOrderByPrecioDesc(nombre);
                default           -> colonias = coloniaRepository.findByNombreContainingIgnoreCase(nombre);
            }
        } else {
            // Sin filtro, solo orden
            colonias = switch (orden) {
                case "precioAsc"  -> coloniaRepository.findAllByOrderByPrecioAsc();
                case "precioDesc" -> coloniaRepository.findAllByOrderByPrecioDesc();
                case "nombreDesc" -> coloniaRepository.findAllByOrderByNombreDesc();
                default           -> coloniaRepository.findAllByOrderByNombreAsc();
            };
        }

        model.addAttribute("colonias", colonias);
        model.addAttribute("nombre", nombre);
        model.addAttribute("orden", orden);
        return "colonias/lista";   // → templates/colonias/lista.html
    }

    // ──────────────────────────────────────────
    // CREAR
    // ──────────────────────────────────────────
    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        model.addAttribute("colonia", new Colonia());
        model.addAttribute("titulo", "Nueva Colonia");
        return "colonias/formulario";  // → templates/colonias/formulario.html
    }

    @PostMapping("/nueva")
    public String guardarNueva(@Valid @ModelAttribute Colonia colonia,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Nueva Colonia");
            return "colonias/formulario";
        }
        coloniaRepository.save(colonia);
        return "redirect:/colonias";
    }

    // ──────────────────────────────────────────
    // DETALLE
    // ──────────────────────────────────────────
    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Colonia colonia = coloniaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colonia no encontrada: " + id));
        model.addAttribute("colonia", colonia);
        return "colonias/detalle";   // → templates/colonias/detalle.html
    }

    // ──────────────────────────────────────────
    // EDITAR
    // ──────────────────────────────────────────
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Colonia colonia = coloniaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Colonia no encontrada: " + id));
        model.addAttribute("colonia", colonia);
        model.addAttribute("titulo", "Editar Colonia");
        return "colonias/formulario";
    }

    @PostMapping("/editar/{id}")
    public String guardarEdicion(@PathVariable Long id,
                                 @Valid @ModelAttribute Colonia colonia,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Editar Colonia");
            return "colonias/formulario";
        }
        colonia.setId(id);
        coloniaRepository.save(colonia);
        return "redirect:/colonias";
    }

    // ──────────────────────────────────────────
    // ELIMINAR
    // ──────────────────────────────────────────
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        coloniaRepository.deleteById(id);
        return "redirect:/colonias";
    }

    // ──────────────────────────────────────────
    // QUERY PERSONALIZADA: buscar por precio máximo
    // ──────────────────────────────────────────
    @GetMapping("/buscar")
    public String buscarPorPrecio(
            @RequestParam(required = false) Double precioMax,
            @RequestParam(required = false) String marca,
            Model model) {

        List<Colonia> resultado = null;

        if (precioMax != null) {
            resultado = coloniaRepository.findColoniasMasBaratasQue(precioMax);
            model.addAttribute("precioMax", precioMax);
        }
        if (marca != null && !marca.trim().isEmpty()) {
            resultado = coloniaRepository.buscarPorMarca(marca);
            model.addAttribute("marcaBuscar", marca);
        }

        model.addAttribute("resultado", resultado);
        return "colonias/buscar";   // → templates/colonias/buscar.html
    }
}
