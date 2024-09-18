**Java Developer Admin/User**

Este é um projeto desenvolvido em Java usando Spring Boot e Maven, com integração à API Twilio para envio de mensagens SMS e validação de senhas. O sistema permite o gerenciamento de dois tipos de usuários: Usuário e Administrador. Cada um tem funcionalidades específicas, como alterar dados pessoais, exibir informações e receber mensagens SMS de confirmação.

**Funcionalidades**
- Login para Usuário e Administrador
- Validação de números de telefone
- Envio de SMS via Twilio
- Validação de senha
- Alteração de dados do usuário e administrador
- Exibição das informações cadastradas

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

**Antes de iniciar, certifique-se de ter as seguintes ferramentas instaladas:**

- Java 11 ou superior
- Maven
- Spring Boot
- Conta no Twilio (para envio de SMS)
- MySQL (ou outro banco de dados que desejar integrar)

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

- Compile e execute o projeto:

*mvn spring-boot:run*

**Uso**

- Ao executar o programa, você será solicitado a se identificar como Usuário ou Administrador.
- Dependendo da escolha, siga as instruções para login e uso das funcionalidades específicas de cada tipo de usuário.
- Caso opte por se registrar como Usuário, será necessário validar o número de telefone e criar uma senha válida (seguindo as regras de complexidade).
- Como Administrador, é possível alterar informações dos usuários cadastrados e realizar modificações nas suas próprias credenciais.


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
