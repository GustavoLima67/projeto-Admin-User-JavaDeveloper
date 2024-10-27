package com.adm_user_JavaDeveloper.java_developer.services;

import java.util.Scanner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.adm_user_JavaDeveloper.java_developer.model.Administrador;
import com.adm_user_JavaDeveloper.java_developer.ProgramJavaDeveloper;
import com.adm_user_JavaDeveloper.java_developer.controller.exceptions.ExececaoPadrao;
import com.adm_user_JavaDeveloper.java_developer.controller.security.Senha;
import com.adm_user_JavaDeveloper.java_developer.controller.validators.Validation;
import com.adm_user_JavaDeveloper.java_developer.db.DB;
import com.adm_user_JavaDeveloper.java_developer.model.Usuarios;
import com.adm_user_JavaDeveloper.java_developer.repositories.interfaceAcaoRepository;
import com.adm_user_JavaDeveloper.java_developer.services.enums.Response;
import com.twilio.rest.api.v2010.account.Message;

import com.twilio.type.PhoneNumber;

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

        if (procesarEntidade().toLowerCase().equals("adm")) {
            System.out.println();
            System.out.println(adm.toString());
            System.out.println();
        }else if (procesarEntidade().toLowerCase().equals("user")) {
            System.out.println();
            System.out.println(user.toString());
            System.out.println();
        }
        
    }

    public static String mudarPropiedades() throws ExececaoPadrao {
        System.out.print("O que deseja mudar: (nome / telefone / senha / dataNascimento) ");
        String mudar = sc.nextLine();
    
        switch (mudar.toLowerCase()) {
            case "nome":
                procesarNome();
                break;
            case "telefone": 
                procesarTelefone();
                break;
            case "senha":
                procesarSenha();
                break;
            case "dataNascimento":
                procesarData();
                break;
            default:
                throw new ExececaoPadrao("Erro ao chamar as funções");
                
        }

        return mudar;
    }
    
    public static String procesarNome() {
        System.out.print("Entre com o nome desejado: ");
        String name = sc.next();
     
        try {
            if(procesarEntidade().toLowerCase().equals("user")) {
                user.setName(name);
            }
            else if(procesarEntidade().toLowerCase().equals("adm")) {
                adm.setName(name);
            } 
            st.setString(1, name);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static String procesarSenha() {
        boolean senhaValida;
        String userInputsenha;
        sc.nextLine();
        do {
            System.out.print("Digite sua senha: ");
            userInputsenha = sc.nextLine();
            
            senhaValida = Validation.validatePassword(userInputsenha);

            if (!senhaValida) {
                Validation.senhaInvalida();
            }
        } while (!senhaValida);
        System.out.println("Senha valida! acesso concedido!");

        try {
            if (procesarEntidade().equals("user")) {
                user.setSenha(userInputsenha);
            } else if (procesarEntidade().equals("adm")) {
                adm.setSenha(userInputsenha);
            }
            st.setString(2, userInputsenha);
            st.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return userInputsenha;
    }

    public static String procesarTelefone() throws ExececaoPadrao {
        String phoneNumber;
        boolean validPhone;
        do {
            System.out.print("Entre com seu numero de Telefone: ");
            phoneNumber = sc.nextLine();
            
            validPhone = Validation.isValidPhoneNumber(phoneNumber);
            
            if (!validPhone) {
                System.out.println("Numero de telefone invalido. Tente novamente.");
            }
        } while(!validPhone);
            
        Message message = Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber("+12513021245"), "Numero de Telefone validado no 'Projeto JavaDeveloper' com sucesso!").create();
        System.out.println("Mensagem Enviada com Sucesso!" + message.getSid());

        try {
            if (procesarEntidade().equals("user")) {
                user.setTelefone(phoneNumber);
            } else if (procesarEntidade().equals("adm")) {
               System.out.println("Adm não contem um numero para contato");
            }
            st.setString(3, phoneNumber);
            st.executeUpdate();
        }catch (SQLException e) {
            throw new ExececaoPadrao("Erro no cadastro do numero de telefone, tente novamente");
        }
        return phoneNumber;
    }

    public static LocalDate procesarData() {
        boolean dataValida = false;
        LocalDate dataNascimento = null;

        if(dataNascimento == null) {
            do {
                System.out.print("Entre com sua data de nascimento: (dd/MM/yyyy) ");
                String dataNasciInput = sc.nextLine();
                try {
                    dataNascimento = LocalDate.parse(dataNasciInput, formatter);
                    dataValida = true;
    
                    if (procesarEntidade().equals("user")) {
                        user.setDataNascimento(dataNascimento);
                    } else if (procesarEntidade().equals("adm")) {
                        adm.setDataNascimento(dataNascimento);
                    }
                    st.setDate(4, java.sql.Date.valueOf(dataNascimento));
                    st.executeUpdate();
                } catch(DateTimeParseException |  SQLException e) {
                    System.out.println("Data inválida, tente novamente" );
                    e.printStackTrace();
                }
            } while(!dataValida);
        } 
        return dataNascimento;
    }

    public static Connection executeDbConnection(String name, String userInputsenha, String phoneNumber, LocalDate dataNascimento) {
        conn = DB.getConnection(); 
        try {
            st = conn.prepareStatement("INSERT INTO usuarios (nome, senha, telefone, data_nascimento)" + " VALUES" + " (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
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
    
    public static void exibir(Usuarios user) {
        
        String entidade = procesarEntidade();

        pegarToString(entidade);

        char response;
        try {
			do {
				System.out.println("Bem Vindo "+user.getNome()+"!");
				System.out.println("FUNCIONALIDADES DO "+entidade.toUpperCase()+". ");
				System.out.print("Quer mudar suas informações: (s/n) ");
				response = sc.next().charAt(0);
				
				Response userResponse = Response.fromChar(response);
             
                try {
                    switch (userResponse) {
                        case YES:
                            ProgramJavaDeveloper.lerFuncionalidadesDeUsuarios();
                            break;
                        case NO: 
                            System.out.println("Obrigador por se cadastrar " + user.getNome() + "!. :)");
                            System.exit(response);
                            break;
                        default:
                            throw new ExececaoPadrao("Erro na sintexe!, digite da forma descrita (s/n)");
                    } 
                } catch (Exception e) {
                    System.out.println("Erro em exibir o Adm");
                }
            
			} while(response != 'n');
			
			
		} catch (Exception e) {
			System.err.println("Erro de exibição" + e.getMessage());
		}
    }
 
    public static void exibirModer(Administrador adm) {
    
        System.out.println();
        System.out.println("Exibir Administrador: ");
        System.out.println();
        System.out.println(adm.toString());
        System.out.println();
        String no = "Obrigado pelo dia administrador " +adm.getName() + "!. :)";
        System.out.println(no);

        try {
            char response;
            do {
                System.out.println("Bem Vindo "+adm.getName()+"!");
                System.out.println("FUNCIONALIDADES DO ADMINSTRADOR. ");
                System.out.print("Quer mudar suas informações: (y/n) ");
                sc.nextLine();
                response = sc.next().charAt(0);
                
                Response userResponse = Response.fromChar(response);

                switch (userResponse) {
                    case YES:
                        ProgramJavaDeveloper.lerFuncionalidadesAdm();
                        break;
                    case NO:
                        System.out.println(no);
                    default:
                        throw new ExececaoPadrao("Erro na sintexe, digite da forma descrita (s / n): ");
                }

            } while(response != 'n');
        } catch (Exception e) {
            System.err.println("Erro de exibição"+ e.getMessage());
        }
    }

    public static void getUserOuAdm(interfaceAcaoRepository userAcao, interfaceAcaoRepository admAcao) throws ExececaoPadrao {
        System.out.println("O que deseja fazer?: (Escreva exatamente como esta abaixo) ");
        System.out.println("'User' | 'Adm'");
        System.out.print("Escreva: ");
        String response = sc.nextLine();


        if (response.equals("User")) {
            procesarIqualUsuario();
        }
        if (response.equals("Adm")) {
            procesarIgualAdm();
        }
        
    }

    public static void procesarIqualUsuario() {
        try {
            if(user == null) {
                System.out.print("Usuario não existe:\nCrie um novo usuario para prosseguir:\n");
                ProgramJavaDeveloper.getUser();
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void procesarIgualAdm() {
        System.out.print("O que deseja mudar: (nome / senha / dataNascimento) ");
        String mudar = sc.nextLine();

        switch (mudar.toLowerCase()) {
            case "nome":
                procesarNome();  
                break;
            case "senha":
                procesarSenha(); 
                break;
            case "datanascimento":
                procesarData();  
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
    }

    public static Connection procesarConnectionSQL(String upName, String upPassw, LocalDate procesDate){
        try {
            conn = DB.getConnection();

            st = conn.prepareStatement("UPDATE administrador SET nome = ?, senha = ?,  data_nascimento = ? WHERE id = (SELECT MAX(id) FROM administrador) ");
            st.setString(1, upName);
            st.setString(2, Senha.hashSenha(upPassw));
            st.setDate(3, java.sql.Date.valueOf(procesDate));

            int linhasAtualizadas = st.executeUpdate();
            if (linhasAtualizadas > 0) {
                System.out.println("Tabela atualizada com sucesso");
            }
            else {
                System.out.println("Nenhuma linha afetada");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeResultSet(rs);
            DB.closeResultSet(rs);
            DB.closeConnection();
        }
        return conn;
    }

    public static Connection executeDbConnectionAdm(String name, String passwordAdm, LocalDate dataNascimento) {
        try { 
            conn = DB.getConnection();

            st = conn.prepareStatement("INSERT INTO administrador (Nome, Senha, DataNascimento) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, name);
            st.setString(2, passwordAdm);
            st.setDate(3, java.sql.Date.valueOf(dataNascimento));

            int linhasAfetadas = st.executeUpdate();

            if(linhasAfetadas > 0) {
                rs = st.getGeneratedKeys();
                while (rs.next()) {
                    int idAdm = rs.getInt(1);
                    System.out.println("Administrador inserido com sucesso! Id: " + idAdm);
                }
            } else {
                System.out.println("Erro ao inserir administrador");
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

}
