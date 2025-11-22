package com.autotech.autotech.compras;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.autotech.autotech.proveedores.ProveedorRepository;

@Controller
public class CompraController {

    private final CompraRepository compraRepository;
    private final ProveedorRepository proveedorRepository;

    public CompraController(CompraRepository compraRepository, ProveedorRepository proveedorRepository) {
        this.compraRepository = compraRepository;
        this.proveedorRepository = proveedorRepository;
    }

    @GetMapping("/compras")
    public String listar(Model model) {
        model.addAttribute("compras", compraRepository.findAll());
        return "compras";
    }

    @GetMapping("/compras/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("compra", new Compra());
        model.addAttribute("proveedores", proveedorRepository.findAll());
        return "compra_form";
    }

    @GetMapping("/compras/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Compra compra = compraRepository.findById(id).orElseThrow();
        model.addAttribute("compra", compra);
        model.addAttribute("proveedores", proveedorRepository.findAll());
        return "compra_form";
    }

    @PostMapping("/compras")
    public String guardar(@ModelAttribute("compra") Compra compra, BindingResult result) {
        if (compra.getFecha() == null) {
            compra.setFecha(LocalDate.now());
        }
        if (result.hasErrors()) {
            return "compra_form";
        }
        compraRepository.save(compra);
        return "redirect:/compras";
    }

    @PostMapping("/compras/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        compraRepository.deleteById(id);
        return "redirect:/compras";
    }
}
