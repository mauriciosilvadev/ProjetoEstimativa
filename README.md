# ProjectTreeMDI

Projeto com arquitetura de tela utilizando **JTree** para navegação hierárquica e **JDesktopPane** para múltiplas janelas internas (MDI). Estruturado com o padrão **MVP (Model-View-Presenter)** no estilo **Passive View**, o projeto foca em uma abordagem modular e escalável para o desenvolvimento de aplicações Java.

## 🛠️ Tecnologias Utilizadas
- **Java** (JDK 17)
- **Swing** (para a interface gráfica)
- **FlatLaf** (tema moderno para o Swing)
- **JFreeChart** (para gráficos)

## 📂 Estrutura do Projeto
O código está organizado de forma clara e modular, seguindo o padrão MVP:

```
├── br.projeto
│   ├── command       # Comandos que executam ações específicas (padrão Command)
│   ├── model         # Modelos de dados (Projetos, Funcionalidades, etc.)
│   ├── presenter     # Lógica de apresentação (controla a interação View-Model)
│   ├── repository    # Repositório de dados (mockado para simulação)
│   ├── service       # Serviços auxiliares para cálculos e manipulação de dados
│   └── view          # Componentes de interface gráfica (Swing)
```

## 🔍 Funcionalidades
- **Navegação Hierárquica**: Visualização de projetos em árvore com **JTree**.
- **MDI (Interface Multi-documento)**: Abertura de múltiplas janelas internas para detalhamento de projetos.
- **Estimativas de Projeto**: Cálculo de prazos e custos com base nos perfis e funcionalidades selecionadas.
- **Gráficos Dinâmicos**: Exibição de gráficos de distribuição de custos e tipos de projeto com **JFreeChart**.
- **Persistência de Layout**: Salva e restaura o layout das janelas abertas (padrão Memento).

## 🎯 Padrões de Projeto Aplicados
- **MVP (Passive View)**: A lógica da aplicação está separada da interface, facilitando manutenção e testes.
- **Command**: Encapsulamento de ações em comandos reutilizáveis.
- **Memento**: Persistência do estado das janelas para restaurar o layout.
- **Observer**: Atualização automática das janelas ao modificar dados do repositório.

## 🚀 Como Executar o Projeto
1. Clone o repositório:
   ```bash
   git clone https://github.com/claytonfraga/projecttreeMDI
   ```

2. Navegue até o diretório do projeto e compile:
   ```bash
   cd projecttreeMDI
   mvn clean install
   ```

3. Execute o projeto:
   ```bash
   java -jar target/ProjetoEstimativaMDI.jar
   ```

## 📝 Observações
- Os dados são **mockados** para demonstração.
- O projeto é um exemplo educacional de arquitetura e padrões de projeto em aplicações Java Swing.

## 📜 Licença
Distribuído sob a licença MIT. Consulte `LICENSE` para mais informações.

---

💻 **Desenvolvido por [Clayton Fraga](https://github.com/claytonfraga)** 🚀
