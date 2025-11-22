package com.autotech.autotech.roles;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RolController {

    private final RolRepository rolRepository;

    public RolController(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @GetMapping("/roles")
    public String listar(Model model) {
        model.addAttribute("roles", rolRepository.findAll());
        return "roles";
    }

    @GetMapping("/roles/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("rol", new Rol());
        return "roles";
    }

    @GetMapping("/roles/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("rol", rolRepository.findById(id).orElseThrow());
        model.addAttribute("roles", rolRepository.findAll());
        return "roles";
    }

    @PostMapping("/roles")
    public String guardar(@ModelAttribute("rol") Rol rol, BindingResult result) {
        if (result.hasErrors()) {
            return "roles";
        }
        rolRepository.save(rol);
        return "redirect:/roles";
    }

    @PostMapping("/roles/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        rolRepository.deleteById(id);
        return "redirect:/roles";
    }
}
