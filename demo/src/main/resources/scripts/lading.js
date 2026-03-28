document.addEventListener('DOMContentLoaded', () => {
    const btnScroll = document.getElementById('btnScrollCalculator');

    btnScroll.addEventListener('click', () => {
        // Se a sua calculadora estiver na mesma página (ex: num container com id="calculadoraForm")
        // ele vai rolar até lá. Caso contrário, você pode redirecionar para outra página.
        console.log("Iniciando a jornada ESG do cliente...");
        
        // Exemplo de scroll suave:
        window.scrollBy({ 
            top: 600, // Ajuste esse valor dependendo da altura da tela
            behavior: 'smooth' 
        });
        
        // Se fosse para outra página:
        // window.location.href = "calculadora.html";
    });
});