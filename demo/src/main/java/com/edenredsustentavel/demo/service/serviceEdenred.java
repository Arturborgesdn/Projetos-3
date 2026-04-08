package com.edenredsustentavel.demo.service;

import com.edenredsustentavel.demo.dto.SimulacaoRequestDTO;
import com.edenredsustentavel.demo.dto.SimulacaoResponseDTO;

import org.springframework.stereotype.Service;

import com.edenredsustentavel.demo.model.modelEmpresa;
import com.edenredsustentavel.demo.repository.repositoryEmpresa;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class serviceEdenred {

    public SimulacaoResponseDTO calcularImpacto(SimulacaoRequestDTO req) {
    SimulacaoResponseDTO response = new SimulacaoResponseDTO();

    // 1. Cálculo da base de cartões (Produção + Reemissão)
    int cartoesTotais = req.qtd_cartoes * req.multiplicidade_cartoes;
    double reemissao = cartoesTotais * (req.taxa_reemissao / 100.0);
    double totalCartoes = cartoesTotais + reemissao;

    // 2. Definição de Fatores de Material e Embalagem
    double fatorMaterial = req.material.equals("pvc_reciclado") ? 0.7 : 1.0;
    
    double fatorEmbalagem;
    switch (req.tipo_embalagem) {
        case "kit_premium": fatorEmbalagem = 1.5; break;
        case "kit_padrao": fatorEmbalagem = 1.0; break;
        default: fatorEmbalagem = 0.8;
    }

    // 3. CÁLCULOS DOS INDICADORES
    
    // Carbono (kg CO2) - Lógica que você já tinha
    double emissaoCartao = 0.011; 
    double emissaoPapel = 0.005;
    double baseCarbono = (totalCartoes * emissaoCartao * fatorMaterial) + 
                         (totalCartoes * emissaoPapel * fatorEmbalagem);
    
    // Ajuste de destino final
    double fatorDestino = req.destino.equals("reciclagem") ? 0.7 : (req.destino.equals("descarte_comum") ? 1.2 : 1.0);
    response.carbono = baseCarbono * fatorDestino;

    // Água (Litros) - Ex: 0.15L por cartão PVC
    response.agua = totalCartoes * 0.15 * fatorMaterial;

    // Energia (kWh) - Ex: 0.02kWh por cartão
    response.energia = totalCartoes * 0.02 * fatorMaterial;

    // Resíduos Plásticos (kg) - Peso médio de um cartão é ~0.005kg
    // Se for reciclado, o resíduo "final" para o planeta é menor
    double pesoPlastico = totalCartoes * 0.005;
    response.residuos = req.destino.equals("reciclagem") ? pesoPlastico * 0.1 : pesoPlastico;

    return response;
    }
        // Adicionar dentro da classe, antes do último }
    @Autowired
    private repositoryEmpresa repositoryEmpresa;

    public modelEmpresa login(String cnpj, String senha) {
        Optional<modelEmpresa> empresa = repositoryEmpresa.findByCnpj(cnpj);

    if (empresa.isPresent() && empresa.get().getSenha().equals(senha)) {
        return empresa.get();
    }

    return null;
    }
}