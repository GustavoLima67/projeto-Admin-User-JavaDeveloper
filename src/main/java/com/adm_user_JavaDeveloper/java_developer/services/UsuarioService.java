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
import com.adm_user_JavaDeveloper.java_developer.services.enums.Response;
import com.twilio.rest.api.v2010.account.Message;

import com.twilio.type.PhoneNumber;

public class UsuarioService {
    
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
    
        procesarNome(entidade);  
    
        return entidade;
    }

    public static void pegarToString() {
        System.out.println();
        System.out.println(user.toString());
        System.out.println();
    }

    public static String lerOpcoes() throws ExececaoPadrao {
        System.out.print("O que deseja mudar: (nome / telefone / senha / dataNascimento) ");
        String mudar = sc.nextLine();
        String entidade = procesarEntidade();
    
        switch (mudar.toLowerCase()) {
            case "nome":
                procesarNome(entidade);
                break;
            case "telefone": 
                procesarTelefone(entidade);
                break;
            case "senha":
                procesarSenha(entidade);
                break;
            case "dataNascimento":
                procesarData(entidade);
                break;
            default:
                throw new ExececaoPadrao("Erro ao chamar as funções");
                
        }

        return mudar;
    }
    
    public static String procesarNome(String entidade) {
        System.out.print("Entre com o nome desejado: ");
        String name = sc.next();
     
        try {
            if(entidade.toLowerCase().equals("user")) {
                user.setName(name);
            }
            else if(entidade.toLowerCase().equals("adm")) {
                adm.setName(name);
            } 
            st.setString(1, name);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static String procesarTelefone(String entidade) throws ExececaoPadrao{
      
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
            if (entidade.equals("user")) {
                user.setTelegone(phoneNumber);
            } else if (entidade.equals("adm")) {
               System.out.println("Adm não contem um numero para contato");
            }
            st.setString(2, phoneNumber);
            st.executeUpdate();
        }catch (SQLException e) {
            throw new ExececaoPadrao("Erro no cadastro do numero de telefone, tente novamente");
        }
        return phoneNumber;
    }

    public static String procesarSenha(String entidade) {
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
            if (entidade.equals("user")) {
                user.setSenha(userInputsenha);
            } else if (entidade.equals("adm")) {
                adm.setSenha(userInputsenha);
            }
            st.setString(3, userInputsenha);
            st.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return userInputsenha;
    }

    public static LocalDate procesarData(String entidade) {
        boolean dataValida = false;
        LocalDate dataNascimento = null;

        if(dataNascimento == null) {
            do {
                System.out.print("Entre com sua data de nascimento: (dd/MM/yyyy) ");
                String dataNasciInput = sc.nextLine();
                try {
                    dataNascimento = LocalDate.parse(dataNasciInput, formatter);
                    dataValida = true;
    
                    if (entidade.equals("user")) {
                        user.setDataNascimento(dataNascimento);
                    } else if (entidade.equals("adm")) {
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
            st = conn.prepareStatement("INSERT INTO usuarios (Nome, Senha, Telefone, Data_Nascimento)" + " VALUES" + " (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, name);
            st.setString(2, userInputsenha);
            st.setString(3, Senha.hashSenha(userInputsenha));
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

    public static void Inform(Usuarios user) {
        System.out.println();
        System.out.println("Exibir Usuário: ");
        System.out.println();
        System.out.println(user.toString());
        System.out.println();
        System.out.println("Obrigador por se cadastrar " + user.getName() + "!. :)");
		exibir(user);
	}
    
    public static void exibir(Usuarios user) {
        try {
			char response;
			do {
				System.out.println("Bem Vindo "+user.getName()+"!");
				System.out.println("FUNCIONALIDADES DO USUARIO. ");
				System.out.print("Quer mudar suas informações: (s/n) ");
				response = sc.next().charAt(0);
				
				Response userResponse = Response.fromChar(response);

				switch (userResponse) {
					case YES:
						ProgramJavaDeveloper.lerFuncionalidades();
						break;
					case NO: 
						UsuarioService.Inform(user);
						break;
					default:
						throw new ExececaoPadrao("Erro na sintexe!, digite da forma descrita (s/n)");
				}
			} while(response != 'n');
			
			
		} catch (Exception e) {
			System.err.println("Erro de exibição" + e.getMessage());
		}
    }
}
