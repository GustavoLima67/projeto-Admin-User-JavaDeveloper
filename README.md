# Projeto JavaDeveloper

Este é um projeto Java que implementa um sistema de gerenciamento de usuários e administradores. A aplicação permite que usuários se cadastrem e que administradores gerenciem as informações dos usuários. O projeto utiliza o Spring Boot para a construção da API e o MySQL como banco de dados.

## Funcionalidades

- **Cadastro de Usuários**: Usuários podem se cadastrar fornecendo nome, telefone, senha e data de nascimento.
- **Login de Usuários e Administradores**: Usuários e administradores podem efetuar login no sistema.
- **Envio de SMS**: O sistema envia mensagens SMS para o número de telefone fornecido durante o cadastro.
- **Gerenciamento de Informações**: Usuários podem atualizar suas informações pessoais, como nome, telefone e senha.


Principais funcionalidades por tipo de usuário:

**Usuário:**
- Login com validação de número de telefone e senha.
- Edição de nome, número de telefone e senha.
- Envio de mensagem SMS ao validar o número de telefone.

**Administrador:**
- Login com ID único e senha validada.
- Edição de nome e senha.
- Acesso às informações de usuários cadastrados.
- Requisitos

## Tecnologias Utilizadas

- Java
- Spring Boot
- MySQL
- JDBC
- Twilio API para envio de SMS

## Configuração do Ambiente

### Pré-requisitos

- JDK 11 ou superior
- MySQL
- Maven

### Configuração do Banco de Dados

1. Crie um banco de dados chamado `projetoadmjdbc`.
2. Execute o script SQL para criar as tabelas necessárias:
   ```sql
   CREATE TABLE usuario (
       id INT AUTO_INCREMENT PRIMARY KEY,
       Nome VARCHAR(255) NOT NULL,
       Senha VARCHAR(255) NOT NULL,
       Telefone VARCHAR(20) NOT NULL,
       DataNascimento DATE NOT NULL
   );


**Instalação**
*Clone este repositório:*

- git clone https://github.com/seu-usuario/seu-repositorio.git
- cd java_developer_admin_user

**Configure seu arquivo application.properties (em src/main/resources/) com os dados do seu banco de dados MySQL e sua conta Twilio:**

````
spring.datasource.url=jdbc:mysql://localhost:3306/seu_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
````

````
twilio.account.sid=SEU_ACCOUNT_SID
twilio.auth.token=SEU_AUTH_TOKEN
twilio.phone.number=SEU_PHONE_NUMBER
````

# Configuração do Twilio

- Crie uma conta no Twilio e obtenha suas credenciais (ACCOUNT_SID e AUTH_TOKEN).
- Substitua os valores das constantes ACCOUNT_SID e AUTH_TOKEN no código pelo que você obteve do Twilio.

# Arquivo de Propriedades
- Crie um arquivo chamado application.properties na pasta src/main/resources e adicione as seguintes propriedades:
````
spring.application.name=java_developer

# Configurações do MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/seu_banco_de_Dados
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configurações do JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
  
````

*mvn spring-boot:run*

**Uso**

- Ao executar o programa, você será solicitado a se identificar como Usuário ou Administrador.
- Dependendo da escolha, siga as instruções para login e uso das funcionalidades específicas de cada tipo de usuário.
- Caso opte por se registrar como Usuário, será necessário validar o número de telefone e criar uma senha válida (seguindo as regras de complexidade).
- Como Administrador, é possível alterar informações dos usuários cadastrados e realizar modificações nas suas próprias credenciais.

![Imagem exemplo Método](images/loginUser.png)


**Exemplo de Login como Usuário:**
````
Você é um Usuário?: (y/n) y
Entre com seu nome de Usuário: João
Entre com seu número de Telefone: +5511987654321
Mensagem enviada com sucesso!
Entre com uma senha válida: senha@Valida123
Senha válida! Acesso concedido!
Validação de Senha
A senha deve seguir as seguintes regras:

No mínimo 8 caracteres.
Pelo menos uma letra maiúscula.
Pelo menos uma letra minúscula.
Pelo menos um número.
Pelo menos um caractere especial (ex: @, #, !).
Integração com Twilio
````
- Para envio de mensagens SMS, o projeto usa a API Twilio. Certifique-se de configurar suas credenciais no arquivo application.properties com ACCOUNT_SID, AUTH_TOKEN e o número de telefone Twilio fornecido pela plataforma.

*Estrutura do Projeto*

````
src/
│
├── main/
│   ├── java/com/adm_user_JavaDeveloper/java_developer/
│   │   ├── ProgramJavaDeveloper.java          # Classe principal
│   │   ├── entities/
│   │   │   ├── Usuario.java                   # Classe do usuário
│   │   │   ├── Administrador.java             # Classe do administrador
│   │   ├── exceptions/
│   │   │   ├── ExececaoPadrao.java            # Classe de exceções
│   │   ├── validators/
│   │   │   ├── PasswordValidators.java        # Validador de senha
│   └── resources/
│       └── application.properties             # Configuração de banco e Twilio
└── test/
    └── java/com/adm_user_JavaDeveloper/tests/  # Testes unitários (em desenvolvimento)
````

**Contribuições**
- Sinta-se à vontade para abrir um PR ou Issues para relatar bugs ou sugerir melhorias.
