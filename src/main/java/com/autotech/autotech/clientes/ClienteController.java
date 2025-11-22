package com.autotech.autotech.clientes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/clientes")
    public String listar(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "clientes";
    }

    @GetMapping("/clientes/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente_form";
    }

    @GetMapping("/clientes/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow();
        model.addAttribute("cliente", cliente);
        return "cliente_form";
    }

    @PostMapping("/clientes")
    public String guardar(@ModelAttribute("cliente") Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            return "cliente_form";
        }
        clienteRepository.save(cliente);
        return "redirect:/clientes";
    }

    @PostMapping("/clientes/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        clienteRepository.deleteById(id);
        return "redirect:/clientes";
    }
}
