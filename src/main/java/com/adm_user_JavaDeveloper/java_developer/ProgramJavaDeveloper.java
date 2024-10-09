package com.adm_user_JavaDeveloper.java_developer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adm_user_JavaDeveloper.java_developer.entities.Administrador;
import com.adm_user_JavaDeveloper.java_developer.entities.Usuario;

import com.adm_user_JavaDeveloper.java_developer.exceptions.ExececaoPadrao;

import com.adm_user_JavaDeveloper.java_developer.metodos.AdmFuncionalidades;
import com.adm_user_JavaDeveloper.java_developer.metodos.SistemaDoAdm;
import com.adm_user_JavaDeveloper.java_developer.metodos.LoginSistema;
import com.adm_user_JavaDeveloper.java_developer.metodos.SistemaDoUsuario;
import com.adm_user_JavaDeveloper.java_developer.metodos.FuncionalidadesPrincipais;

@SpringBootApplication
public class ProgramJavaDeveloper {
	
	
	
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


	public static void main(String[] args) throws Exception{
		try {
			executeAdmOuUsuario();	
		} catch (Exception e) {
			throw new ExececaoPadrao("Error na chamada da função!");
		}
		
	}
	
	public static void executeAdmOuUsuario() throws ExececaoPadrao { 
		
		try {
			System.out.println("Bem-vindo");
			SistemaDoUsuario.logarNoSistema(() -> SistemaDoUsuario.loginUser(), () -> SistemaDoUsuario.loginAdm());

		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		
	}
	
	public static void getUser() {
		try {
			String name = LoginSistema.pegarNome();

			String phoneNumber = LoginSistema.pegarTelefone();
			
			String userInputsenha = LoginSistema.pegarSenha();	
		
			LocalDate dataNascimento = LoginSistema.pegarDataNascimento();

			LoginSistema.executeDbConnection(name, phoneNumber, userInputsenha, dataNascimento);

			new Usuario(name, phoneNumber, userInputsenha, dataNascimento);
			System.out.println();
			User();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void UserFunc(){
		try {
			FuncionalidadesPrincipais.pegarToString();

			FuncionalidadesPrincipais.lerOpcoesUsuario();

			FuncionalidadesPrincipais.procesarNome(FuncionalidadesPrincipais.procesarEntidade());
	
			FuncionalidadesPrincipais.procesarTelefone();

			FuncionalidadesPrincipais.procesarSenha();

			FuncionalidadesPrincipais.procesarData();
			
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	
	public static void processAdm() throws ExececaoPadrao{
		try {
			String name = SistemaDoAdm.pegarNomeAdm();

			String passwordAdm = SistemaDoAdm.pegarSenhaAdm();

			LocalDate dataNascimento = SistemaDoAdm.pegarDataAdm();

        	
            new Administrador(name, passwordAdm, dataNascimento);
            
			pegarFuncionalidadesAdm();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void pegarFuncionalidadesAdm() throws ExececaoPadrao{
		
		try {
			
			AdmFuncionalidades.pegarToString();
			
			AdmFuncionalidades.getUserOuAdm(() ->  AdmFuncionalidades.procesarIqualUsuario(), () -> AdmFuncionalidades.procesarIgualAdm());

			String upName = FuncionalidadesPrincipais.procesarNome(FuncionalidadesPrincipais.procesarEntidade());

			String upPassw = FuncionalidadesPrincipais.procesarSenha();

			LocalDate procesDate = FuncionalidadesPrincipais.procesarData();

			AdmFuncionalidades.procesarConnectionSQL(upName, upPassw, procesDate);
			
		}catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
			
		} 
	}
	
	public static void Adm() {
		try {
			AdmFuncionalidades.informacoesAdm();
			
		} catch (Exception e) {
			System.err.println("Erro de exibição"+ e.getMessage());
		}
		
	}
	
	public static void User() {
		try {
			FuncionalidadesPrincipais.InformUser();
			
		} catch (Exception e) {
			System.err.println("Erro de exibição" + e.getMessage());
		}
		
	}
}