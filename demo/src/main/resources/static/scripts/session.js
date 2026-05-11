document.addEventListener('DOMContentLoaded', () => {
    const navLinks = document.getElementById('navLinks');
    const emailSalvo = sessionStorage.getItem("emailEmpresa");

    if (emailSalvo && navLinks) {
        // Remove botões de Login e Cadastro se logado
        const loginBtn = navLinks.querySelector('.nav-btn-login');
        const cadastroBtn = navLinks.querySelector('.nav-btn-cadastro');
        
        if (loginBtn) loginBtn.remove();
        if (cadastroBtn) cadastroBtn.remove();

        // Limpa links genéricos para dar espaço aos links do usuário
        // (Opcional, dependendo do design, mas vamos adicionar os novos links)
        
        const calcLink = document.createElement('a');
        calcLink.href = "/calculadora";
        calcLink.textContent = "Calculadora";
        navLinks.appendChild(calcLink);

        const histLink = document.createElement('a');
        histLink.href = "/historico";
        histLink.textContent = "Histórico";
        navLinks.appendChild(histLink);

        // Adiciona botão de Logout
        const logoutBtn = document.createElement('a');
        logoutBtn.href = "#";
        logoutBtn.className = "nav-btn-login"; // Usa a mesma classe para manter o estilo
        logoutBtn.textContent = "Sair";
        logoutBtn.style.borderColor = "var(--edenred-red)";
        logoutBtn.style.color = "var(--edenred-red)";

        logoutBtn.addEventListener('click', async (e) => {
            e.preventDefault();
            try {
                await fetch("/auth/logout", { method: "POST" });
            } catch (error) {
                console.warn("Sessão já expirada no servidor ou erro de conexão.");
            }
            sessionStorage.removeItem("emailEmpresa");
            sessionStorage.removeItem("simulacaoFeita");
            window.location.href = "/login";
        });

        navLinks.appendChild(logoutBtn);
    }
});
