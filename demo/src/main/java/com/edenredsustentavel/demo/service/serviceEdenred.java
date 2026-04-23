package com.edenredsustentavel.demo.service;

import com.edenredsustentavel.demo.dto.SimulacaoRequestDTO;
import com.edenredsustentavel.demo.dto.SimulacaoResponseDTO;
import com.edenredsustentavel.demo.model.modelDadosSimulacao;
import com.edenredsustentavel.demo.model.modelDadosTangiveis;
import com.edenredsustentavel.demo.model.modelEmpresa;
import com.edenredsustentavel.demo.repository.repositoryDadosSimulacao;
import com.edenredsustentavel.demo.repository.repositoryDadosTangiveis;
import com.edenredsustentavel.demo.repository.repositoryEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class serviceEdenred {

    @Autowired
    private repositoryEmpresa repositoryEmpresa;

    @Autowired
    private repositoryDadosSimulacao repoDadosSimulacao;

    @Autowired
    private repositoryDadosTangiveis repoDadosTangiveis;

    // ── Login ──────────────────────────────────────────────
    public modelEmpresa login(String cnpj, String senha) {
        Optional<modelEmpresa> empresa = repositoryEmpresa.findByCnpj(cnpj);
        if (empresa.isPresent() && empresa.get().getSenha().equals(senha)) {
            return empresa.get();
        }
        return null;
    }

    // ── Cadastro ───────────────────────────────────────────
    public modelEmpresa cadastrar(modelEmpresa empresa) {
        if (repositoryEmpresa.findByCnpj(empresa.getCnpj()).isPresent()) {
            throw new RuntimeException("CNPJ já cadastrado");
        }
        if (repositoryEmpresa.findByEmail(empresa.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        return repositoryEmpresa.save(empresa);
    }

    // ── Cálculo + Persistência ─────────────────────────────
    public SimulacaoResponseDTO calcularImpacto(SimulacaoRequestDTO req) {
        SimulacaoResponseDTO response = new SimulacaoResponseDTO();

        // 1. Base de cartões
        int cartoesTotais   = req.qtd_cartoes * req.multiplicidade_cartoes;
        double reemissao    = cartoesTotais * (req.taxa_reemissao / 100.0);
        double totalCartoes = cartoesTotais + reemissao;

        // 2. Fatores
        double fatorMaterial  = req.material.equals("pvc_reciclado") ? 0.7 : 1.0;
        double fatorEmbalagem;
        switch (req.tipo_embalagem) {
            case "kit_premium": fatorEmbalagem = 1.5; break;
            case "kit_padrao":  fatorEmbalagem = 1.0; break;
            default:            fatorEmbalagem = 0.8;
        }

        // 3. Cálculos ambientais
        double baseCarbono  = (totalCartoes * 0.011 * fatorMaterial)
                            + (totalCartoes * 0.005 * fatorEmbalagem);
        double fatorDestino = req.destino.equals("reciclagem")     ? 0.7
                            : req.destino.equals("descarte_comum") ? 1.2 : 1.0;

        response.carbono  = baseCarbono * fatorDestino;
        response.agua     = totalCartoes * 0.15 * fatorMaterial;
        response.energia  = totalCartoes * 0.02 * fatorMaterial;

        double pesoPlastico = totalCartoes * 0.005;
        response.residuos   = req.destino.equals("reciclagem") ? pesoPlastico * 0.1 : pesoPlastico;

        // 4. Dados tangíveis
        response.entregasDelivery   = response.carbono  / 1.2;
        response.transacoesDigitais = response.energia  / 0.0004;
        response.garrafasPet        = response.residuos / 0.045;
        response.banhosDeAgua       = response.agua     / 90.0;

        // 5. Salva tabela dados_simulacao
        modelDadosSimulacao simulacao = new modelDadosSimulacao();
        simulacao.setQtdCartoes(req.qtd_cartoes);
        simulacao.setMaterial(req.material);
        simulacao.setTipoEmbalagem(req.tipo_embalagem);
        simulacao.setFrequencia(req.frequencia);
        simulacao.setTaxaReemissao(req.taxa_reemissao);
        simulacao.setDestino(req.destino);
        simulacao.setMultiplicidadeCartoes(req.multiplicidade_cartoes);
        simulacao.setEmissaoCo2Kg(response.carbono);
        simulacao.setConsumoAguaLitros(response.agua);
        simulacao.setResiduosPlasticosKg(response.residuos);
        simulacao.setEnergiaKwh(response.energia);
        repoDadosSimulacao.save(simulacao);

        // 6. Salva tabela dados_tangiveis
        modelDadosTangiveis tangiveis = new modelDadosTangiveis();
        tangiveis.setSimulacao(simulacao);
        tangiveis.setEntregasDelivery(response.entregasDelivery);
        tangiveis.setTransacoesDigitais(response.transacoesDigitais);
        tangiveis.setGarrafasPet(response.garrafasPet);
        tangiveis.setBanhosDeAgua(response.banhosDeAgua);
        repoDadosTangiveis.save(tangiveis);

        return response;
    }
}