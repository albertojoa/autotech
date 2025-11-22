package com.autotech.autotech.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/usuarios")
    public String listar(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuarios";
    }

    @GetMapping("/usuarios/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioRepository.findById(id).orElseThrow());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuarios";
    }

    @PostMapping("/usuarios")
    public String guardar(@ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("usuarios", usuarioRepository.findAll());
            return "usuarios";
        }
        usuarioRepository.save(usuario);
        return "redirect:/usuarios";
    }

    @PostMapping("/usuarios/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/usuarios";
    }
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuarios";
    }
}
