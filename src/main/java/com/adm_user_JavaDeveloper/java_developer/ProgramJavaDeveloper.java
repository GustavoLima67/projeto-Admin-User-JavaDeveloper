package com.adm_user_JavaDeveloper.java_developer;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adm_user_JavaDeveloper.java_developer.entities.Administrador;
import com.adm_user_JavaDeveloper.java_developer.entities.Usuario;
import com.adm_user_JavaDeveloper.java_developer.exceptions.ExececaoPadrao;

@SpringBootApplication
public class ProgramJavaDeveloper {
	
	public static Scanner sc = new Scanner(System.in);
	public static  Administrador adm;
	public static  Usuario user;
	

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
			
			LoginAdm();
			LoginUser();
			
		} catch (Exception e) {
			throw new ExececaoPadrao("Corrija suas credenciais.", e);
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
			
			System.out.print("Você tem uma como Usuário?: (y/n) ");
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
	
	public static void Inform() {
		InformAdm();
		InformUser();
	}
	
	public static void InformAdm() {
		
		try {
			System.out.println();
			System.out.println("Exibir Administrador: ");
			System.out.println(adm.toString());
		} catch (Exception e) {
			System.err.println("Erro em exibir o Adm. " + e);
		}
		
	}
	
	public static void InformUser() {
		
		try {
			System.out.println();
			System.out.println("Exibir Usuário: ");
			System.out.println(user.toString());
		} catch (Exception e) {
			System.err.println("Erro em exibir os Usuários. " + e);
		}
		
	}
 	
	
}





















