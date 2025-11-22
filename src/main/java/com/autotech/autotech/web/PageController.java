package com.autotech.autotech.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/reportes")
    public String reportes() {
        return "reportes";
    }

    @GetMapping("/configuracion")
    public String configuracion() {
        return "configuracion";
    }

    @GetMapping("/ayuda")
    public String ayuda() {
        return "ayuda";
    }
}
