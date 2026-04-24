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
}