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
        const response = await fetch("http://localhost:8080/impacto", {
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
        cards[0].textContent = resultado.carbono.toFixed(2);
        cards[1].textContent = resultado.agua.toLocaleString('pt-BR');
        cards[2].textContent = resultado.residuos.toFixed(2);
        cards[3].textContent = resultado.energia.toFixed(2);

        // 3. Preenche os dados tangíveis
        document.getElementById('val-delivery').textContent = Math.round(resultado.entregasDelivery).toLocaleString('pt-BR');
        document.getElementById('val-digital').textContent  = Math.round(resultado.transacoesDigitais).toLocaleString('pt-BR');
        document.getElementById('val-pet').textContent      = Math.round(resultado.garrafasPet).toLocaleString('pt-BR');
        document.getElementById('val-banho').textContent    = Math.round(resultado.banhosDeAgua).toLocaleString('pt-BR');

        // 4. Rola até os resultados
        secaoResultados.scrollIntoView({ behavior: 'smooth' });

        // 5. Busca e exibe o histórico da última simulação
        if (emailEmpresa) {
            const hist = await fetch(`http://localhost:8080/impacto/historico/${emailEmpresa}`);

            if (hist.status === 200) {
                const ultima = await hist.json();
                const secaoHistorico = document.getElementById("secaoHistorico");

                if (secaoHistorico) {
                    secaoHistorico.style.display = "block";

                    document.getElementById("histCo2").textContent        = ultima.emissaoCo2Kg?.toFixed(2) ?? "-";
                    document.getElementById("histAgua").textContent       = ultima.consumoAguaLitros?.toFixed(2) ?? "-";
                    document.getElementById("histPlastico").textContent   = ultima.residuosPlasticosKg?.toFixed(2) ?? "-";
                    document.getElementById("histEnergia").textContent    = ultima.energiaKwh?.toFixed(2) ?? "-";
                    document.getElementById("histEntregas").textContent   = ultima.dadosTangiveis?.entregasDelivery?.toFixed(0) ?? "-";
                    document.getElementById("histGarrafas").textContent   = ultima.dadosTangiveis?.garrafasPet?.toFixed(0) ?? "-";
                    document.getElementById("histBanhos").textContent     = ultima.dadosTangiveis?.banhosDeAgua?.toFixed(0) ?? "-";
                    document.getElementById("histTransacoes").textContent = ultima.dadosTangiveis?.transacoesDigitais?.toFixed(0) ?? "-";
                }
            }
        }

    } catch (error) {
        console.error("Erro ao calcular:", error);
        alert("Erro ao processar cálculo. Verifique se o backend está rodando.");
    }
});