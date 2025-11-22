package com.autotech.autotech.compras;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.autotech.autotech.proveedores.Proveedor;
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
    public String listar(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
        @RequestParam(required = false) String filtroProveedor,
        @RequestParam(required = false) String filtroItem,
        Model model) {

        List<Compra> compras = compraRepository.buscarConFiltros(fechaInicio, fechaFin, filtroProveedor, filtroItem);
        model.addAttribute("compras", compras);
        model.addAttribute("filtroProveedor", filtroProveedor);
        model.addAttribute("filtroItem", filtroItem);
        model.addAttribute("fechaInicio", fechaInicio);
        model.addAttribute("fechaFin", fechaFin);
        return "compras";
    }

    @GetMapping("/compras/nuevo")
    public String nuevo(Model model) {
        cargarFormulario(model, new Compra());
        return "compra_form";
    }

    @GetMapping("/compras/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Compra compra = compraRepository.findById(id).orElseThrow();
        cargarFormulario(model, compra);
        return "compra_form";
    }

    @PostMapping("/compras")
    public String guardar(
        @ModelAttribute("compra") Compra compra,
        @RequestParam("proveedorId") Long proveedorId,
        BindingResult result,
        Model model) {

        Proveedor proveedor = proveedorRepository.findById(proveedorId).orElse(null);
        compra.setProveedor(proveedor);

        if (compra.getFecha() == null) {
            compra.setFecha(LocalDate.now());
        }

        if (result.hasErrors()) {
            cargarFormulario(model, compra);
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

    private void cargarFormulario(Model model, Compra compra) {
        model.addAttribute("compra", compra);
        model.addAttribute("proveedores", proveedorRepository.findAll());
        Long proveedorSeleccionado = compra.getProveedor() != null ? compra.getProveedor().getId() : null;
        model.addAttribute("proveedorSeleccionado", proveedorSeleccionado);
    }
}
