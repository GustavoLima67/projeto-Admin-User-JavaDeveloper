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
			AdmOuUser();
			
		} catch (Exception e) {
			throw new ExececaoPadrao("Corrija suas credenciais.", e);
		}
		
	}
	
	public static void AdmOuUser() {
		try {
			
			System.out.print("Você é um Usuário ou um Administrador?: (User/Adm) ");
			String login = sc.next();
			
			if (login.equals("User")) {
				LoginUser();
				
			} 
			
			if (login.equals("Adm")) {
				LoginAdm();
				
			} else {
				throw new ExececaoPadrao("Erro na sintaxe!. Digite da forma descrita: (User/Adm).");
				
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void LoginAdm() throws ExececaoPadrao{
		try {
			
			System.out.print("Você tem uma conta como Administrador?: (y/n) ");
			char response = sc.next().charAt(0);
			
			String name;
			Long id;
			String senha;
			
			if (response == 'y') {
			    System.out.print("Entre com seu nome de login: ");
			    name = sc.next();
			
			    System.out.print("Entre com seu ID Unico: ");
			    id = sc.nextLong();
			
			    System.out.print("Entre com sua senha: ");
			    senha = sc.next();
			    
			} else if (response == 'n') {
			    System.out.print("Crie um nome: ");
			    name = sc.next();
			
			    System.out.print("Crie um ID: ");
			    id = sc.nextLong();
			
			    System.out.print("Crie uma senha: ");
			    senha = sc.next();
			    
			} else {
			    System.out.println("Resposta inválida.");
			    return; 
			}
			
			adm = new Administrador(id, name, senha);
			System.out.println();
			
		} catch (Exception e) {
			throw new ExececaoPadrao("Erro ao fazer login como Administrador!", e);
		}
	}
	
	public static void LoginUser() throws ExececaoPadrao{
		try {
			
			System.out.print("Você tem uma conta como Usuário?: (y/n) ");
			char response = sc.next().charAt(0);
			
			String name;
			String email;
			String senha;
			
			if(response == 'y') {
				System.out.print("Entre com seu nome de Usuário: ");
				name = sc.next();
				
				System.out.print("Entre com email: ");
				email = sc.next();
				
				System.out.print("Entre com sua senha: ");
				senha = sc.next();
				
			} else if(response == 'n') {
				System.out.print("Cadastre um nome de Usúario: ");
				name = sc.next();
				
				System.out.print("Cadastre um email de Usúario: ");
				email = sc.next();
				
				System.out.print("Cadastre uma senha de Usúario: ");
				senha = sc.next();												
				
			} else {
				System.out.print("Resposta inválida! ");
				return;
			}
			user = new Usuario(name, email, senha);
			System.out.println();
		
		} catch(Exception e) {
			throw new ExececaoPadrao("Erro ao fazer login como Usuário! ", e);
		}
	}
	
	public static void AdministradorFuncionalidade() throws ExececaoPadrao{
		try {
			
			funcAdm();
			System.out.print("O que deseja fazer?: ");
			String admFunci = sc.next();
			
			if (admFunci == "Exibir Usuarios" ) {
				System.out.println(user.toString());
				
				System.out.print("Deseja Mudar Alguma credencial: (y/n)");
				char response = sc.next().charAt(0);
				
				if (response == 'y') {
					System.out.println(user.toString());
					System.out.print("O que deseja mudar: (nome / email / senha) ");
					String mudar = sc.next();
					if (mudar == "nome") {
						System.out.print("Entre com o nome desejado: ");
						String name = sc.next();
						
						user.setName(name);
						System.out.println(user.toString());
					}
					if (mudar == "email") {
						System.out.print("Entre com o email desejado: ");
						String email = sc.next();
						
						user.setEmail(email);
						System.out.println(user.toString());
					}
					if (mudar == "senha") {
						System.out.print("Entre com a senha desejado: ");
						String senha = sc.next();
						
						user.setSenha(senha);
						System.out.println(user.toString());
					}
				}
			}
			
			if (admFunci == "Exibir Adm") {
				System.out.println(adm.toString());
				
				System.out.print("Deseja Mudar Alguma credencial: (y/n)");
				char response = sc.next().charAt(0);
				
				if (response == 'y') {
					System.out.println(adm.toString());
					System.out.print("O que deseja mudar: (nome / senha) ");
					String mudar = sc.next();
					if (mudar == "nome") {
						System.out.print("Entre com o nome desejado: ");
						String name = sc.next();
						
						adm.setName(name);
						System.out.println(adm.toString());
					}
					if (mudar == "senha") {
						System.out.print("Entre com a senha desejado: ");
						String senha = sc.next();
						
						adm.setSenha(senha);
						System.out.println(adm.toString());
					}
				}
			}			
		}catch (Exception e) {
			throw new ExececaoPadrao("Erro!, corrija suas credenciais");
		}
	}
	
	public static void funcAdm() {
		System.out.println("Bem Vindo Administrador!");
		System.out.println("FUNCIONALIDADES DO ADMINSTRADOR. ");
		System.out.println("(digite exatamente como esta escrito abaixo)");
		System.out.println();
		System.out.print("	Exibir Usuarios	 |	Exibir Adm	");
		System.out.println();
	}
	
	public static void UserFunc() throws ExececaoPadrao{
		try {
			
			funcUser();
			System.out.print("O que deseja fazer?: ");
			String admFunci = sc.next();
			
			if (admFunci == "Exibi meu usuario" ) {
				System.out.println(user.toString());
				
				System.out.print("Deseja Mudar Alguma credencial: (y/n)");
				char response = sc.next().charAt(0);
				
				if (response == 'y') {
					System.out.println(user.toString());
					System.out.print("O que deseja mudar: (nome / email / senha) ");
					String mudar = sc.next();
					if (mudar == "nome") {
						System.out.print("Entre com o nome desejado: ");
						String name = sc.next();
						
						user.setName(name);
						System.out.println(user.toString());
					}
					if (mudar == "email") {
						System.out.print("Entre com o email desejado: ");
						String email = sc.next();
						
						user.setEmail(email);
						System.out.println(user.toString());
					}
					if (mudar == "senha") {
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
	
	
	public static void funcUser() {
		System.out.println("Bem Vindo Usuario!");
		System.out.println("FUNCIONALIDADES DO USUARIO. ");
		System.out.println("(digite exatamente como esta escrito abaixo)");
		System.out.println();
		System.out.print(" Exibir Usuarios ");
		System.out.println();
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
		} catch (Exception e) {
			System.err.println("Erro em exibir o Adm. " + e.getMessage());
		}
		
	}
	
	public static void InformUser() {
		
		try {
			System.out.println();
			System.out.println("Exibir Usuário: ");
			System.out.println();
			System.out.println(user.toString());
		} catch (Exception e) {
			System.err.println("Erro em exibir os Usuários. " + e.getMessage());
		}
	}
	
	
 	
	
}





















