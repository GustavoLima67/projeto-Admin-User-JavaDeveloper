package com.adm_user_JavaDeveloper.java_developer;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adm_user_JavaDeveloper.java_developer.entities.Administrador;
import com.adm_user_JavaDeveloper.java_developer.entities.Usuario;
import com.adm_user_JavaDeveloper.java_developer.exceptions.ExececaoPadrao;

@SpringBootApplication
public class JavaDeveloperApplication {
	
	public static Scanner sc = new Scanner(System.in);
	public static  Administrador adm;
	public static  Usuario user;

	public static void main(String[] args) {
		SpringApplication.run(JavaDeveloperApplication.class, args);
		
		try {
			
			
			
			
		} catch(Exception e) {
			System.err.println("Error!"+ e);
			e.printStackTrace();
		}
		
	}
	
	public static void LoginUserAdm() throws ExececaoPadrao{
		
		try {
			
			LoginAdm();
			
		} catch (Exception e) {
			throw new ExececaoPadrao("Corrija suas credenciais.", e);
		}
		
	}
	
	public static void LoginAdm() throws ExececaoPadrao{
		try {
			
			System.out.print("Você tem uma conta como Administrador: (y/n) ");
			char response = sc.next().charAt(0);
			
			if (response == 'y') {
				System.out.print("Entre com seu nome de login: ");
				String name = sc.next();
				
				System.out.print("Entre com seu ID Unico: ");
				Long id = sc.nextLong();
				
				System.out.print("Entre com sua senha: ");
				String senha = sc.next();
				
				adm = new Administrador(id, name, senha);
			}
			
			if (response == 'n') {
				System.out.print("Crie um nome: ");
				String name = sc.next();
				
				System.out.print("Crie um ID: ");
				Long id = sc.nextLong();
				
				System.out.print("Crie uma senha: ");
				String senha = sc.next();
				
				adm = new Administrador(id, name, senha);
			}
			
		} catch (Exception e) {
			throw new ExececaoPadrao("Error ao fazer login como Administrador!", e);
		}
	}
 	
	
}





















