
package com.edenredsustentavel.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edenredsustentavel.demo.dto.SimulacaoRequestDTO;
import com.edenredsustentavel.demo.dto.SimulacaoResponseDTO;
import com.edenredsustentavel.demo.service.serviceEdenred;

@RestController
@RequestMapping("/impacto")
@CrossOrigin
public class SimulacaoController {

    @Autowired
    private serviceEdenred service;

    @PostMapping
    public SimulacaoResponseDTO calcular(@RequestBody SimulacaoRequestDTO request) {
    return service.calcularImpacto(request);
    }
}
