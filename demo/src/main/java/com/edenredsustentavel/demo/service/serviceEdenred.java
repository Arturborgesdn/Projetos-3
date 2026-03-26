package com.edenredsustentavel.demo.service;

import com.edenredsustentavel.demo.dto.SimulacaoRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class ServiceEdenred {

    public double calcularImpacto(SimulacaoRequestDTO req) {

        // Total de cartões
        int cartoesTotais = req.qtd_cartoes * req.multiplicidade_cartoes;

        // Reemissão
        double reemissao = cartoesTotais * (req.taxa_reemissao / 100.0);

        double totalCartoes = cartoesTotais + reemissao;

        // ===== FATORES REAIS =====
        double emissaoCartao = 0.011; // kg CO2 por cartão (PVC)
        double emissaoPapel = 0.005;  // kg CO2 por carta

        // ===== MATERIAL =====
        double fatorMaterial = req.material.equals("pvc_reciclado") ? 0.7 : 1.0;

        // ===== EMBALAGEM =====
        double fatorEmbalagem;
        switch (req.tipo_embalagem) {
            case "kit_padrao":
                fatorEmbalagem = 1.0;
                break;
            case "kit_premium":
                fatorEmbalagem = 1.5;
                break;
            default:
                fatorEmbalagem = 0.8;
        }

        // ===== CÁLCULO BASE =====
        double emissaoTotal =
                totalCartoes * emissaoCartao * fatorMaterial +
                totalCartoes * emissaoPapel * fatorEmbalagem;

        // ===== DESTINO FINAL =====
        double fatorDestino = 1.0;
        if (req.destino.equals("descarte_comum")) {
            fatorDestino = 1.2;
        } else if (req.destino.equals("reciclagem")) {
            fatorDestino = 0.7;
        }

        return emissaoTotal * fatorDestino;
    }
}