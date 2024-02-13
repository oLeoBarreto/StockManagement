# Arquiteturais e a estrutura do projeto

## Requisitos para rodar o projeto

### Setup de ambiente:

- [Java 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Docker 24.0+](https://docs.docker.com/get-docker/)

### Como rodar na minha maquina:

- Clone o projeto `git clone https://github.com/oLeoBarreto/StockManagement.git`
- Rode o comando `mvn verify` no root do projeto
- Agora já está pronto para rodar de maneira local usando as configuração desejadas
## Estrutura do projeto

- `src/main/java`: Onde se localiza o projeto com a classe principal para rodar o projeto.
- `src/main/resources`: Onde se localiza as configurações do projeto e suas variáveis de ambiente.
- `src/test/java`: Onde se localiza os testes unitários do projeto. Estando estruturada assim como a core da aplicação se encontra.
- `src/test/resources`: Onde se localiza as configurações e variáveis de ambiente para os testes.
- `src/main/java/infra`: Contém arquivos referente a infraestrutura da aplicação, como configurações, DTOs, Handlers e Exceptions.
- `src/main/java/infra/database/repository`: Todos os repositories dos domains se encontraram nessa pasta.
- `src/main/java/domains`: Trata-se da camada mais baixa da aplicação onde objetos/classes de domain se encontram.
- `src/main/java/useCases`: Camada intermediaria da aplicação, onde para cada domain se encontraram divididos por pastas, a interface com os useCases e um service que implementa os useCases.
- `src/main/java/controller`: Camada que os usuários irá interagir, os controllers iram implementar os services de cada domain criado rotas para interação do usuários com a funçõess da aplicação. Também se encontra dividida em sub-pastas com cada domain, tendo a interface de endpoints que estaram disponíveis no controller e classe de controller que implementa os endpoints.