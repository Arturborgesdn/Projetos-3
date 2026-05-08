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
    emailEmpresa: emailEmpresa,
  };

  try {
    const response = await fetch("/impacto", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    });

    if (!response.ok) throw new Error("Erro no servidor");

    const resultado = await response.json();

    // ── Marca que o usuário já fez uma simulação ──
    sessionStorage.setItem("simulacaoFeita", "true");

    // 1. Mostra a seção de resultados
    secaoResultados.style.display = "block";

    // 2. Preenche os cards de resultado
    const cards = document.querySelectorAll(".card-number");
    cards[0].textContent = resultado.carbono.toFixed(2);
    cards[1].textContent = resultado.agua.toLocaleString("pt-BR");
    cards[2].textContent = resultado.residuos.toFixed(2);
    cards[3].textContent = resultado.energia.toFixed(2);

    // 3. Preenche os dados tangíveis
    document.getElementById("val-delivery").textContent = Math.round(
      resultado.entregasDelivery,
    ).toLocaleString("pt-BR");
    document.getElementById("val-digital").textContent = Math.round(
      resultado.transacoesDigitais,
    ).toLocaleString("pt-BR");
    document.getElementById("val-pet").textContent = Math.round(
      resultado.garrafasPet,
    ).toLocaleString("pt-BR");
    document.getElementById("val-banho").textContent = Math.round(
      resultado.banhosDeAgua,
    ).toLocaleString("pt-BR");

    // 4. Renderiza os Gráficos
    renderizarGraficos(resultado);

    // 5. Rola até os resultados
    secaoResultados.scrollIntoView({ behavior: "smooth" });

    // 6. Busca e exibe o histórico da última simulação com base na sessão do usuário
    if (emailEmpresa) {
      try {
        const hist = await fetch("/impacto/historico");

        if (hist.ok) {
          const ultima = await hist.json();
          const secaoHistorico = document.getElementById("secaoHistorico");

          if (secaoHistorico) {
            secaoHistorico.style.display = "block";

            document.getElementById("histCo2").textContent =
              ultima.emissaoCo2Kg?.toFixed(2) ?? "-";
            document.getElementById("histAgua").textContent =
              ultima.consumoAguaLitros?.toFixed(2) ?? "-";
            document.getElementById("histPlastico").textContent =
              ultima.residuosPlasticosKg?.toFixed(2) ?? "-";
            document.getElementById("histEnergia").textContent =
              ultima.energiaKwh?.toFixed(2) ?? "-";
            document.getElementById("histEntregas").textContent =
              ultima.dadosTangiveis?.entregasDelivery?.toFixed(0) ?? "-";
            document.getElementById("histGarrafas").textContent =
              ultima.dadosTangiveis?.garrafasPet?.toFixed(0) ?? "-";
            document.getElementById("histBanhos").textContent =
              ultima.dadosTangiveis?.banhosDeAgua?.toFixed(0) ?? "-";
            document.getElementById("histTransacoes").textContent =
              ultima.dadosTangiveis?.transacoesDigitais?.toFixed(0) ?? "-";
          }
        }
      } catch (historyError) {
        console.warn("Não foi possível carregar o histórico:", historyError);
      }
    }
  } catch (error) {
    console.error("Erro ao calcular:", error);
    alert("Erro ao processar cálculo: " + error.message);
  }
});

function renderizarGraficos(dados) {
  // Destruir instâncias anteriores para evitar bugs de hover/renderização
  if (barChartInstance) barChartInstance.destroy();
  if (pieChartInstance) pieChartInstance.destroy();

  const ctxBar = document.getElementById("barChart").getContext("2d");
  const ctxPie = document.getElementById("pieChart").getContext("2d");

  // Configurações globais para combinar com o tema Edenred
  Chart.defaults.font.family = "'Inter', sans-serif";
  Chart.defaults.color = "#64748b";

  // 1. Gráfico de Colunas (Físico vs Digital)
  barChartInstance = new Chart(ctxBar, {
    type: "bar",
    data: {
      labels: ["CO₂ (kg)", "Água (L)", "Plástico (kg)", "Energia (kWh)"],
      datasets: [
        {
          label: "Físico",
          data: [
            dados.carbonoFisico,
            dados.aguaFisica,
            dados.residuosFisicos,
            dados.energiaFisica,
          ],
          backgroundColor: "#e1000f", // Edenred Red
          borderRadius: 6,
          barPercentage: 0.6,
          categoryPercentage: 0.6,
        },
        {
          label: "Digital",
          data: [
            dados.carbonoDigital,
            dados.aguaDigital,
            dados.residuosDigital,
            dados.energiaDigital,
          ],
          backgroundColor: "#16a34a", // Green
          borderRadius: 6,
          barPercentage: 0.6,
          categoryPercentage: 0.6,
        },
      ],
    },
    options: {
      responsive: true,
      maintainAspectRatio: true,
      plugins: {
        legend: {
          position: "bottom",
          labels: {
            usePointStyle: true,
            padding: 20,
            font: { weight: "600", size: 12 },
          },
        },
        tooltip: {
          backgroundColor: "#1a2637",
          padding: 12,
          cornerRadius: 8,
          titleFont: { size: 13, weight: "700" },
          bodyFont: { size: 13 },
        },
      },
      scales: {
        y: {
          beginAtZero: true,
          grid: { display: true, color: "#f1f5f9" },
          ticks: { font: { size: 11 } },
        },
        x: {
          grid: { display: false },
          ticks: { font: { weight: "600", size: 11 } },
        },
      },
      animation: {
        duration: 2000,
        easing: "easeOutQuart",
      },
    },
  });

  // 2. Gráfico de Pizza (Média das Reduções)
  const mediaReducao =
    (dados.reducaoCarbono +
      dados.reducaoAgua +
      dados.reducaoEnergia +
      dados.reducaoResiduos) /
    4;
  const impactoRemanescente = 100 - mediaReducao;

  pieChartInstance = new Chart(ctxPie, {
    type: "doughnut", // Transformado em Doughnut para um visual mais moderno
    data: {
      labels: ["Redução Digital", "Impacto Físico"],
      datasets: [
        {
          data: [mediaReducao, impactoRemanescente],
          backgroundColor: ["#16a34a", "#e1000f"],
          borderWidth: 0,
          hoverOffset: 10,
        },
      ],
    },
    options: {
      responsive: true,
      maintainAspectRatio: true,
      cutout: "70%", // Espaço central do doughnut
      plugins: {
        legend: {
          position: "bottom",
          labels: {
            usePointStyle: true,
            padding: 20,
            font: { weight: "600", size: 12 },
          },
        },
        tooltip: {
          backgroundColor: "#1a2637",
          padding: 12,
          cornerRadius: 8,
          callbacks: {
            label: function (context) {
              return ` ${context.label}: ${context.raw.toFixed(1)}%`;
            },
          },
        },
      },
      animation: {
        animateScale: true,
        animateRotate: true,
        duration: 2000,
      },
    },
  });
}