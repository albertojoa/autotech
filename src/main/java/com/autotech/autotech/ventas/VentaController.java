package com.autotech.autotech.ventas;

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

import com.autotech.autotech.clientes.Cliente;

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
    public String listar(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
        @RequestParam(required = false) String filtroCliente,
        @RequestParam(required = false) String filtroItem,
        Model model) {

        List<Venta> ventas = ventaRepository.buscarConFiltros(fechaInicio, fechaFin, filtroCliente, filtroItem);
        model.addAttribute("ventas", ventas);
        model.addAttribute("filtroCliente", filtroCliente);
        model.addAttribute("filtroItem", filtroItem);
        model.addAttribute("fechaInicio", fechaInicio);
        model.addAttribute("fechaFin", fechaFin);
        return "ventas";
    }

    @GetMapping("/ventas/nuevo")
    public String nuevo(Model model) {
        cargarFormulario(model, new Venta());
        model.addAttribute("venta", new Venta());
        model.addAttribute("clientes", clienteRepository.findAll());
        return "venta_form";
    }

    @GetMapping("/ventas/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Venta venta = ventaRepository.findById(id).orElseThrow();
        cargarFormulario(model, venta);
        model.addAttribute("venta", venta);
        model.addAttribute("clientes", clienteRepository.findAll());
        return "venta_form";
    }

    @PostMapping("/ventas")
    public String guardar(
        @ModelAttribute("venta") Venta venta,
        BindingResult result,
        @RequestParam("clienteId") Long clienteId,
        Model model) {

        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        venta.setCliente(cliente);

        if (venta.getFecha() == null) {
            venta.setFecha(LocalDate.now());
        }

        if (result.hasErrors()) {
            cargarFormulario(model, venta);
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

    private void cargarFormulario(Model model, Venta venta) {
        model.addAttribute("venta", venta);
        model.addAttribute("clientes", clienteRepository.findAll());
        Long clienteSeleccionado = venta.getCliente() != null ? venta.getCliente().getId() : null;
        model.addAttribute("clienteSeleccionado", clienteSeleccionado);
    }
}
