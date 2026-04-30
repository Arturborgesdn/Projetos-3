package com.edenredsustentavel.demo.controller;

import com.edenredsustentavel.demo.model.modelEmpresa;
import com.edenredsustentavel.demo.service.serviceEdenred;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class controllerLogin {

    @Autowired
    private serviceEdenred serviceEdenred;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> dados, jakarta.servlet.http.HttpSession session) {
        String email = dados.get("email");
        String senha = dados.get("senha");

        modelEmpresa empresa = serviceEdenred.login(email, senha);

        if (empresa != null) {
            session.setAttribute("usuarioLogado", empresa.getEmail());
            return ResponseEntity.ok(Map.of(
                "mensagem", "Login realizado com sucesso",
                "empresa", empresa.getNome(),
                "email", empresa.getEmail()
            ));
        }

        return ResponseEntity.status(401).body(Map.of(
            "mensagem", "Email ou senha inválidos"
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(jakarta.servlet.http.HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("mensagem", "Logout realizado com sucesso"));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody modelEmpresa empresa) {
        try {
            modelEmpresa nova = serviceEdenred.cadastrar(empresa);
            return ResponseEntity.ok(Map.of(
                "mensagem", "Empresa cadastrada com sucesso",
                "empresa", nova.getNome(),
                "email", nova.getEmail()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of(
                "mensagem", e.getMessage()
            ));
        }
    }
}
