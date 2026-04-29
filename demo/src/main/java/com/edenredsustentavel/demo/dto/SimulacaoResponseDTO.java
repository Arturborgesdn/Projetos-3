package com.edenredsustentavel.demo.dto;

public class SimulacaoResponseDTO {

    //Físico 
    public double carbonoFisico;
    public double aguaFisica;
    public double energiaFisica;
    public double residuosFisicos;

    //Digital 
    public double carbonoDigital;
    public double aguaDigital;
    public double energiaDigital;
    public double residuosDigital;

    //Porcentagem de redução para gráfico pizza
    public double reducaoCarbono;
    public double reducaoAgua;
    public double reducaoEnergia;
    public double reducaoResiduos;

    //Dados Brutos
    public double carbono;
    public double agua;
    public double energia;
    public double residuos;


    // Dados Tangíveis 
    public double entregasDelivery;   // Para CO2
    public double transacoesDigitais; // Para Energia
    public double garrafasPet;        // Para Resíduos 
    public double banhosDeAgua;       // Para Água
}
