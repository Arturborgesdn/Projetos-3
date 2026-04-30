package com.edenredsustentavel.demo.controller;

import com.edenredsustentavel.demo.dto.SimulacaoRequestDTO;
import com.edenredsustentavel.demo.dto.SimulacaoResponseDTO;
import com.edenredsustentavel.demo.repository.repositoryDadosSimulacao;
import com.edenredsustentavel.demo.repository.repositoryEmpresa;
import com.edenredsustentavel.demo.service.serviceEdenred;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/impacto")
@CrossOrigin
public class SimulacaoController {

    @Autowired
    private serviceEdenred service;

    @Autowired
    private repositoryEmpresa repositoryEmpresa;

    @Autowired
    private repositoryDadosSimulacao repoDadosSimulacao;

    @PostMapping
    public SimulacaoResponseDTO calcular(@RequestBody SimulacaoRequestDTO request) {
        return service.calcularImpacto(request);
    }

    @GetMapping("/historico/{email}")
    public ResponseEntity<?> ultimaSimulacao(@PathVariable String email) {
        return repositoryEmpresa.findByEmail(email)
            .map(empresa -> repoDadosSimulacao
                .findFirstByEmpresaIdOrderByDataCriacaoDesc(empresa.getId())
                .map(sim -> ResponseEntity.ok(sim))
                .orElse(ResponseEntity.noContent().build()))
            .orElse(ResponseEntity.notFound().build());
    }
}