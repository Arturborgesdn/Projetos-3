package com.edenredsustentavel.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "simulacoes")
public class modelSimulacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento com empresa
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private modelEmpresa empresa;

    // Dados de entrada
    private Integer quantidadeCartoesFisicos;
    private Integer volumeTransacoesMensais;
    private String materialCartao;

    // Resultados
    private Double co2Economizado;
    private Double aguaEconomizada;
    private Double plasticoEconomizado;

    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public modelEmpresa getEmpresa() { return empresa; }
    public void setEmpresa(modelEmpresa empresa) { this.empresa = empresa; }

    public Integer getQuantidadeCartoesFisicos() { return quantidadeCartoesFisicos; }
    public void setQuantidadeCartoesFisicos(Integer q) { this.quantidadeCartoesFisicos = q; }

    public Integer getVolumeTransacoesMensais() { return volumeTransacoesMensais; }
    public void setVolumeTransacoesMensais(Integer v) { this.volumeTransacoesMensais = v; }

    public String getMaterialCartao() { return materialCartao; }
    public void setMaterialCartao(String m) { this.materialCartao = m; }

    public Double getCo2Economizado() { return co2Economizado; }
    public void setCo2Economizado(Double c) { this.co2Economizado = c; }

    public Double getAguaEconomizada() { return aguaEconomizada; }
    public void setAguaEconomizada(Double a) { this.aguaEconomizada = a; }

    public Double getPlasticoEconomizado() { return plasticoEconomizado; }
    public void setPlasticoEconomizado(Double p) { this.plasticoEconomizado = p; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime d) { this.dataCriacao = d; }
}