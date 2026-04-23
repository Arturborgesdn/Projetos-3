package com.edenredsustentavel.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dados_simulacao")
public class modelDadosSimulacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private modelEmpresa empresa;

    // Entradas
    private Integer qtdCartoes;
    private String material;
    private String tipoEmbalagem;
    private String frequencia;
    private Double taxaReemissao;
    private String destino;
    private Integer multiplicidadeCartoes;

    // Resultados ambientais
    private Double emissaoCo2Kg;
    private Double consumoAguaLitros;
    private Double residuosPlasticosKg;
    private Double energiaKwh;

    private LocalDateTime dataCriacao;

    @OneToOne(mappedBy = "simulacao", cascade = CascadeType.ALL)
    private modelDadosTangiveis dadosTangiveis;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public modelEmpresa getEmpresa() { return empresa; }
    public void setEmpresa(modelEmpresa empresa) { this.empresa = empresa; }

    public Integer getQtdCartoes() { return qtdCartoes; }
    public void setQtdCartoes(Integer v) { this.qtdCartoes = v; }

    public String getMaterial() { return material; }
    public void setMaterial(String v) { this.material = v; }

    public String getTipoEmbalagem() { return tipoEmbalagem; }
    public void setTipoEmbalagem(String v) { this.tipoEmbalagem = v; }

    public String getFrequencia() { return frequencia; }
    public void setFrequencia(String v) { this.frequencia = v; }

    public Double getTaxaReemissao() { return taxaReemissao; }
    public void setTaxaReemissao(Double v) { this.taxaReemissao = v; }

    public String getDestino() { return destino; }
    public void setDestino(String v) { this.destino = v; }

    public Integer getMultiplicidadeCartoes() { return multiplicidadeCartoes; }
    public void setMultiplicidadeCartoes(Integer v) { this.multiplicidadeCartoes = v; }

    public Double getEmissaoCo2Kg() { return emissaoCo2Kg; }
    public void setEmissaoCo2Kg(Double v) { this.emissaoCo2Kg = v; }

    public Double getConsumoAguaLitros() { return consumoAguaLitros; }
    public void setConsumoAguaLitros(Double v) { this.consumoAguaLitros = v; }

    public Double getResiduosPlasticosKg() { return residuosPlasticosKg; }
    public void setResiduosPlasticosKg(Double v) { this.residuosPlasticosKg = v; }

    public Double getEnergiaKwh() { return energiaKwh; }
    public void setEnergiaKwh(Double v) { this.energiaKwh = v; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime v) { this.dataCriacao = v; }

    public modelDadosTangiveis getDadosTangiveis() { return dadosTangiveis; }
    public void setDadosTangiveis(modelDadosTangiveis v) { this.dadosTangiveis = v; }
}
