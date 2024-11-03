package com.adm_user_JavaDeveloper.java_developer.services;

import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.adm_user_JavaDeveloper.java_developer.model.Administrador;
import com.adm_user_JavaDeveloper.java_developer.ProgramJavaDeveloper;
import com.adm_user_JavaDeveloper.java_developer.controller.AdmController;
import com.adm_user_JavaDeveloper.java_developer.controller.UsuarioController;
import com.adm_user_JavaDeveloper.java_developer.controller.exceptions.ExececaoPadrao;
import com.adm_user_JavaDeveloper.java_developer.controller.security.Senha;
import com.adm_user_JavaDeveloper.java_developer.controller.validators.Validation;
import com.adm_user_JavaDeveloper.java_developer.db.DB;
import com.adm_user_JavaDeveloper.java_developer.model.Usuarios;
import com.adm_user_JavaDeveloper.java_developer.repositories.interfaceAcaoRepository;
import com.adm_user_JavaDeveloper.java_developer.services.enums.Response;
import com.adm_user_JavaDeveloper.java_developer.services.enums.ResponseUserAdm;


@Service
public class MetodosService {
    
    private static Usuarios user = new Usuarios();
    private static Administrador adm = new Administrador();
    private static Scanner sc = new Scanner(System.in);

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    private static Connection conn = null;
	private static PreparedStatement st = null;
	private static ResultSet rs = null;

    public static String procesarEntidade() {
        System.out.println("Qual a entidade que deseja ser atribuída?: (adm / user)");
        String entidade = sc.nextLine(); 
        return entidade;
    }

    public static void pegarToString(String entidade) {
        if (entidade.toLowerCase().equals("adm")) {
            System.out.println();
            System.out.println(adm.toString());
            System.out.println();
        }else if (entidade.toLowerCase().equals("user")) {
            System.out.println();
            System.out.println(user.toString());
            System.out.println();
        }
        
    }
    
    public static String procesarNome() {
        String name;
        String nomeFormatado;
        String[] partes;
        do {
            System.out.print("Entre com seu nome completo: ");
            name = sc.nextLine();
    
            partes = name.trim().split("\\s+");

            nomeFormatado = formatarNome(name);
            
        }while(partes.length < 2);
        
        return nomeFormatado;
    }

    public static String formatarNome(String name) {
        String[] partes = name.trim().split("\\s+");
        
        if (partes.length < 2) {
            throw new IllegalArgumentException("Por favor, entre com um nome completo contendo nome e sobrenome");
        }

        String primeiroNome = partes[0];
        String ultimoSobrenome = partes[partes.length -1];
        String inicialSobrenome = partes[1].toUpperCase().substring(0, 1) + ".";
        
        return primeiroNome + " " + inicialSobrenome + " " + ultimoSobrenome;
    }

    public static String procesarSenha() {
        boolean senhaValida;
        String userInputsenha;
        do {
            System.out.print("Digite uma senha válida: ");
            userInputsenha = sc.nextLine();
            
            senhaValida = Validation.validarSenha(userInputsenha);

            if (!senhaValida) {
                Validation.senhaInvalida();
            }
        } while (!senhaValida);
        System.out.println("Senha valida! acesso concedido!");
        System.out.println();
        return userInputsenha;
    }

    public static String procesarEmail(String entidade) throws ExececaoPadrao {
        String email;
        boolean validEmail;
        do {
            System.out.print("Entre com seu email: ");
            email = sc.nextLine();

            validEmail = Validation.validarEmail(email);

            if (!validEmail) {
                System.out.println("Email inválido. Tente novamente.");
            }
        } while (!validEmail);

        if (entidade.toLowerCase().equals("adm")){
            EmailService emailService = new EmailService();
            emailService.enviarMensagemEmail(adm.getEmail(), 
                        "Cadastro realizado com sucesso!", 
                        "Olá, seu cadastro no projeto 'java_developer-GL67' foi realizado com sucesso, \n Obrigado por se cadastrar! \n Att. Gustavo L. Souza ");
        } else if (entidade.toLowerCase().equals("user")) {
            EmailService emailService = new EmailService();
            emailService.enviarMensagemEmail(user.getEmail(), 
                        "Cadastro realizado com sucesso!", 
                        "Olá, seu cadastro no projeto 'java_developer-GL67' foi realizado com sucesso, \n Obrigado por se cadastrar! \n Att. Gustavo L. Souza ");
        }
       
        return email;
    }

    public static String pegarCargo() {
        System.out.print("Entre com seu cargo administrador(a): ");
        String cargo = sc.nextLine();

        return cargo;
    }

    public static LocalDate procesarData() {
        boolean dataValida = false;
        LocalDate dataNascimento = null;

        if(dataNascimento == null) {
            do {
                System.out.print("Entre com sua data de nascimento: (dd/MM/yyyy) ");
                String dataNasciInput = sc.nextLine();
               
                dataNascimento = LocalDate.parse(dataNasciInput, formatter);
                dataValida = true;

            } while(!dataValida);
        } 
        return dataNascimento;
    }

    public static void exibir(Usuarios user, Administrador adm, String entidade) {
     
        if (entidade.toLowerCase().equals("adm")) {
            System.out.println("Novo moderador "+adm.getNome()+" cadastrado com sucesso!");
            System.out.println();
            System.out.println(adm.toString());
            System.out.println();
        }else if (entidade.toLowerCase().equals("user")) {
            System.out.println("Usuário "+user.getNome()+" cadastrado com sucesso!");
            System.out.println();
            System.out.println(user.toString());
            System.out.println();
        }
    }

    public static void exibirQuantUsuarios(interfaceAcaoRepository usuarioAcao, interfaceAcaoRepository administradorAcao) throws ExececaoPadrao {
        System.out.print("Gostaria de visualizar a quantidade de usuários cadastrados?: (s / n) ");
        char response = sc.next().toLowerCase().charAt(0);

        Response userResponse = Response.fromChar(response);

        switch (userResponse) {
            case YES:
                usuarioAcao.executar();
                break;
            case NO:
                administradorAcao.executar();
                break;
            default:
                throw new ExececaoPadrao("Erro na sintaxe, digite da forma descrita (s/n). ");
        }
    }

    public static void procesarUsuario(UsuarioController userController) {
        List<Usuarios> usuarios = userController.pegarTodosUsuarios();

        try {
            if(usuarios.isEmpty()) {
                System.out.print("Usuario não existe:\nCrie um novo usuario para prosseguir:\n");
                ProgramJavaDeveloper.lerUsuarios();
            }
            else {
                System.out.println("Usuários cadastrados:");
                usuarios.forEach(user -> System.out.println("ID: " + user.getId() + ", Nome: " + user.getNome()));
            }
        } catch (Exception e) {
           System.out.println("Erro ao Listar os usuários" +e.getMessage());
        }
    }

    public static void procesarAdm(AdmController admController) {
        List<Administrador> admConList = admController.pegarTodosAdm();

        try {
            if(admConList.isEmpty()) {
                System.out.print("Moderadores não existe:\nCrie um novo moderador para prosseguir:\n");
                ProgramJavaDeveloper.lerAdministrador();
            }
            else {
                System.out.println("Moderadores cadastrados:");
                admConList.forEach(adm -> System.out.println("ID: " + adm.getId() + ", Nome: " + adm.getNome()));
            }
        } catch (Exception e) {
           System.out.println("Erro ao Listar os usuários" +e.getMessage());
        }
    }

    public static Connection executeDbConnectionAdm(String name, String passwordAdm, String email, String cargo) {
        try { 
            conn = DB.getConnection();

            st = conn.prepareStatement("INSERT INTO administrador (nome, senha, email, cargo) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, name);
            st.setString(2, passwordAdm);
            st.setString(3, email);
            st.setString(4, cargo);

            int linhasAfetadas = st.executeUpdate();

            if(linhasAfetadas > 0) {
                rs = st.getGeneratedKeys();
                while (rs.next()) {
                    int idAdm = rs.getInt(1);
                    System.out.println("Administrador(a) inserido com sucesso! Id: " + idAdm);
                }
            } else {
                System.out.println("Erro ao inserir administrador(a)");
            }


        } catch (SQLException e) {
            System.out.println("Erro na entrada de valores SQL" + e.getMessage());
            e.printStackTrace();
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
            DB.closeConnection();
        }
        return conn;
    }

    public static Connection executeDbConnection(String name, String userInputsenha, String phoneNumber, LocalDate dataNascimento) {
        conn = DB.getConnection(); 
        try {
            st = conn.prepareStatement("INSERT INTO usuarios (nome, senha, email, data_nascimento)" + " VALUES" + " (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, name);
            st.setString(2, Senha.hashSenha(userInputsenha));
            st.setString(3, phoneNumber);
            st.setDate(4, java.sql.Date.valueOf(dataNascimento)); 

            int linhasAfetadas = st.executeUpdate();
        
            if (linhasAfetadas > 0) {
                rs = st.getGeneratedKeys();
                while(rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Usuario inserido com sucesso! Id: " + id);
                }
            } else {
                System.out.println("Sem nenhuma linha afetada");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
            DB.closeConnection();
        }
        
        return conn; 
    }

    public static void voltarInicio(interfaceAcaoRepository usuarioAcao, interfaceAcaoRepository administradorAcao) throws ExececaoPadrao {
		System.out.println("Cadastro finalizado, gostaria de cadastrar um novo usuário?, ou cadastrar um novo moderador? (u = usuario, a = administrador)");
        char response = sc.next().toLowerCase().charAt(0);

        System.out.println("Se quiser finalizar o programa. Escreva (exit)");
        String exit = sc.nextLine();
        if (exit.equals("exit")) {
            System.out.println("Projeto de cadastro finalizado, obrigado por usa-lo");
            
            DB.closeConnection();
            DB.closeResultSet(rs);
            DB.closeStatement(st);
            sc.close();
        }

        ResponseUserAdm responseUserAdm = ResponseUserAdm.fromChar(response);

        switch (responseUserAdm) {
            case USER:
                usuarioAcao.executar();
                break;
            case ADM:
                administradorAcao.executar();
                break;
            default:
                throw new ExececaoPadrao("Erro na sintaxe, digite da forma descrita (s/n). ");
        }
	}
}
