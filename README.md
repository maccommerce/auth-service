# auth-service
Este repositório contém o código fonte do serviço de login e autenticação dos usuários.

### Variáveis de ambiente

Para executar esta aplicação é necessário informar as seguintes variáveis de ambiente:

- `APPLICATION_PORT` -> porta onde o servidor da aplicação irá executar
- `JDBC_DATABASE_URL` -> string de conexão com o banco de dados
- `JDBC_DATABASE_USERNAME` -> nome de usuário para se autenticar no banco de dados
- `JDBC_DATABASE_PASSWORD` -> senha para se autenticar no banco de dados

ex: java -jar auth-service.jar --JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/postgres --JDBC_DATABASE_USERNAME=postgres --JDBC_DATABASE_PASSWORD=postgres --APPLICATION_PORT=8000
