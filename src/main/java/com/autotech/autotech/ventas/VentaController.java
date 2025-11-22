package com.autotech.autotech.ventas;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.autotech.autotech.clientes.ClienteRepository;

@Controller
public class VentaController {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;

    public VentaController(VentaRepository ventaRepository, ClienteRepository clienteRepository) {
        this.ventaRepository = ventaRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/ventas")
    public String listar(Model model) {
        model.addAttribute("ventas", ventaRepository.findAll());
        return "ventas";
    }

    @GetMapping("/ventas/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("venta", new Venta());
        model.addAttribute("clientes", clienteRepository.findAll());
        return "venta_form";
    }

    @GetMapping("/ventas/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Venta venta = ventaRepository.findById(id).orElseThrow();
        model.addAttribute("venta", venta);
        model.addAttribute("clientes", clienteRepository.findAll());
        return "venta_form";
    }

    @PostMapping("/ventas")
    public String guardar(@ModelAttribute("venta") Venta venta, BindingResult result) {
        if (venta.getFecha() == null) {
            venta.setFecha(LocalDate.now());
        }
        if (result.hasErrors()) {
            return "venta_form";
        }
        ventaRepository.save(venta);
        return "redirect:/ventas";
    }

    @PostMapping("/ventas/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        ventaRepository.deleteById(id);
        return "redirect:/ventas";
    }
}
