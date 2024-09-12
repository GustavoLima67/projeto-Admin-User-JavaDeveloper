package com.adm_user_JavaDeveloper.java_developer;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adm_user_JavaDeveloper.java_developer.entities.Administrador;
import com.adm_user_JavaDeveloper.java_developer.entities.Usuario;
import com.adm_user_JavaDeveloper.java_developer.exceptions.ExececaoPadrao;
import com.adm_user_JavaDeveloper.java_developer.validators.PasswordValidators;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@SpringBootApplication
public class ProgramJavaDeveloper {
	
	public static Scanner sc = new Scanner(System.in);
	public static Administrador adm = new Administrador();
	public static Usuario user = new Usuario();
	
	public static final String ACCOUNT_SID = "AC4b437239aec2ab656a95cf5f9c218312";
    public static final String AUTH_TOKEN = "de5bbfcd665068c60746184b467bcaf2";

    public static void main(String[] args) {
		try {
			LoginUserAdm();
			
		} catch(Exception e) {
			System.err.println("Error!"+ e);
			e.printStackTrace();
		}
		
	}
	
	public static void LoginUserAdm() throws ExececaoPadrao{
		
		try {
			
			System.out.print("Você é um Usuário?: (y/n) ");
			char response = sc.next().charAt(0);
			
			if (response == 'y') {
				loginUser();
				
			} 
			
			else if (response == 'n') {
				
				loginAdm();
				
			} else {
				throw new ExececaoPadrao("Erro na sintaxe!. Digite da forma descrita: (y/n).");
				
			}
			
		} catch (Exception e) {
			throw new ExececaoPadrao("Corrija suas credenciais.", e);
		}
		
	}
	
	public static void loginUser(){
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
				}catch (Exception e) {
					System.out.println("Erro ao enviar o SMS" + e.getMessage());
				}
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			boolean senhaValida;
			String UserInputsenha;
			
			do {
				System.out.print("Entre com uma senha valida: ");
	            UserInputsenha = sc.nextLine();
	            
	            senhaValida = PasswordValidators.validatePassword(UserInputsenha);

	            if (!senhaValida) {
	                senhaInvalida();
	            }
			}  while (!senhaValida);
        	
            user = new Usuario(name, phoneNumber, UserInputsenha);
            
			System.out.println("Senha valida! acesso concedido!");
			System.out.println();
			
			User();
		
		} catch(Exception e) {
			e.getMessage();
		}
	}

	public static void UserFunc(){
		try {
			System.out.println();
			System.out.println(user.toString());
			System.out.println();
			System.out.print("O que deseja mudar: (nome / telefone / senha) ");
			String mudar = sc.next();
			
			if (mudar.equals("nome")) {
				System.out.print("Entre com o nome desejado: ");
				String name = sc.next();
				
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
		        
				System.out.println("Senha valida! acesso concedido!");
				User();
				
			}
		}
		catch (Exception e) {
			e.getMessage();
		}
	}
	
	public static void loginAdm() throws ExececaoPadrao{
		try {
			
			System.out.print("Entre com seu ID Unico: ");
		    Long id = sc.nextLong();
		    
		    sc.nextLine();
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
        	
            adm = new Administrador(id, name, UserInputsenha);
            
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
						admFunc();
					}
				} catch (Exception e) {
					e.getMessage();
				}
				User();
			
			}
			
			if (func.equals("Adm")) {
				System.out.println(adm.toString());
				System.out.print("O que deseja mudar: (nome / senha) ");
				String mudar = sc.next();
				
				if (mudar.equals("nome")) {
					System.out.print("Entre com o nome desejado: ");
					String name = sc.next();
					
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
					
					adm.setSenha(UserInputsenha);
					System.out.println(adm.toString());
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
				
				if (response == 'y') {
					admFunc();
				} else if (response == 'n' ) {
					InformAdm();
				} else {
					System.out.println("Resposta inválida, informe 'y' ou 'n'. ");
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
				System.out.print("Quer mudar suas informações: (y/n) ");
				response = sc.next().charAt(0);
				
				if (response == 'y') {
					UserFunc();
				} else if(response == 'n'){
					InformUser();
				} else {
					System.out.println("Resposta inválida, informe 'y' ou 'n'. ");
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
	
	public static void sendSms(String phoneNumber) {
	 Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
	 try {

	   	 Message message = Message.creator(
	                new PhoneNumber(phoneNumber), 
	                new PhoneNumber("+12513021245"),    
	                "Seu número foi cadastrado no 'Projeto JavaDeveloper Adm / User' com sucesso!")
	            .create();

	        System.out.println("Mensagem enviada com sucesso: " + message.getSid());
 
	 	} catch (Exception e) {
	 		System.out.println("Erro ao enviar o SMS.");
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