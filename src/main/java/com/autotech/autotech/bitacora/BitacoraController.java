package com.autotech.autotech.bitacora;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BitacoraController {

    private final BitacoraRepository bitacoraRepository;

    public BitacoraController(BitacoraRepository bitacoraRepository) {
        this.bitacoraRepository = bitacoraRepository;
    }

    @GetMapping("/bitacora")
    public String listar(Model model) {
        model.addAttribute("eventos", bitacoraRepository.findAll());
        return "bitacora";
    }

    @PostMapping("/bitacora/demo")
    public String registrarDemo() {
        BitacoraEntrada entrada = new BitacoraEntrada();
        entrada.setAccion("Registro de prueba");
        entrada.setModulo("Demo");
        entrada.setUsuario("sistema");
        entrada.setFecha(LocalDateTime.now());
        bitacoraRepository.save(entrada);
        return "redirect:/bitacora";
    }
}
