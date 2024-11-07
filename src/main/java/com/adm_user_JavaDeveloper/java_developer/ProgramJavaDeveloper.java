package com.adm_user_JavaDeveloper.java_developer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.adm_user_JavaDeveloper.java_developer.controller.AdmController;
import com.adm_user_JavaDeveloper.java_developer.controller.UsuarioController;
import com.adm_user_JavaDeveloper.java_developer.model.Administrador;
import com.adm_user_JavaDeveloper.java_developer.model.Usuarios;
import com.adm_user_JavaDeveloper.java_developer.services.SistemaService;
import com.adm_user_JavaDeveloper.java_developer.services.MetodosService;

@SpringBootApplication(scanBasePackages = "com.adm_user_JavaDeveloper.java_developer")
@EntityScan(basePackages = "com.adm_user_JavaDeveloper.java_developer.model")
@EnableJpaRepositories(basePackages = "com.adm_user_JavaDeveloper.java_developer.repositories")
public class ProgramJavaDeveloper {
	
	
	private static MetodosService metodosService = new MetodosService();

	
	public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private static Usuarios users = new Usuarios();
	private static Administrador adm = new Administrador();
	
	private static AdmController admController;
	private static UsuarioController usuarioController;

	public static Connection conn;
	public static PreparedStatement st;
	public static ResultSet rt;
		
	public static void main(String[] args) throws Exception{
		SpringApplication.run(ProgramJavaDeveloper.class, args);

		process();
	}
	
	public static void process() { 
		try {
			SistemaService.logarNoSistema(() -> SistemaService.loginUser(), () -> SistemaService.loginAdm());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void lerUsuarios() {
		try {
			String name = metodosService.procesarNome();

			String userInputsenha = metodosService.procesarSenha();	

			String emailUser = metodosService.procesarEmail();
		
			LocalDate dataNascimento = metodosService.procesarData();

			metodosService.executeDbConnection(name, userInputsenha, emailUser, dataNascimento);
			System.out.println();	

			users = new Usuarios(name, userInputsenha, emailUser, dataNascimento);

			
			lerFuncionalidadesDeUsuarios();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void lerFuncionalidadesDeUsuarios(){
		try {
			String entidade = metodosService.procesarEntidade();

			metodosService.pegarToString(entidade);
			metodosService.procesarUsuario(usuarioController);

			inform();

		} catch (Exception e) {
			throw new IllegalArgumentException("ERROR NA FUNÇÃO " + e.getLocalizedMessage());
		}
	}

	public static void lerAdministrador() {
		try {
			String name = metodosService.procesarNome();

			String passwordAdm = metodosService.procesarSenha();

			String email = metodosService.procesarEmail();
			
			String cargo = metodosService.pegarCargo();

			System.out.println();

           	adm = new Administrador(name, passwordAdm, email, cargo);
			metodosService.executeDbConnectionAdm(name, passwordAdm, email, cargo);

			lerFuncionalidadesAdm();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void lerFuncionalidadesAdm() {
		try {
			if (usuarioController != null && admController != null) {
				metodosService.exibirQuantUsuarios(() -> metodosService.procesarUsuario(usuarioController), () -> metodosService.procesarAdm(admController));
			} else {
				System.out.println("Erro: Controladores não inicializados corretamente.");
			}

			inform();

			metodosService.voltarInicio(() -> lerUsuarios(), () -> lerAdministrador());
		}catch (Exception e) {
			e.printStackTrace();
			
		} 
	}
	
	public static void inform() {
		String entidade = metodosService.procesarEntidade();
		try {
			metodosService.exibir(users, adm, entidade);
		} catch (Exception e) {
			System.err.println("Erro de exibição"+ e.getMessage());
		}
	}
}