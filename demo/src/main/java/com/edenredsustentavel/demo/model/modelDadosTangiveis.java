package com.edenredsustentavel.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dados_tangiveis")
public class modelDadosTangiveis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "simulacao_id", nullable = false, unique = true)
    private modelDadosSimulacao simulacao;

    private Double entregasDelivery;
    private Double transacoesDigitais;
    private Double garrafasPet;
    private Double banhosDeAgua;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public modelDadosSimulacao getSimulacao() { return simulacao; }
    public void setSimulacao(modelDadosSimulacao simulacao) { this.simulacao = simulacao; }

    public Double getEntregasDelivery() { return entregasDelivery; }
    public void setEntregasDelivery(Double v) { this.entregasDelivery = v; }

    public Double getTransacoesDigitais() { return transacoesDigitais; }
    public void setTransacoesDigitais(Double v) { this.transacoesDigitais = v; }

    public Double getGarrafasPet() { return garrafasPet; }
    public void setGarrafasPet(Double v) { this.garrafasPet = v; }

    public Double getBanhosDeAgua() { return banhosDeAgua; }
    public void setBanhosDeAgua(Double v) { this.banhosDeAgua = v; }
}