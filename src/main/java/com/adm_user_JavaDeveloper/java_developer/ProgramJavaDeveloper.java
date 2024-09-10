package com.adm_user_JavaDeveloper.java_developer;

import java.io.IOException;
import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adm_user_JavaDeveloper.java_developer.entities.Administrador;
import com.adm_user_JavaDeveloper.java_developer.entities.Usuario;
import com.adm_user_JavaDeveloper.java_developer.exceptions.ExececaoPadrao;
import com.adm_user_JavaDeveloper.java_developer.notifications.MailgunEmailSender;
import com.adm_user_JavaDeveloper.java_developer.validators.PasswordValidators;

@SpringBootApplication
public class ProgramJavaDeveloper {
	
	public static Scanner sc = new Scanner(System.in);
	public static Administrador adm = new Administrador();
	public static Usuario user = new Usuario();
	
	

	public static void main(String[] args) {
		try {
			
			LoginUserAdm();
			Inform();
			
			
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
				throw new ExececaoPadrao("Erro na sintaxe!. Digite da forma descrita: (User/Adm).");
				
			}
			
		} catch (Exception e) {
			throw new ExececaoPadrao("Corrija suas credenciais.", e);
		}
		
	}
	
	public static void loginUser() throws ExececaoPadrao{
		try {
			
			
			System.out.print("Entre com seu nome de Usuário: ");
			String name = sc.next();
			
			System.out.print("Entre com email: ");
			String email = sc.next();
			
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
			System.out.println("Senha valida! acesso concedido!");
			
			try {
				MailgunEmailSender.sendEmail(email, "Cadastro feito com sucesso", "Você foi cadastrado como Usuario em um projeto do Dev Gustavo. \nSe não for relevante desconsidere esse email!.");
			}catch (Exception e) {
				throw new ExececaoPadrao("Erro no cadastro do email tente novamente");
			}
			
			user = new Usuario(name, email, UserInputsenha);
			System.out.println("Usuario Criado" + user);
			System.out.println();
			
			UserFunc();
		
		} catch(Exception e) {
			throw new ExececaoPadrao("Erro ao fazer login como Usuário! ", e);
		}
	}

	public static void UserFunc() throws ExececaoPadrao{
		try {
			
			System.out.println(user.toString());
			
			System.out.print("Deseja mudar alguma credencial: (y/n)");
			char response = sc.next().charAt(0);
			System.out.println();
			
			if (response == 'y') {
				System.out.print("O que deseja mudar: (nome / email / senha) ");
				String mudar = sc.next();
				
				if (mudar.equals("nome")) {
					System.out.print("Entre com o nome desejado: ");
					String name = sc.next();
					
					user.setName(name);
					System.out.println(user.toString());
				}
				else if (mudar.equals("email")) {
					System.out.print("Entre com o email desejado: ");
					String email = sc.next();
					try {
						MailgunEmailSender.sendEmail(email, "Cadastro feito com sucesso", "Bem vindo Usuario!.");
					}catch (IOException e) {
						e.printStackTrace();
					}
					
					
					user.setEmail(email);
					System.out.println(user.toString());
				}
				else if (mudar.equals("senha")) { 
					
					boolean senhaValida;
					do {
			            System.out.print("Digite sua senha: ");
			            String UserInputsenha = sc.nextLine();
			            
			            senhaValida = PasswordValidators.validatePassword(UserInputsenha);
	
			            if (!senhaValida) {
			                senhaInvalida();
			            }

		        } while (!senhaValida);
			        
					System.out.println("Senha valida! acesso concedido!");
					User();
					
				}
				
				if(response == 'n') {
					User();
				}
			}
		}
		catch (Exception e) {
			throw new ExececaoPadrao("Erro!, corrija suas credenciais");
		}
	}
	
	public static void loginAdm() throws ExececaoPadrao{
		try {
			
		    System.out.print("Entre com seu nome de login administrador: ");
		    String name = sc.next();
		
		    System.out.print("Entre com seu ID Unico: ");
		    Long id = sc.nextLong();
		
		    System.out.print("Entre com sua senha: ");
		    String senha = sc.next();
			
			adm = new Administrador(id, name, senha);
			System.out.println();
			
			admFunc();
			
		} catch (Exception e) {
			throw new ExececaoPadrao("Erro ao fazer login como Administrador!", e);
		}
	}
	
	public static void admFunc() throws ExececaoPadrao{
		
		try {
			System.out.println(adm.toString());
			
			System.out.print("O que deseja fazer?: (Escreva exatamente como esta abaixo) ");
			System.out.println();
			System.out.println("User | Adm");
			String func = sc.next();
			
			if (func.equals("User")) {
				
				try {
					if(user.getName() == null && user.getEmail() == null && user.getSenha() == null) {
						System.out.print("Usuario não existe:\nCrie um novo usuario para prosseguir:\n");
						loginUser();
					}
					System.out.println(user.toString());
				} catch (Exception e) {
					e.getMessage();
				}
				
				
				System.out.print("Deseja Mudar Alguma credencial: (y/n)");
				char response = sc.next().charAt(0);
				
				if (response == 'y') {
					UserFunc();
				}
					
				else {
					User();
				}
				
			
			}
			
			if (func.equals("Adm")) {
				System.out.println(adm.toString());
				
				System.out.print("Deseja Mudar Alguma credencial: (y/n)");
				char response = sc.next().charAt(0);
				
				if (response == 'y') {
					System.out.println(adm.toString());
					System.out.print("O que deseja mudar: (nome / email / senha) ");
					String mudar = sc.next();
					
					if (mudar.equals("nome")) {
						System.out.print("Entre com o nome desejado: ");
						String name = sc.next();
						
						adm.setName(name);
						System.out.println(user.toString());
					}
					if (mudar.equals("senha")) {
						boolean senhaValida;
						do {
				            System.out.print("Digite sua senha: ");
				            String UserInputsenha = sc.nextLine();
				            
				            senhaValida = PasswordValidators.validatePassword(UserInputsenha);
		
				            if (!senhaValida) {
				                senhaInvalida();
				            }

			        } while (!senhaValida);
				        
						System.out.println("Senha valida! acesso concedido!");
						User();
						
					}
					
					if(response == 'n') {
						Adm();
					}
					
				}
			} 
			
		}catch (Exception e) {
			throw new ExececaoPadrao("Erro!, corrija suas credenciais");
		} 
	}
	
	public static void Adm() {
		try {
			System.out.println("Bem Vindo Administrador!");
			System.out.println("FUNCIONALIDADES DO ADMINSTRADOR. ");
			System.out.println("(digite exatamente como esta escrito abaixo)");
			InformAdm();
		} catch (Exception e) {
			System.err.println("Erro de exibição"+ e.getMessage());
		}
		
	}
	
	
		
	public static void userFunc() throws ExececaoPadrao{
		try {
			System.out.println();
			System.out.println(user.toString());
			
			System.out.print("Deseja Mudar Alguma credencial: (y/n)");
			char response = sc.next().charAt(0);
			
			if (response == 'y') {
				System.out.println(user.toString());
				System.out.print("O que deseja mudar: (nome / email / senha) ");
				String mudar = sc.next();
				
				try {
					if (mudar.equals("nome")) {
						System.out.print("Entre com o nome desejado: ");
						String name = sc.next();
						
						user.setName(name);
						System.out.println(user.toString());
					}
					if (mudar.equals("email")) {
						System.out.print("Entre com o email desejado: ");
						String email = sc.next();
						
						user.setEmail(email);
						System.out.println(user.toString());
					}
					if (mudar.equals("senha")) {
						boolean senhaValida;
						
				        do {
				            System.out.print("Digite sua senha: ");
				            String UserInputsenha = sc.nextLine();
				            
				            senhaValida = PasswordValidators.validatePassword(UserInputsenha);

				            if (!senhaValida) {
				                senhaInvalida();
				            }

				        } while (!senhaValida);
				        
						System.out.println("Senha valida! acesso concedido!");
						User();
						
					}
				} catch (Exception e) {
					e.getMessage();
				}	
			}
		
	
			} catch (Exception e) {
			throw new ExececaoPadrao("Erro!, corrija suas credenciais");
		}
	}
	
	
	public static void User() {
		
		try {
			System.out.println("Bem Vindo Usuario!");
			System.out.println("FUNCIONALIDADES DO USUARIO. ");
			System.out.println("(digite exatamente como esta escrito abaixo)");
			System.out.println();
			InformUser();
		} catch (Exception e) {
			System.err.println("Erro de exibição" + e.getMessage());
		}
		
	}
	
	public static void Inform() {
		InformAdm();
		InformUser();
	}
	
	public static void InformAdm() {
		try {
			System.out.println();
			System.out.println("Exibir Administrador: ");
			System.out.println();
			System.out.println(adm.toString());
			System.out.println();
			
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