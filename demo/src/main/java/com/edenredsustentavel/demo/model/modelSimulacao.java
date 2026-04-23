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
    private Double emissoesCo2;
    private Double consumoAgua;
    private Double residuosPlasticos;

    private LocalDateTime dataCriacao;


    private String tipoEmbalagem;
    private String frequenciaUso;
    private Double taxaReemissao;
    private String destinoFinal;
    private Integer multiplicidadeCartoes; 

    // Dados Tangíveis 
    private Double entregasDelivery;
    private Double transacoesDigitais;
    private Double garrafasPet;
    private Double banhosDeAgua;


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

    public Double getEmissoesCo2() { return emissoesCo2; }
    public void setEmissoesCo2(Double e) { this.emissoesCo2 = e; }

    public Double getConsumoAgua() { return consumoAgua; }
    public void setConsumoAgua(Double a) { this.consumoAgua = a; }

    public Double getResiduosPlasticos() { return residuosPlasticos; }
    public void setResiduosPlasticos(Double p) { this.residuosPlasticos = p; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime d) { this.dataCriacao = d; }

    public String getTipoEmbalagem() { 
        return tipoEmbalagem; 
    }
    public void setTipoEmbalagem(String tipoEmbalagem) { 
        this.tipoEmbalagem = tipoEmbalagem; 
    }

    public String getFrequenciaUso() {
         return frequenciaUso; 
        }
    public void setFrequenciaUso(String frequenciaUso) { 
        this.frequenciaUso = frequenciaUso; 
    }

    public Double getTaxaReemissao() {
         return taxaReemissao; 
        }
    public void setTaxaReemissao(Double taxaReemissao) { 
        this.taxaReemissao = taxaReemissao; 
    }

    public String getDestinoFinal() { 
        return destinoFinal; 
    }
    public void setDestinoFinal(String destinoFinal) { 
        this.destinoFinal = destinoFinal; 
    }

    public Integer getMultiplicidadeCartoes() { 
        return multiplicidadeCartoes; 
    }
    public void setMultiplicidadeCartoes(Integer multiplicidadeCartoes) { 
        this.multiplicidadeCartoes = multiplicidadeCartoes; 
    }

    public Double getEntregasDelivery() { 
        return entregasDelivery; 
    }
    public void setEntregasDelivery(Double entregasDelivery) { 
        this.entregasDelivery = entregasDelivery; 
    }

    public Double getTransacoesDigitais() { 
        return transacoesDigitais; 
    }
    public void setTransacoesDigitais(Double transacoesDigitais) { 
        this.transacoesDigitais = transacoesDigitais; 
    }

    public Double getGarrafasPet() { 
        return garrafasPet; 
    }
    public void setGarrafasPet(Double garrafasPet) { 
        this.garrafasPet = garrafasPet; 
    }

    public Double getBanhosDeAgua() { 
        return banhosDeAgua; 
    }
    public void setBanhosDeAgua(Double banhosDeAgua) { 
        this.banhosDeAgua = banhosDeAgua; 
    }
}