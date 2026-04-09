package com.edenredsustentavel.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CadastroController {

    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro"; // aponta para templates/cadastro.html
    }
}