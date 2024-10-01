package com.adm_user_JavaDeveloper.java_developer;

import java.io.IOError;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adm_user_JavaDeveloper.java_developer.authenticator.TwilioAuthentication;
import com.adm_user_JavaDeveloper.java_developer.db.DB;
import com.adm_user_JavaDeveloper.java_developer.entities.Administrador;
import com.adm_user_JavaDeveloper.java_developer.entities.Usuario;
import com.adm_user_JavaDeveloper.java_developer.enums.Response;
import com.adm_user_JavaDeveloper.java_developer.exceptions.ExececaoPadrao;
import com.adm_user_JavaDeveloper.java_developer.validators.PasswordValidators;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@SpringBootApplication
public class ProgramJavaDeveloper {
	
	public static Scanner sc = new Scanner(System.in);
	public static Administrador adm = new Administrador();
	public static Usuario user = new Usuario();
	
	public static Connection conn = null;
	public static PreparedStatement st = null;
	public static ResultSet rs = null;
	
	public static final String accountSid = TwilioAuthentication.getAccountSid();
    public static final String accountAuthToken = TwilioAuthentication.getAuthToken();

	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


	public static void main(String[] args) throws Exception{
		try {
			admOuUsuario();	
		} catch (Exception e) {
			throw new ExececaoPadrao("Error na chamada da função!");
		}
		
	}
	
	public static void admOuUsuario() throws ExececaoPadrao { 
		
		try {
			
			System.out.println("Bem-vindo");

			//Sistema.logarNoSistema(Sistema::loginUser, Sistema::loginAdm);

		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		
	}
	
	public static void loginUser() {
		try {
			String phoneNumber = "";
			boolean validPhone;
			System.out.print("Entre com seu nome de Usuário: ");
			String name = sc.next();
			
			sc.nextLine();
			try {
				do {
					
					System.out.print("Entre com seu numero de Telefone: ");
					phoneNumber = sc.nextLine();
					
					validPhone = isValidPhoneNumber(phoneNumber);
					
					if (!validPhone) {
						System.out.println("Numero de telefone invalido. Tente novamente.");
					}

				} while(!validPhone);
				
				try {

					sendSms(phoneNumber);
					System.out.println("Menssagem enviada com sucesso!");
				
				} catch(ApiException e) {
					System.out.println("Error na Api de envio SMS " + e.getMessage());
					e.printStackTrace();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			boolean senhaValida;
			String userInputsenha;
			
			do {
				System.out.print("Entre com uma senha valida: ");
	            userInputsenha = sc.nextLine();
	            
	            senhaValida = PasswordValidators.validatePassword(userInputsenha);

	            if (!senhaValida) {
	                senhaInvalida();
	            }
			}  while (!senhaValida);
			System.out.println("Senha valida! acesso concedido!");
			

			
            LocalDate dataNascimento = null;
            boolean dataValida = false;
            
            do {
            	System.out.print("Entre com sua data de nascimento: (dd/MM/yyyy) ");
            	String dataNasciInput = sc.nextLine();
            	try {
            		dataNascimento = LocalDate.parse(dataNasciInput, formatter);
            		dataValida = true;
            	} catch(DateTimeParseException e) {
            		System.out.println("Data inválida, tente novamente" );
            		e.printStackTrace();
            	}
            	
            } while(!dataValida);
            	
            try {
            	
            	conn = DB.getConnection();
            	
            	st = conn.prepareStatement("INSERT INTO usuario (Nome, Senha, Telefone, DataNascimento)" + " VALUES" + " (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            	st.setString(1, name);
            	st.setString(2, userInputsenha);
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
            	 	
            }catch(SQLException e) {
            	e.printStackTrace();
            } finally {
            	DB.closeStatement(st);
            	DB.closeResultSet(rs);
            	DB.closeConnection();
            }
            
            user = new Usuario(name, phoneNumber, userInputsenha, dataNascimento);
			System.out.println();
			User();
		
		} catch(IOError e) {
			e.printStackTrace();
		}
	}

	public static void UserFunc(){
		try {
			System.out.println();
			System.out.println(user.toString());
			System.out.println();
			
			System.out.print("O que deseja mudar: (nome / telefone / senha / dataNascimento) ");
			String mudar = sc.next();
			
			if (mudar.equals("nome")) {
				System.out.print("Entre com o nome desejado: ");
				String name = sc.next();
				
				st.setString(1, name);
				user.setName(name);
			}
			else if (mudar.equals("telefone")) {
				boolean validPhone;
				String phoneNumber;
				
				try {
					do {
						
						System.out.print("Entre com seu numero de Telefone: ");
						phoneNumber = sc.nextLine();
						
						validPhone = isValidPhoneNumber(phoneNumber);
						
						if (!validPhone) {
							System.out.println("Numero de telefone invalido. Tente novamente.");
						}

					} while(!validPhone);
					
					Message message = Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber("+12513021245"), "Numero de Telefone validado no 'Projeto JavaDeveloper' com sucesso!").create();
					System.out.println("Mensagem Enviada com Sucesso!" + message.getSid());
					
				}catch (Exception e) {
					throw new ExececaoPadrao("Erro no cadastro do numero de telefone, tente novamente");
				}
				
				user.setPhoneNumber(phoneNumber);
				st.setString(2, phoneNumber);
			}
			else if (mudar.equals("senha")) { 
				
				boolean senhaValida;
				String UserInputsenha;
				sc.nextLine();
				do {
		            System.out.print("Digite sua senha: ");
		            UserInputsenha = sc.nextLine();
		            
		            senhaValida = PasswordValidators.validatePassword(UserInputsenha);

		            if (!senhaValida) {
		                senhaInvalida();
		            }

	        } while (!senhaValida);

				user.setSenha(UserInputsenha);
				st.setString(3, UserInputsenha);
				System.out.println("Senha valida! acesso concedido!");
			}

			else if (mudar.equals("dataNascimento")) {
				LocalDate dataNascimento = null;
            	boolean dataValida = false;
            
            do {
            	System.out.print("Entre com sua data de nascimento: (dd/MM/yyyy) ");
            	String dataNasciInput = sc.nextLine();
            	try {
            		dataNascimento = LocalDate.parse(dataNasciInput, formatter);
            		dataValida = true;

					user.setDate(dataNascimento);
					st.setDate(4, java.sql.Date.valueOf(dataNascimento));

            	} catch(DateTimeParseException e) {
            		System.out.println("Data inválida, tente novamente" );
            		e.printStackTrace();
            	}
            	
            } while(!dataValida);
			}

		}
		catch (Exception e) {
			e.getMessage();
		}
	}
	
	public static void loginAdm() throws ExececaoPadrao{
		try {
			System.out.print("Entre com seu nome de login administrador: ");
		    String name = sc.next();
		
		    boolean senhaValida;
			String UserInputsenha;
			
			sc.nextLine();
			do {
				System.out.print("Entre com uma senha valida: ");
	            UserInputsenha = sc.nextLine();
	            
	            senhaValida = PasswordValidators.validatePassword(UserInputsenha);

	            if (!senhaValida) {
	                senhaInvalida();
	            }
			}  while (!senhaValida);

			LocalDate dataNascimento = null;
			boolean dataValida = false;
			do {
				System.out.print("Entre com sua data de nascimento: ");
				String dataNascimInput = sc.nextLine();

				try {
					dataNascimento = LocalDate.parse(dataNascimInput, formatter);
					dataValida = true;
				} catch(DateTimeParseException e) {
					System.out.println("Senha inválida! Tente novamente");
					e.printStackTrace();
				}

			} while (!dataValida);

			try { 
				conn = DB.getConnection();

				st = conn.prepareStatement("INSERT INTO administrador (Nome, Senha, DataNascimento) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				st.setString(1, name);
				st.setString(2, UserInputsenha);
				st.setDate(3, java.sql.Date.valueOf(dataNascimento));

				int linhasAfetadas = st.executeUpdate();

				if(linhasAfetadas > 0) {
					rs = st.getGeneratedKeys();
					while (rs.next()) {
						int idAdm = rs.getInt(1);
						System.out.println("Administrador inserido com sucesso! Id: " + idAdm);
            		}
            	} else {
            		System.out.println("Sem nenhuma linha afetada");
            	}


			} catch (SQLException e) {
				System.out.println("Erro na entrada de valores SQL" + e.getMessage());
				e.printStackTrace();
			} finally {
				DB.closeStatement(st);
				DB.closeResultSet(rs);
				DB.closeConnection();
			}
        	
            adm = new Administrador(name, UserInputsenha, dataNascimento);
            
			System.out.println();
			
			admFunc();
			
		} catch (Exception e) {
			throw new ExececaoPadrao("Erro ao fazer login como Administrador!", e);
		}
	}
	
	public static void admFunc() throws ExececaoPadrao{
		
		try {
			System.out.println();
			System.out.println(adm.toString());
			System.out.println();
			
			System.out.print("O que deseja fazer?: (Escreva exatamente como esta abaixo) ");
			System.out.println();
			System.out.print("User | Adm");
			String func = sc.nextLine();
			
			if (func.equals("User")) {
				
				try {
					if(user.getName() == null && user.getPhoneNumber() == null && user.getSenha() == null) {
						System.out.print("Usuario não existe:\nCrie um novo usuario para prosseguir:\n");
						loginUser();
					}
					else {
						UserFunc();
					}
				} catch (Exception e) {
					e.getMessage();
				}
			}
			
			if (func.equals("Adm")) {
				System.out.println(adm.toString());
				System.out.print("O que deseja mudar: (nome / senha) ");
				String mudar = sc.next();
				
				if (mudar.equals("nome")) {
					System.out.print("Entre com o nome desejado: ");
					String name = sc.next();

					st.setString(1, name);
					adm.setName(name);

					System.out.println(adm.toString());
				}
				if (mudar.equals("senha")) {
					boolean senhaValida;
					String UserInputsenha;
					do {
			            System.out.print("Digite sua senha: ");
			            UserInputsenha = sc.nextLine();
			            
			            senhaValida = PasswordValidators.validatePassword(UserInputsenha);
	
			            if (!senhaValida) {
			                senhaInvalida();
			            }

		        } while (!senhaValida);
					System.out.println("Senha valida! acesso concedido!");
					
					st.setString(2, UserInputsenha);
					adm.setSenha(UserInputsenha);
					System.out.println(adm.toString());
				}

				else if(mudar.equals("dataNascimento")) {
					LocalDate dataNascimento = null;
					boolean dataValida = false;
					do {
						System.out.print("Entre com uma data de nascimento diferente: ");
						String dataNascimInput = sc.nextLine();

						try {
							dataNascimento = LocalDate.parse(dataNascimInput, formatter);
							dataValida = true;

							adm.setDataNascimento(dataNascimento);
							st.setDate(3, java.sql.Date.valueOf(dataNascimInput));

						} catch(DateTimeParseException e) {
							System.out.println("Senha inválida! Tente novamente");
							e.printStackTrace();
						}
					} while (!dataValida);
			}
				Adm();
			} else { 
				System.out.println("Resposta inválida, tente novamente");
				admFunc();
			}

		}catch (Exception e) {
			throw new ExececaoPadrao("Erro!, corrija suas credenciais");
		} 
	}
	
	public static void Adm() {
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
						admFunc();
						break;
					case NO:
						InformAdm();
					default:
						throw new ExececaoPadrao("Erro na sintexe, digite da forma descrita (s / n): ");
				}

			} while(response != 'n');
			
			
		} catch (Exception e) {
			System.err.println("Erro de exibição"+ e.getMessage());
		}
		
	}
	
	public static void User() {
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
						UserFunc();
						break;
					case NO: 
						InformUser();
						break;
					default:
						throw new ExececaoPadrao("Erro na sintexe!, digite da forma descrita (s/n)");
				}
			} while(response != 'n');
			
			
		} catch (Exception e) {
			System.err.println("Erro de exibição" + e.getMessage());
		}
		
	}
	
	public static boolean isValidPhoneNumber(String phoneNumber) {
	    String regex = "^\\+?[1-9]{1}[0-9]{1,3}[1-9]{1}[0-9]{1,4}[0-9]{4,5}[0-9]{4}$";
	    
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(phoneNumber);
		
		return matcher.matches();
	}
	
	public static void sendSms(String phoneNumber) throws ExececaoPadrao {
	 Twilio.init(accountSid, accountAuthToken);
	 try {

	   	 Message message = Message.creator(
	                new PhoneNumber(phoneNumber), 
	                new PhoneNumber("+12513021245"),    
	                "Seu número foi cadastrado no 'Projeto JavaDeveloper Adm / User' com sucesso!")
	            .create();

	        System.out.println("Mensagem enviada com sucesso: " + message.getSid());
 
	 	} catch (Exception e) {
	 		throw new ExececaoPadrao("Erro na no envio da mensagem: " + e.getMessage());
	 	}
	 
	}
	
	public static void InformAdm() {
		try {
			System.out.println();
			System.out.println("Exibir Administrador: ");
			System.out.println();
			System.out.println(adm.toString());
			System.out.println();
			System.out.println("Obrigado pelo esforço administrador " +adm.getName() + "!. :)");
			
		} catch (Exception e) {
			System.err.println("Erro em exibir o Adm. " + e.getMessage());
		}
		return;
	}
	
	public static void InformUser() {
		
		try {
			System.out.println();
			System.out.println("Exibir Usuário: ");
			System.out.println();
			System.out.println(user.toString());
			System.out.println();
			System.out.println("Obrigador por se cadastrar " + user.getName() + "!. :)");
		} catch (Exception e) {
			System.err.println("Erro em exibir os Usuários. " + e.getMessage());
		}
		return;
	}
	
	public static void senhaInvalida() {
		System.out.println("Senha Invalida!!");
		System.out.println("Sua senha deve conter: ");
		System.out.println("1. Deve ter no mínimo 8 caracteres");
		System.out.println("2. Deve conter pelo menos uma letra maiúscula.");
		System.out.println("3. Deve conter pelo menos uma letra minúscula.");
		System.out.println("4. Deve conter pelo menos um número.");
		System.out.println("5. Deve conter pelo menos um caractere especial (por exemplo: @, #, !, etc.).");
	}
}