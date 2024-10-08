package com.adm_user_JavaDeveloper.java_developer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.adm_user_JavaDeveloper.java_developer.entities.Administrador;
import com.adm_user_JavaDeveloper.java_developer.entities.Usuario;

import com.adm_user_JavaDeveloper.java_developer.exceptions.ExececaoPadrao;

import com.adm_user_JavaDeveloper.java_developer.metodos.AdmFuncionalidades;
import com.adm_user_JavaDeveloper.java_developer.metodos.ExibirNoSistema;
import com.adm_user_JavaDeveloper.java_developer.metodos.SistemaDoAdm;
import com.adm_user_JavaDeveloper.java_developer.metodos.LoginSistema;
import com.adm_user_JavaDeveloper.java_developer.metodos.SistemaDoUsuario;
import com.adm_user_JavaDeveloper.java_developer.metodos.FuncionalidadesPrincipais;

@SpringBootApplication
public class ProgramJavaDeveloper {
	
	
	private static Administrador adm = new Administrador();
	private static Usuario user = new Usuario();
	
	
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

			user = new Usuario(name, phoneNumber, userInputsenha, dataNascimento);
			System.out.println();
			User();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void UserFunc(){
		try {
			String mudar = "";
			FuncionalidadesPrincipais.exibirInfomacoesUsuario();

			FuncionalidadesPrincipais.lerOpcoesUsuario();

			FuncionalidadesPrincipais.procesarNome(mudar);
	
			FuncionalidadesPrincipais.procesarTelefone(mudar);

			FuncionalidadesPrincipais.procesarSenha(mudar);

			FuncionalidadesPrincipais.procesarData(mudar);
			
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

        	
            adm = new Administrador(name, passwordAdm, dataNascimento);
            
			pegarFuncionalidadesAdm();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void pegarFuncionalidadesAdm() throws ExececaoPadrao{
		
		try {
			
			System.out.println();
			System.out.println(adm.toString());
			System.out.println();
			
			String mudar = "";    
			
			AdmFuncionalidades.getUserOuAdm(() ->  AdmFuncionalidades.procesarIqualUsuario(), () -> AdmFuncionalidades.procesarIgualAdm());

			String upName = AdmFuncionalidades.atualizarIgualNome(mudar);

			String upPassw = AdmFuncionalidades.atualizarIgualSenha(mudar);

			LocalDate procesDate = FuncionalidadesPrincipais.procesarData(mudar);

			AdmFuncionalidades.procesarConnectionSQL(upName, upPassw, procesDate);
			
		}catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
			
		} 
	}
	
	public static void Adm() {
		try {
			ExibirNoSistema.exibirAdm();
			
		} catch (Exception e) {
			System.err.println("Erro de exibição"+ e.getMessage());
		}
		
	}
	
	public static void User() {
		try {
			ExibirNoSistema.exibirUsuario();
			
		} catch (Exception e) {
			System.err.println("Erro de exibição" + e.getMessage());
		}
		
	}
	
	public static void informacoesAdm() {
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