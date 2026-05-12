package com.edenredsustentavel.demo.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edenredsustentavel.demo.dto.SimulacaoRequestDTO;
import com.edenredsustentavel.demo.dto.SimulacaoResponseDTO;
import com.edenredsustentavel.demo.repository.repositoryDadosSimulacao;
import com.edenredsustentavel.demo.repository.repositoryEmpresa;
import com.edenredsustentavel.demo.service.serviceEdenred;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/impacto")
@CrossOrigin
public class SimulacaoController {
   
    @GetMapping("/check-selo")
    public Map<String, Boolean> checkSelo(
            @RequestParam String email
    ) {

        boolean autorizado = true;

        return Map.of(
            "autorizado",
            autorizado
        );
}

    @Autowired
    private serviceEdenred service;

    @Autowired
    private repositoryEmpresa repositoryEmpresa;

    @Autowired
    private repositoryDadosSimulacao repoDadosSimulacao;

    @PostMapping
    public SimulacaoResponseDTO calcular(@RequestBody SimulacaoRequestDTO request, HttpSession session) {
        if ((request.emailEmpresa == null || request.emailEmpresa.isBlank())
                && session.getAttribute("usuarioLogado") != null) {
            request.emailEmpresa = session.getAttribute("usuarioLogado").toString();
        }
        return service.calcularImpacto(request);
    }

    @GetMapping("/historico")
    public ResponseEntity<?> ultimaSimulacao(HttpSession session) {
        Object usuarioLogado = session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email = usuarioLogado.toString();
        return repositoryEmpresa.findByEmail(email)
            .map(empresa -> repoDadosSimulacao
                .findFirstByEmpresaIdOrderByDataCriacaoDesc(empresa.getId())
                .map(sim -> ResponseEntity.ok(sim))
                .orElse(ResponseEntity.noContent().build()))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/historico/{email}")
    public ResponseEntity<?> ultimaSimulacaoPorEmail(@PathVariable String email) {
        return repositoryEmpresa.findByEmail(email)
            .map(empresa -> repoDadosSimulacao
                .findFirstByEmpresaIdOrderByDataCriacaoDesc(empresa.getId())
                .map(sim -> ResponseEntity.ok(sim))
                .orElse(ResponseEntity.noContent().build()))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/historico/todos/{email}")
    public ResponseEntity<?> todasSimulacoes(@PathVariable String email) {
        return repositoryEmpresa.findByEmail(email)
            .map(empresa -> ResponseEntity.ok(repoDadosSimulacao.findByEmpresaIdOrderByDataCriacaoDesc(empresa.getId())))
            .orElse(ResponseEntity.notFound().build());
    }
    }