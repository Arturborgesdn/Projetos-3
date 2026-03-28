document.getElementById("btnCalcular").addEventListener("click", async () => {

    const form = document.getElementById("calculadoraForm");
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

    const response = await fetch("http://localhost:8080/impacto", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    });

    const resultado = await response.text();
    const valor = parseFloat(resultado).toFixed(2);

    // pega o primeiro card (CO2)
    document.querySelector(".card-number").textContent = valor;

    
});