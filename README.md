# Planejador de Viagens

## Descrição

O Planejador de Viagens é uma aplicação backend desenvolvida em Java utilizando o framework Spring Boot. Esta aplicação permite aos usuários cadastrar suas viagens e convidar outras pessoas para participarem delas, facilitando o planejamento e organização de viagens em grupo.

## Funcionalidades

- Criar Viagem
- Obter Detalhes da Viagem
- Atualizar Viagem
- Confirmar Viagem
- Convidar Participante
- Listar Participantes
- Registrar Atividade
- Listar Atividades
- Registrar Link
- Listar Links

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- H2 Database (ou outra base de dados de sua escolha)
- Maven

## Pré-requisitos

- Java 11 ou superior
- Maven
- Git

## Como Executar o Projeto

1. Clone o repositório:
   ```sh
   git clone https://github.com/seuusuario/planejador-de-viagens.git
   
2. Navegue até o diretório do projeto:
   ```sh
   cd planejador-de-viagens
   
3. Compile e execute o projeto utilizando o Maven:
   ```sh
   mvn spring-boot:run

4. A aplicação estará disponível em `http://localhost:8080`.

## Endpoints
#### Viagens
- GET /trips: Lista todas as viagens cadastradas.
- POST /trips: Cadastra uma nova viagem.
- GET /trips/{id}: Retorna os detalhes de uma viagem específica.
- PUT /viagens/{id}: Atualiza os detalhes de uma viagem existente.
- GET /viagens/{id}/confirm: Confirma uma viagem e envia e-mails de confirmação para os participantes.

#### Participantes
- POST /viagens/{id}/invite: Envia um convite para participar da viagem.
- GET /viagens/{id}/participants: Retorna a lista de todos os participantes de uma viagem.

#### Atividades
- POST /viagens/{id}/activities: Registra uma atividade para a viagem.
- GET /viagens/{id}/activities: Retorna a lista de todas as atividades de uma viagem.

#### Links
- POST /viagens/{id}/links: Registra um link relacionado à viagem.
- GET /viagens/{id}/links: Retorna a lista de todos os links relacionados à viagem.


