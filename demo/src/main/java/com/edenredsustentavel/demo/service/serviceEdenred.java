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
    public modelEmpresa login(String email, String senha) {
        Optional<modelEmpresa> empresa = repositoryEmpresa.findByEmail(email);
        if (empresa.isPresent() && empresa.get().getSenha().equals(senha)) {
            return empresa.get();
        }
        return null;
    }

// ── Cadastro ───────────────────────────────────────────
    public modelEmpresa cadastrar(modelEmpresa empresa) {
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

        
        // Mapeamento do Cenário Físico (Dados Brutos calculados anteriormente)
        response.carbonoFisico = response.carbono;
        response.aguaFisica = response.agua;
        response.energiaFisica = response.energia;
        response.residuosFisicos = response.residuos;

        // Cálculo do Cenário Digital
        response.residuosDigital = 0.0; 
        response.aguaDigital = 0.0;     
        response.energiaDigital = req.volumeTransacoes * 0.0004; 
        response.carbonoDigital = response.energiaDigital * 0.085; 

        // Cálculo das Percentagens de Redução
        if (response.carbonoFisico > 0) {
            response.reducaoCarbono = ((response.carbonoFisico - response.carbonoDigital) / response.carbonoFisico) * 100;
        } else {
            response.reducaoCarbono = 0.0;
        }

        response.reducaoAgua = 100.0; 

        if (response.energiaFisica > 0) {
            response.reducaoEnergia = ((response.energiaFisica - response.energiaDigital) / response.energiaFisica) * 100;
        } else {
            response.reducaoEnergia = 0.0;
        }

        response.reducaoResiduos = 100.0;

        // Atualização dos Dados Tangíveis baseada na Economia Real (Físico - Digital)
        double poupadoCarbono = response.carbonoFisico - response.carbonoDigital;
        double poupadoEnergia = response.energiaFisica - response.energiaDigital;

        // Dados tangíveis
        response.entregasDelivery = poupadoCarbono / 1.2;
        response.transacoesDigitais = poupadoEnergia / 0.0004;
        response.garrafasPet = response.residuosFisicos / 0.045;
        response.banhosDeAgua = response.aguaFisica / 90.0;

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