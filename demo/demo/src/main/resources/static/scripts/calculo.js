let barChartInstance = null;
let pieChartInstance = null;

document.getElementById("btnCalcular").addEventListener("click", async () => {
    const form = document.getElementById("calculadoraForm");
    const secaoResultados = document.getElementById("secaoResultados");
    const formData = new FormData(form);

    // ── Pega o email salvo no login ──
    const emailEmpresa = sessionStorage.getItem("emailEmpresa") || "";

    // ── Monta o objeto com todos os dados ──
    const data = {
        qtd_cartoes: Number(formData.get("qtd_cartoes")),
        material: formData.get("material"),
        tipo_embalagem: formData.get("tipo_embalagem"),
        frequencia: formData.get("frequencia"),
        taxa_reemissao: Number(formData.get("taxa_reemissao")),
        destino: formData.get("destino"),
        multiplicidade_cartoes: Number(formData.get("multiplicidade_cartoes")),
        emailEmpresa: emailEmpresa
    };

    try {
        const response = await fetch("/impacto", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        if (!response.ok) throw new Error("Erro no servidor");

        const resultado = await response.json();

        // 1. Mostra a seção de resultados
        secaoResultados.style.display = 'block';

        // 2. Preenche os cards de resultado
        const cards = document.querySelectorAll(".card-number");
        if (cards.length >= 4) {
            cards[0].textContent = resultado.carbono.toFixed(2);
            cards[1].textContent = resultado.agua.toLocaleString('pt-BR');
            cards[2].textContent = resultado.residuos.toFixed(2);
            cards[3].textContent = resultado.energia.toFixed(2);
        }

        // 3. Preenche os dados tangíveis
        const elDelivery = document.getElementById('val-delivery');
        const elDigital = document.getElementById('val-digital');
        const elPet = document.getElementById('val-pet');
        const elBanho = document.getElementById('val-banho');

        if (elDelivery) elDelivery.textContent = Math.round(resultado.entregasDelivery).toLocaleString('pt-BR');
        if (elDigital) elDigital.textContent  = Math.round(resultado.transacoesDigitais).toLocaleString('pt-BR');
        if (elPet) elPet.textContent      = Math.round(resultado.garrafasPet).toLocaleString('pt-BR');
        if (elBanho) elBanho.textContent    = Math.round(resultado.banhosDeAgua).toLocaleString('pt-BR');

        // 4. Renderiza os Gráficos
        if (typeof renderizarGraficos === "function") {
            renderizarGraficos(resultado);
        }

        // 5. Rola até os resultados
        secaoResultados.scrollIntoView({ behavior: 'smooth' });

    } catch (error) {
        console.error("Erro ao calcular:", error);
        alert("Erro ao processar cálculo. Verifique se o backend está rodando.");
    }
});
