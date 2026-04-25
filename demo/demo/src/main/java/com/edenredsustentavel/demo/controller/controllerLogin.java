package com.edenredsustentavel.demo.controller;

import com.edenredsustentavel.demo.model.modelEmpresa;
import com.edenredsustentavel.demo.service.serviceEdenred;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class controllerLogin {

    @Autowired
    private serviceEdenred serviceEdenred;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> dados) {
        String cnpj = dados.get("cnpj");
        String senha = dados.get("senha");

        modelEmpresa empresa = serviceEdenred.login(cnpj, senha);

        if (empresa != null) {
            return ResponseEntity.ok(Map.of(
                "mensagem", "Login realizado com sucesso",
                "empresa", empresa.getNome(),
                "cnpj", empresa.getCnpj()
            ));
        }

        return ResponseEntity.status(401).body(Map.of(
            "mensagem", "CNPJ ou senha inválidos"
        ));
    }
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody modelEmpresa empresa) {
        try {
            modelEmpresa nova = serviceEdenred.cadastrar(empresa);
            return ResponseEntity.ok(Map.of(
                "mensagem", "Empresa cadastrada com sucesso",
                "empresa", nova.getNome(),
                "cnpj", nova.getCnpj()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of(
                "mensagem", e.getMessage()
            ));
        }
    }
}