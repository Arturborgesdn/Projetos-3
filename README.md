# Projeto – Calculadora de Impacto Ambiental para Pagamentos Digitais
## Entrega 1 - Concluída

- [x] Histórias de usuário do Projeto — [ver documento](https://docs.google.com/document/d/1acG23aitg1odm9QbZYD1o-JmEy9F-HNU1X26R3d0vic/edit?usp=sharing)
- [x] Screencast da Prototipação de baixa fidelidade — [ver vídeo](https://youtu.be/zewOdGQVTis)

---

## Entrega 2 - Concluída

- [x] Issue/bug tracker atualizado: <img width="917" height="545" alt="image" src="https://github.com/user-attachments/assets/6cf1b4fc-f01a-41dd-b2d1-cc781499746e" />
- [x] Screencast das 2 primeiras histórias de usuário — [ver vídeo](https://youtu.be/cbvwdfVgjTY)

---

## Entrega 3 - Concluída

- [x] Issue/bug tracker atualizado: <img width="1361" height="616" alt="image" src="https://github.com/user-attachments/assets/957b0511-5a96-480d-a25b-cdcf7ff3325e" />
- [x] Screencast — 3 a 4 histórias de usuário e evolução do projeto — [ver vídeo](https://youtu.be/vpwV2chFlmI)

---

## Entrega 4 -



---

## SR1 - 

- [x] Apresentação Status Report — [ver documento](https://github.com/Arturborgesdn/Projetos-3/blob/main/STATUS%20REPORT.pdf)

---

## SR2 - 



---

## Tecnologias Utilizadas

### Back-end
- **Java 17**
- **Spring Boot 4.0.4**
- **Spring Data JPA** — mapeamento objeto-relacional
- **Spring Web MVC** — endpoints REST
- **Thymeleaf** — template engine para renderização HTML
- **Hibernate** — ORM para persistência
- **MySQL 8.0** — banco de dados relacional
- **HikariCP** — pool de conexões

### Front-end
- **HTML5 / CSS3 / JavaScript ES6+**
- **Chart.js** — gráficos interativos
- **Phosphor Icons** — biblioteca de ícones
- **Google Fonts** — tipografia (Inter, DM Sans)

### Ferramentas
- **Maven** — gerenciamento de dependências
- **Spring DevTools** — hot reload em desenvolvimento
- **Git / GitHub** — controle de versão
- **MySQL Workbench** — administração do banco de dados

---

## Instruções de Execução

### Pré-requisitos

- Java 17 ou superior instalado
- MySQL 8.0 instalado e rodando
- Git instalado

### 1. Clonar o repositório

```bash
git clone https://github.com/Arturborgesdn/Projetos-3.git
cd Projetos-3
```

### 2. Configurar o banco de dados

Abra o MySQL Workbench ou terminal MySQL e execute:

```sql
CREATE DATABASE edenred_sustentavel;
```

### 3. Configurar as credenciais

Abra o arquivo `demo/src/main/resources/application.properties` e atualize com suas credenciais:
```properties
spring.application.name=demo
spring.datasource.url=jdbc:mysql://localhost:3306/edenred_sustentavel
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_AQUI
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 4. Executar o projeto

```bash
cd demo
./mvnw spring-boot:run
```

### 5. Acessar a aplicação

Após o log exibir `Started DemoApplication`, acesse:
http://localhost:8080

### 6. Verificar as tabelas no banco

```sql
USE edenred_sustentavel;
SHOW TABLES;
```
Devem aparecer:
- `empresas`
- `dados_simulacao`
- `dados_tangiveis`

---

## Endpoints da API

| Método |             Rota             |            Descrição               |
|--------|------------------------------|------------------------------------|
| `POST` | `/auth/cadastro`             | Cadastrar nova empresa             |
| `POST` | `/auth/login`                | Autenticar empresa                 |
| `POST` | `/impacto`                   | Calcular impacto ambiental         |
| `GET`  | `/impacto/historico/{email}` | Buscar última simulação da empresa |

### Exemplo — Cadastro

```json
{
  "nome": "Empresa Teste",
  "email": "empresa@teste.com",
  "senha": "123456"
}
```

### Exemplo — Login

```json
{
  "email": "empresa@teste.com",
  "senha": "123456"
}
```

---

## Contexto

A **Edenred** é uma multinacional francesa presente em mais de **45 países**, líder global no mercado de **benefícios corporativos e soluções de mobilidade**. A empresa atua fortemente em iniciativas de sustentabilidade por meio do pilar estratégico **Ideal Planet**, que tem como meta atingir **Net Zero Carbon até 2050**.

Como parte dessa estratégia, a Edenred busca **digitalizar parte de seus produtos**, reduzindo a necessidade de cartões físicos e, consequentemente, diminuindo a **pegada de carbono**, o uso de **materiais virgens** e a geração de **resíduos**.

Além disso, a transformação digital acompanha uma tendência crescente do mercado: o **trabalho remoto e as soluções digitais**, nas quais a agilidade tecnológica passa a ser mais eficiente do que a logística de distribuição de cartões físicos.

Apesar dessas vantagens, a empresa enfrenta desafios na **adoção dessas soluções digitais por parte de seus clientes**, principalmente devido a barreiras culturais e operacionais.

---

## Problemas Identificados

* **Resistência cultural à digitalização:** muitos clientes ainda preferem o modelo tradicional baseado em cartões físicos.
* **Dificuldade de adaptação ao modelo digital:** questionamentos sobre a viabilidade de operar apenas com pagamentos digitais.
* **Falta de ferramentas de apoio ao time comercial:** ausência de recursos que traduzam dados técnicos de sustentabilidade em argumentos claros e persuasivos.
* **Justificativa ambiental pouco tangível:** sem dados concretos e visualmente compreensíveis, os benefícios ambientais da digitalização tornam-se abstratos para os clientes.

---

## Impactos e Consequências

1. **Perda de clientes:** resistência à mudança pode gerar insatisfação e evasão de clientes atuais.
2. **Queda nas vendas:** dificuldade em comercializar soluções exclusivamente digitais.
3. **Resistência à mudança organizacional:** clientes apresentam dificuldades em compreender e aceitar o novo modelo.
4. **Baixa clareza nos benefícios ambientais:** sem métricas concretas, torna-se difícil demonstrar o valor sustentável da migração para o digital.

---

## Objetivo Geral

Desenvolver uma **calculadora digital de impacto ambiental** capaz de comparar as emissões e resíduos gerados por **transações financeiras realizadas com cartões físicos** e **pagamentos digitais**, permitindo demonstrar de forma clara os **benefícios ambientais da digitalização das soluções da Edenred**.

---

## Objetivos Específicos

1. **Mensurar o impacto ambiental da produção de cartões físicos**, considerando:
   - uso de plástico
   - metais utilizados no chip
   - consumo de energia e água
   - emissões de carbono durante o processo produtivo

2. **Calcular o impacto logístico da distribuição dos cartões**, incluindo:
   - emissões provenientes do transporte
   - embalagens utilizadas
   - materiais de envio

3. **Analisar o impacto ambiental do pós-consumo**, considerando o descarte de cartões e dos materiais utilizados na entrega.

4. **Comparar o consumo energético entre transações físicas e digitais**, levando em conta diferentes cenários de infraestrutura energética.

5. **Apresentar os resultados de forma clara e comparativa**, traduzindo dados ambientais em equivalências compreensíveis, como:
   - redução de **CO₂ emitido**
   - quantidade de **plástico evitado**
   - quantidade de **papel economizado**
   - equivalência em **árvores preservadas**

6. **Criar uma ferramenta visual e acessível**, que possa ser utilizada tanto por **clientes quanto pela equipe comercial**.

7. **Apoiar a estratégia de sustentabilidade da Edenred**, fortalecendo argumentos que incentivem clientes a adotarem **soluções digitais**.

---

## Solução Proposta

A solução consiste no desenvolvimento de uma **calculadora digital de impacto ambiental**, capaz de transformar dados complexos sobre sustentabilidade em **informações claras, visuais e facilmente compreensíveis**.

A ferramenta permitirá simular diferentes cenários de uso entre **cartões físicos e pagamentos digitais**, demonstrando o impacto ambiental associado a cada modelo. Com base nos dados inseridos, o sistema apresentará **comparações diretas e equivalências ambientais**, facilitando a compreensão dos benefícios da digitalização.

Além de apoiar clientes na tomada de decisão, a calculadora funcionará como um **instrumento estratégico para o time comercial da Edenred**, permitindo transformar métricas ambientais em **argumentos de venda concretos**, fortalecendo a adoção de soluções digitais e contribuindo para as metas de sustentabilidade da empresa.
