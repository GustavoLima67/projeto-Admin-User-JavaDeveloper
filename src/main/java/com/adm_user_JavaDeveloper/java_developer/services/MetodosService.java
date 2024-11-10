package com.adm_user_JavaDeveloper.java_developer.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import com.adm_user_JavaDeveloper.java_developer.ProgramJavaDeveloper;
import com.adm_user_JavaDeveloper.java_developer.controller.AdmController;
import com.adm_user_JavaDeveloper.java_developer.controller.UsuarioController;
import com.adm_user_JavaDeveloper.java_developer.controller.exceptions.ExececaoPadrao;
import com.adm_user_JavaDeveloper.java_developer.controller.security.Senha;
import com.adm_user_JavaDeveloper.java_developer.controller.validators.Validation;
import com.adm_user_JavaDeveloper.java_developer.db.DB;
import com.adm_user_JavaDeveloper.java_developer.model.Administrador;
import com.adm_user_JavaDeveloper.java_developer.model.Usuarios;
import com.adm_user_JavaDeveloper.java_developer.repositories.interfaceAcaoRepository;
import com.adm_user_JavaDeveloper.java_developer.services.enums.Response;
import com.adm_user_JavaDeveloper.java_developer.services.enums.ResponseUserAdm;

@Service
public class MetodosService {
    
    private Usuarios user = new Usuarios();
    private Administrador adm = new Administrador();
    private Scanner sc = new Scanner(System.in);

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    private Connection conn = null;
	private PreparedStatement st = null;
	private ResultSet rs = null;

    @Autowired
    private UsuarioController usuarioController;
    @Autowired
    private AdmController admController;

  
    public String procesarEntidade() {
        System.out.println("Qual a entidade que deseja ser atribuída?: (adm / user)");
        String entidade = sc.nextLine(); 
        return entidade;
    }

    public void pegarToString(String entidade) {
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
    
    public String procesarNome() {
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

    public String formatarNome(String name) {
        String[] partes = name.trim().split("\\s+");
        
        if (partes.length < 2) {
            throw new IllegalArgumentException("Por favor, entre com um nome completo contendo nome e sobrenome");
        }

        String primeiroNome = partes[0];
        String ultimoSobrenome = partes[partes.length -1];
        String inicialSobrenome = partes[1].toUpperCase().substring(0, 1) + ".";
        
        return primeiroNome + " " + inicialSobrenome + " " + ultimoSobrenome;
    }

    public String procesarSenha() {
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
    
    public String procesarEmail() {
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

        try {
           
        } catch(MailException e) {
            e.printStackTrace();
        }
        return email;
    }

    public String pegarCargo() {
        System.out.print("Entre com seu cargo administrador(a): ");
        String cargo = sc.nextLine();

        return cargo;
    }

    public LocalDate procesarData() {
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

    public void exibir(Usuarios user, Administrador adm, String entidade) {
     
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

    public void exibirQuantUsuarios(interfaceAcaoRepository usuarioAcao, interfaceAcaoRepository administradorAcao) throws ExececaoPadrao {
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

    public void procesarUsuario(String entidade, UsuarioController userController, AdmController admController) {
        if (entidade.toLowerCase().equals("adm")) {
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
        else if (entidade.toLowerCase().equals("adm")) {
            List<Administrador> administrador = admController.pegarTodosAdm();

            try {
                if (admnistrador.isEmpty()) {
                    System.out.print("Administradores não existe. \n Crie um novo adminstrador e prossiga. \n");
                    ProgramJavaDeveloper.lerAdministradores();
                } else {
                    administradores.forEach(adm -> System.out.print("ID: " + adm.getId() + "Nome: " + adm.getNome()));
                }
            } catch () {

            }
        }

    }


    public Connection executeDbConnectionAdm(String name, String passwordAdm, String email, String cargo) {
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

    public Connection executeDbConnection(String name, String userInputsenha, String phoneNumber, LocalDate dataNascimento) {
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

    public Connection deletarUserPorId(Integer userId) {
        conn = DB.getConnection();

        try {
            st = conn.prepareStatement("DELETE FROM usuarios WHERE id = ? ");

            st.setInt(1, userId);

            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.print("Usuário deletado com sucesso. Id:" + userId);
            } else {
                System.out.println("Nenhuma linha foi afetada. Verifique se o id existe");
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

    public void deletarOuVisualizarUsers(String entidade) {
        System.out.print("Deseja deletar ou visualizar os usuarios? (deletar/visualizar): ");
        String oqueDeseja = sc.nextLine();

        if(entidade.toLowerCase().equals("user")) {
            if(oqueDeseja.toLowerCase().equals("deletar")) {
                System.out.print("Entre com o id de usuario para excluir do banco: ");
                Integer userId = sc.nextInt();
                deletarUserPorId(userId);
            } 
            else if(oqueDeseja.toLowerCase().equals("visualizar")) {
                procesarUsuario(usuarioController);
            }
            else {
                System.out.print("Erro de entrada, digite como está no parentese: (deletar/visualizar)");
            }
        } else if (entidade.toLowerCase().equals("adm")) {
            if(oqueDeseja.toLowerCase().equals("deletar")) {
                System.out.print("Entre com o id de usuario para excluir do banco: ");
                Integer admId = sc.nextInt();
                deletarUserPorId(admId);
            } 
            else if(oqueDeseja.toLowerCase().equals("visualizar")) {
                procesarAdm(admController);
            }
            else {
                System.out.print("Erro de entrada, digite como está no parentese: (deletar/visualizar)");
            }
        }
    }

    public void voltarInicio(interfaceAcaoRepository usuarioAcao, interfaceAcaoRepository administradorAcao) throws ExececaoPadrao {
		System.out.println("Cadastro finalizado, gostaria de cadastrar um novo usuário?, ou cadastrar um novo moderador? (u = usuario, a = administrador)");
        char response = sc.next().toLowerCase().charAt(0);

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
