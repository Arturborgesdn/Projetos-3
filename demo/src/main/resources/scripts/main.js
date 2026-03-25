document.addEventListener('DOMContentLoaded', () => {
    
    // 1. Lógica Visual dos Cartões de Material
    const radioCards = document.querySelectorAll('.radio-card');
    radioCards.forEach(card => {
        card.addEventListener('click', function() {
            // Remove active de todos
            radioCards.forEach(c => c.classList.remove('active'));
            // Adiciona no clicado
            this.classList.add('active');
            // Marca o input interno como checked
            this.querySelector('input').checked = true;
        });
    });

    // 2. Lógica Visual do Tipo de Entrega (Segmented Control)
    const segments = document.querySelectorAll('.segment');
    segments.forEach(seg => {
        seg.addEventListener('click', function() {
            segments.forEach(s => s.classList.remove('active'));
            this.classList.add('active');
            this.querySelector('input').checked = true;
        });
    });

    // 3. Atualizar o valor do Slider em tempo real
    const faturasRange = document.getElementById('faturasRange');
    const sliderValue = document.getElementById('sliderValue');
    
    faturasRange.addEventListener('input', function() {
        sliderValue.textContent = this.value + '%';
    });

    // 4. Capturar dados para enviar ao Spring Boot
    const btnCalcular = document.getElementById('btnCalcular');
    
    btnCalcular.addEventListener('click', () => {
        // Coleta os dados do formulário
        const formData = new FormData(document.getElementById('calculadoraForm'));
        const dadosSimulacao = Object.fromEntries(formData.entries());
        
        console.log("Dados prontos para o Spring Boot:", dadosSimulacao);
        alert("Dados capturados! Verifique o Console (F12). Próximo passo: Fetch API.");

        /* Exemplo de como o JS Integrator fará a requisição depois:
        
        fetch('http://localhost:8080/api/simulacao', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dadosSimulacao)
        })
        .then(response => response.json())
        .then(data => console.log('Resultado do cálculo:', data))
        .catch(error => console.error('Erro:', error));
        */
    });
});