package com.adm_user_JavaDeveloper.java_developer;

import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adm_user_JavaDeveloper.java_developer.entities.Administrador;
import com.adm_user_JavaDeveloper.java_developer.entities.Usuario;
import com.adm_user_JavaDeveloper.java_developer.exceptions.ExececaoPadrao;

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
			
			AdmOuUser();
			
		} catch (Exception e) {
			throw new ExececaoPadrao("Corrija suas credenciais.", e);
		}
		
	}
	
	public static void AdmOuUser() {
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
			System.err.println(e.getMessage());
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
	
	public static void loginUser() throws ExececaoPadrao{
		try {
			
			String name;
			String email;
			String senha;	
			
			System.out.print("Entre com seu nome de Usuário: ");
			name = sc.next();
			
			System.out.print("Entre com email: ");
			email = sc.next();
			
			System.out.print("Entre com sua senha: ");
			senha = sc.next();
		
			user = new Usuario(name, email, senha);
			System.out.println();
			System.out.println(user.toString());
			
			UserFunc();
		
		} catch(Exception e) {
			throw new ExececaoPadrao("Erro ao fazer login como Usuário! ", e);
		}
	}
	
	public static void UserFunc() throws ExececaoPadrao{
		try {
			
			System.out.print("O que deseja fazer?:  (Exibir Usuario) ");
			String admFunci = sc.next();
			
			if (admFunci.equals("Exibir Usuario")) {
				System.out.println(user.toString());
				
				System.out.print("Deseja mudar alguma credencial: (y/n)");
				char response = sc.next().charAt(0);
				
				if (response == 'y') {
					System.out.println(user.toString());
					System.out.print("O que deseja mudar: (nome / email / senha) ");
					String mudar = sc.next();
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
						System.out.print("Entre com a senha desejado: ");
						String senha = sc.next();
						
						user.setSenha(senha);
						System.out.println(user.toString());
					}
					
					if(response == 'n') {
						User();
					}
					
				}
			}
			
			if (admFunci.equals("Exibir Adm")) {
				System.out.println(adm.toString());
				
				System.out.print("Deseja Mudar Alguma credencial: (y/n)");
				char response = sc.next().charAt(0);
				
				if (response == 'y') {
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
						System.out.print("Entre com a senha desejado: ");
						String senha = sc.next();
						
						adm.setSenha(senha);
						System.out.println(adm.toString());
					}
				}
				Adm();
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
	
	public static void admFunc() throws ExececaoPadrao{
		
		try {
			System.out.print("O que deseja fazer?: ");
			System.out.println();
			System.out.println("User | Adm");
			String func = sc.next();
			
			if (func.equals("User")) {
				
				try {
					if(user.getName() == null && user.getEmail() == null && user.getSenha() == null) {
						System.out.print("Usuario não existe:\nCrie um novo usuario para prosseguir:\n");
						loginUser();
					}
				} catch (Exception e) {
					e.getMessage();
				}
				System.out.println(user.toString());
				
				System.out.print("Deseja Mudar Alguma credencial: (y/n)");
				char response = sc.next().charAt(0);
				
				if (response == 'y') {
					System.out.println(user.toString());
					System.out.print("O que deseja mudar: (nome / email / senha) ");
					String mudar = sc.next();
					
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
						System.out.print("Entre com a senha desejado: ");
						String senha = sc.next();
						
						user.setSenha(senha);
						System.out.println(user.toString());
					}
					
					if(response == 'n') {
						User();
					}
					
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
						System.out.print("Entre com a senha desejado: ");
						String senha = sc.next();
						
						adm.setSenha(senha);
						System.out.println(user.toString());
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
		
	public static void userFunc() throws ExececaoPadrao{
		try {
			System.out.print("O que deseja fazer?: ");
			String admFunci = sc.next();
			
			if (admFunci.equals("Exibir usuario")) {
				System.out.println(user.toString());
				
				System.out.print("Deseja Mudar Alguma credencial: (y/n)");
				char response = sc.next().charAt(0);
				
				if (response == 'y') {
					System.out.println(user.toString());
					System.out.print("O que deseja mudar: (nome / email / senha) ");
					String mudar = sc.next();
					
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
						System.out.print("Entre com a senha desejado: ");
						String senha = sc.next();
						
						user.setSenha(senha);
						System.out.println(user.toString());
					}
				}
				
			}
			
		}catch (Exception e) {
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
	
}