
package com.edenredsustentavel.demo.controller;

import com.edenredsustentavel.demo.dto.SimulacaoRequestDTO;
import com.edenredsustentavel.demo.dto.SimulacaoResponseDTO;
import com.edenredsustentavel.demo.service.serviceEdenred;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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