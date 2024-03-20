
# E-Commerce System com Spring Boot e Microserviços

Este projeto é um sistema de e-commerce desenvolvido com o framework Spring Boot e arquitetura de microserviços. Ele permite aos usuários realizar operações como cadastro, login, gestão de itens, manipulação de carrinho de compras e simulação de pagamento.

## Features
O sistema é composto por várias APIs:

- 👤**Microsserviço Autenticação de Usuários**: Gerencia os dados do Usuário.
```shell
curl --location 'http://localhost:8080/api/v1/auth/signup' \
--header 'Content-Type: application/json' \
--data '{
    "username": "usuario",
    "password": "senha"
}'
```
- 🛒**Microsserviço Carrinho de compras**: Gerencia o carrinho de compras.
```shell
curl --location 'http://localhost:8082/carrinho-de-compras/carrinho?username=usuario'
```
- 📦**Microsserviço Gestão de Itens**: Gerencia itens.
```shell
curl --location 'http://localhost:8081/gestao-itens/itens' \
--header 'Content-Type: application/json' \
--data '{
    "nome": "Playstation 5",
    "preco": 4500
}'
```
- 💵**Microsserviço Pagamentos**: Gerencia os dados do quarto.
```shell
curl --location 'http://localhost:8083/pagamentos/formas-pagamento' 
```


Importante: para consultar todas as opções de APIs, favor acessar a collection POSTMAN disponibilizada:

- [FIAP - Booking.postman_collection.json](src%2Fmain%2Fresources%2FFIAP%20-%20Booking.postman_collection.json)

![img.png](img.png)


## Tecnologias e ferramentas utilizadas:

* [Spring initializr]( https://start.spring.io/): Ferramenta para criação de projetos Spring Boot
* [Spring Boot ]( https://spring.io/projects/spring-boot): Framework Web
* [Spring Boot Starter Web] Dependência para desenvolvimento Web
* [Spring Boot Starter Validation] Dependência para validação de formulários
* [Maven]( https://mvnrepository.com/): Gerenciador de dependências do projeto
* [JPA Repository] Gerenciador de interação entre camadas do projeto
* [Lombock] Dependência para requisições HTTP
* [Postman](https://www.postman.com/): Ferramenta para teste de requisições HTTP
* [Open Api - Swagger] Dependência para gerar interface amigável para testes
* [H2] Banco de dados em memória
* [Spring Security] Dependência para segurança da aplicação

## Arquitetura
- Login e Registro de Usuário: Serviço responsável pela autenticação e registro de usuários.
- Gestão de Itens: Serviço para adicionar, atualizar e remover itens.
- Carrinho de Compras: Serviço para gerenciar o carrinho de compras dos usuários.
- Pagamento (Simulação): Serviço para simular o processo de pagamento.

## Desafios


Optamos por adotar uma abordagem monolítica para o desenvolvimento de nossa aplicação, visando otimizar o tempo e concentrar esforços em outras áreas do projeto.

Decidimos alcançar uma cobertura de testes de 50% para refletir o conhecimento adquirido até o momento, permitindo-nos avançar para a próxima fase do projeto.

Durante o desenvolvimento, surgiram desafios relacionados à interpretação das regras de negócio, incluindo questões sobre os limites do escopo do projeto:

A escolha do banco de dados: Optamos por utilizar o H2, uma solução leve e eficiente para nossas necessidades.
Testes e simulação de requisições HTTP: Para testar nossas APIs, decidimos utilizar ferramentas como o Swagger UI e o Postman, facilitando a criação e execução de testes automatizados.
Esses foram alguns dos aspectos considerados pela equipe durante a fase inicial do projeto. A partir dessas definições, realizamos reuniões de alinhamento para planejar e coordenar nossas ações, garantindo uma execução eficiente e coerente com nossos objetivos.

## Consultando a Documentação da API
Acessa o arquivo swagger da aplicação pelo link: [api-docs.json](src%2Fmain%2Fresources%2Fapi-docs.json)
Basta colar o documento no site: [Swagger Editor](https://editor.swagger.io/)

Você pode também pode consultar a documentação da API em run-time. Para isso, siga os passos abaixo:

Certifique-se de que a aplicação está em execução.
Abra um navegador web e navegue até http://localhost:8080/booking/swagger-ui.html.
Isso abrirá a interface do Swagger, onde você poderá explorar todos os endpoints disponíveis, seus parâmetros e respostas.

## Método de utilização da(s) API(s):
## Arquivo do Postman
Para facilitar o teste e a utilização da API, disponibilizamos um arquivo do Postman com todas as requisições pré-configuradas. Você pode encontrar o arquivo em:

```shell
./fiap-hackathon-booking/src/main/resources/FIAP - Booking.postman_collection.json
```

Basta importar esse arquivo para o seu ambiente do Postman e começar a usar as requisições imediatamente.

## Diagrama de tabelas:
![Imagem do WhatsApp de 2024-03-13 à(s) 15 09 19_4d8cc485](https://github.com/otavioprado/fiap-hackathon-booking/assets/133544024/ad57fc12-7514-4d5c-871e-f9534d2fc710)

## Coverage dos testes:
![img_1.png](img_1.png)

## Diagrama de arquitetura:
![diagrama_arquitetura_sistema](https://github.com/otavioprado/fiap-hackathon-booking/assets/133544024/671b356a-ffe0-4fa0-9970-8fac0b7fee70)
