document.getElementById("btnCalcular").addEventListener("click", async () => {
    const form = document.getElementById("calculadoraForm");
    const secaoResultados = document.getElementById("secaoResultados");
    const formData = new FormData(form);

    const data = {
        qtd_cartoes: Number(formData.get("qtd_cartoes")),
        material: formData.get("material"),
        tipo_embalagem: formData.get("tipo_embalagem"),
        frequencia: formData.get("frequencia"),
        taxa_reemissao: Number(formData.get("taxa_reemissao")),
        destino: formData.get("destino"),
        multiplicidade_cartoes: Number(formData.get("multiplicidade_cartoes"))
    };

    try {
        const response = await fetch("http://localhost:8080/impacto", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        if (!response.ok) throw new Error("Erro no servidor");

        const resultado = await response.json();

        // 1. Mostra a seção de resultados que estava escondida
        secaoResultados.style.display = 'block';

        // 2. Captura todos os spans que possuem a classe 'card-number'
        const cards = document.querySelectorAll(".card-number");

        // 3. Insere os valores na ordem exata do seu HTML
        // Card 0: Emissão de CO2 (kg)
        cards[0].textContent = resultado.carbono.toFixed(2);
        // Card 1: Consumo de Água (litros)
        cards[1].textContent = resultado.agua.toLocaleString('pt-BR');
        // Card 2: Resíduos Plásticos (kg)
        cards[2].textContent = resultado.residuos.toFixed(2);
        // Card 3: Consumo de Energia (kWh)
        cards[3].textContent = resultado.energia.toFixed(2);

        // Cards Tangíveis
        document.getElementById('val-delivery').textContent = Math.round(resultado.entregasDelivery).toLocaleString('pt-BR');
        document.getElementById('val-digital').textContent  = Math.round(resultado.transacoesDigitais).toLocaleString('pt-BR');
        document.getElementById('val-pet').textContent      = Math.round(resultado.garrafasPet).toLocaleString('pt-BR');
        document.getElementById('val-banho').textContent    = Math.round(resultado.banhosDeAgua).toLocaleString('pt-BR');

        // 4. Rola a tela suavemente até os resultados
        secaoResultados.scrollIntoView({ behavior: 'smooth' });

    } catch (error) {
        console.error("Erro ao calcular:", error);
        alert("Erro ao processar cálculo. Verifique se o backend está rodando.");
    }
});