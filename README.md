# ProjectTreeMDI

ProjectTreeMDI é uma aplicação de exemplo que implementa uma arquitetura de interface gráfica com visualização em árvore e suporte a Múltiplos Documentos (MDI). O projeto segue o padrão **Model-View-Presenter (MVP) - Passive View**, promovendo uma separação clara entre a lógica de apresentação e a interface do usuário.

## Funcionalidades

- **Visualização em Árvore**: Gerenciamento de projetos em uma estrutura hierárquica.
- **MDI (Multiple Document Interface)**: Abertura de múltiplas janelas de projeto simultaneamente.
- **Padrão MVP Passive View**: Garantia de separação entre a camada de apresentação e a interface do usuário.
- **Mock de Dados**: Simulação de dados para demonstração das funcionalidades.
- **Gerenciamento de Janelas**: Opções para organizar janelas lado a lado e restaurar estados anteriores usando o padrão de projeto Memento.

## Tecnologias Utilizadas

- **Java Swing**: Para criação da interface gráfica.
- **FlatLaf**: Biblioteca para temas modernos no Swing.
- **JFreeChart**: Geração de gráficos e visualizações de dados.

## Estrutura do Projeto

- **Model**: Representação dos dados do projeto.
- **View**: Interfaces gráficas que interagem com o usuário.
- **Presenter**: Camada de lógica que conecta o Model à View.
- **Service**: Serviços auxiliares para manipulação de dados e negócio.
- **Command**: Implementação do padrão Command para execução de ações.

## Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/seuusuario/ProjectTreeMDI.git
   ```
2. Navegue até o diretório do projeto:
   ```bash
   cd ProjectTreeMDI
   ```
3. Compile o projeto:
   ```bash
   mvn clean install
   ```
4. Execute a aplicação:
   ```bash
   java -jar target/ProjectTreeMDI.jar
   ```

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests.

1. Fork este repositório.
2. Crie uma branch para sua feature (`git checkout -b feature/sua-feature`).
3. Commit suas alterações (`git commit -m 'Adicionando nova funcionalidade'`).
4. Push para a branch (`git push origin feature/sua-feature`).
5. Abra um Pull Request.

## Licença

Este projeto é licenciado sob a Licença MIT

