package com.edenredsustentavel.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index"; // serve templates/index.html
    }

    @GetMapping("/calculadora")
    public String calculadora() {
        return "calculadora"; // serve templates/calculadora.html
    }

    @GetMapping("/por-pessoas")
    public String porPessoas() {
        return "index"; // Lembre-se de criar um HTML para esta página depois!
    }

    @GetMapping("/para-pessoas")
    public String paraPessoas() {
        return "index";
    }

    @GetMapping("/sustentabilidade")
    public String sustentabilidade() {
        return "index";
    }

    @GetMapping("/inovacao")
    public String inovacao() {
        return "index";
    }

    @GetMapping("/noticias")
    public String noticias() {
        return "index";
    }

    @GetMapping("/carreiras")
    public String carreiras() {
        return "index";
    }

    @GetMapping("/compliance")
    public String compliance() {
        return "index";
    }
}