
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

## Diagrama de arquitetura:
![diagrama_arquitetura_sistema](https://private-user-images.githubusercontent.com/57017906/316351221-d6a62333-5870-4714-92f8-22c4dc0cb5c4.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTEzMjQ2NzMsIm5iZiI6MTcxMTMyNDM3MywicGF0aCI6Ii81NzAxNzkwNi8zMTYzNTEyMjEtZDZhNjIzMzMtNTg3MC00NzE0LTkyZjgtMjJjNGRjMGNiNWM0LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDAzMjQlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwMzI0VDIzNTI1M1omWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTk3MzJjNzhjZjQ3NzQxYWI2MTBlM2Y3YWNiOGIyYTNhMDgxMWEyMjNlMWJjY2Y3MzlhZWM0MzJhZjI2ZmUyZGImWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.DCv7OMo7yIQ_CH-0hk5w_q5glsxM-AiRsAPZ4DLUaK0)




## Desafios

Optamos por adotar uma abordagem com microsserviços para o desenvolvimento de nossa aplicação, visando otimizar o tempo e concentrar esforços em outras áreas do projeto.

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
![]()

## Coverage dos testes:
![]()


