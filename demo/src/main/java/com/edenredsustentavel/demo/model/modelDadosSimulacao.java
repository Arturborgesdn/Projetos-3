package com.edenredsustentavel.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "dados_simulacao")
public class modelDadosSimulacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empresa_id")  // ✅ removido nullable = false
    @JsonIgnore
    private modelEmpresa empresa;

    private Integer qtdCartoes;
    private String material;
    private String tipoEmbalagem;
    private String frequencia;
    private Double taxaReemissao;
    private String destino;
    private Integer multiplicidadeCartoes;

    private Double emissaoCo2Kg;
    private Double consumoAguaLitros;
    private Double residuosPlasticosKg;
    private Double energiaKwh;

    // Cenário Digital
    private Double carbonoDigital;
    private Double aguaDigital;
    private Double residuosDigital;
    private Double energiaDigital;

    // Percentagens de Redução
    private Double reducaoCarbono;
    private Double reducaoAgua;
    private Double reducaoEnergia;
    private Double reducaoResiduos;

    private LocalDateTime dataCriacao;

    @OneToOne(mappedBy = "simulacao", cascade = CascadeType.ALL)
    private modelDadosTangiveis dadosTangiveis;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }

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

    public Double getCarbonoDigital() { return carbonoDigital; }
    public void setCarbonoDigital(Double v) { this.carbonoDigital = v; }

    public Double getAguaDigital() { return aguaDigital; }
    public void setAguaDigital(Double v) { this.aguaDigital = v; }

    public Double getResiduosDigital() { return residuosDigital; }
    public void setResiduosDigital(Double v) { this.residuosDigital = v; }

    public Double getEnergiaDigital() { return energiaDigital; }
    public void setEnergiaDigital(Double v) { this.energiaDigital = v; }

    public Double getReducaoCarbono() { return reducaoCarbono; }
    public void setReducaoCarbono(Double v) { this.reducaoCarbono = v; }

    public Double getReducaoAgua() { return reducaoAgua; }
    public void setReducaoAgua(Double v) { this.reducaoAgua = v; }

    public Double getReducaoEnergia() { return reducaoEnergia; }
    public void setReducaoEnergia(Double v) { this.reducaoEnergia = v; }

    public Double getReducaoResiduos() { return reducaoResiduos; }
    public void setReducaoResiduos(Double v) { this.reducaoResiduos = v; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime v) { this.dataCriacao = v; }

    public modelDadosTangiveis getDadosTangiveis() { return dadosTangiveis; }
    public void setDadosTangiveis(modelDadosTangiveis v) { this.dadosTangiveis = v; }
}
