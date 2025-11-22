package com.autotech.autotech.proveedores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProveedorController {

    private final ProveedorRepository proveedorRepository;

    public ProveedorController(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @GetMapping("/proveedores")
    public String listar(Model model) {
        model.addAttribute("proveedores", proveedorRepository.findAll());
        return "proveedores";
    }

    @GetMapping("/proveedores/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "proveedor_form";
    }

    @GetMapping("/proveedores/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Proveedor proveedor = proveedorRepository.findById(id).orElseThrow();
        model.addAttribute("proveedor", proveedor);
        return "proveedor_form";
    }

    @PostMapping("/proveedores")
    public String guardar(@ModelAttribute("proveedor") Proveedor proveedor, BindingResult result) {
        if (result.hasErrors()) {
            return "proveedor_form";
        }
        proveedorRepository.save(proveedor);
        return "redirect:/proveedores";
    }

    @PostMapping("/proveedores/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        proveedorRepository.deleteById(id);
        return "redirect:/proveedores";
    }
}
