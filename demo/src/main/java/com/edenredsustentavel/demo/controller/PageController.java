package com.edenredsustentavel.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/historico")
    public String historico() {
        return "historico";
    }

    @GetMapping("/calculadora")
    public String calculadora() {
        return "calculadora";
    }

    @GetMapping("/por-pessoas")
    public String porPessoas() {
        return "index";
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