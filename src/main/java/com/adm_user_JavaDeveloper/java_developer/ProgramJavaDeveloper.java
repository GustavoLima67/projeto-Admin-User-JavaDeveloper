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
import com.adm_user_JavaDeveloper.java_developer.controller.exceptions.ExececaoPadrao;
import com.adm_user_JavaDeveloper.java_developer.model.Administrador;
import com.adm_user_JavaDeveloper.java_developer.model.Usuarios;
import com.adm_user_JavaDeveloper.java_developer.services.SistemaService;
import com.adm_user_JavaDeveloper.java_developer.services.MetodosService;

@SpringBootApplication(scanBasePackages = "com.adm_user_JavaDeveloper.java_developer")
@EntityScan(basePackages = "com.adm_user_JavaDeveloper.java_developer.model")
@EnableJpaRepositories(basePackages = "com.adm_user_JavaDeveloper.java_developer.repositories")
public class ProgramJavaDeveloper {
	
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public static Usuarios users = new Usuarios();
	public static Administrador adm = new Administrador();
	public static AdmController admController;
	public static UsuarioController usuarioController;

	public static Connection conn;
	public static PreparedStatement st;
	public static ResultSet rt;
	
	public static void main(String[] args) throws Exception{
		SpringApplication.run(ProgramJavaDeveloper.class, args);

		process();
	}
	
	public static void process() throws ExececaoPadrao { 
		try {
		SistemaService.logarNoSistema(() -> SistemaService.loginUser(), () -> SistemaService.loginAdm());
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		
	}
	
	public static void lerUsuarios() {
		try {
			String name = MetodosService.procesarNome();

			String userInputsenha = MetodosService.procesarSenha();	

			String phoneNumber = MetodosService.procesarTelefone();
		
			LocalDate dataNascimento = MetodosService.procesarData();

			MetodosService.executeDbConnection(name, userInputsenha, phoneNumber, dataNascimento);
			System.out.println();	

			users = new Usuarios(name, userInputsenha, phoneNumber, dataNascimento);

			inform();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void lerFuncionalidadesDeUsuarios(){
		try {
			String entidade = MetodosService.procesarEntidade();

			MetodosService.pegarToString(entidade);

			MetodosService.procesarNome();
	
			MetodosService.procesarTelefone();

			MetodosService.procesarSenha();

			MetodosService.procesarData();
			
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	
	public static void lerAdministrador() {
		try {;

			String name = MetodosService.procesarNome();

			String passwordAdm = MetodosService.procesarSenha();

			LocalDate dataNascimento = MetodosService.procesarData();
			
			System.out.println();

           	adm = new Administrador(name, passwordAdm, dataNascimento);
			MetodosService.executeDbConnectionAdm(name, passwordAdm, dataNascimento);

			inform();
			lerFuncionalidadesAdm();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void lerFuncionalidadesAdm() {
		try {
			MetodosService.exibirQuantUsuarios(() -> MetodosService.procesarUsuario(usuarioController), () -> MetodosService.procesarAdm(admController));
		}catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
			
		} 
	}
	
	public static void inform() {
		String entidade = MetodosService.procesarEntidade();
		try {
			MetodosService.exibir(users, adm, entidade);
		} catch (Exception e) {
			System.err.println("Erro de exibição"+ e.getMessage());
		}
	}

	public static void voltarInicio() {
		try {
			MetodosService.voltarInicio(() -> lerUsuarios(), () -> lerAdministrador());
		} catch (ExececaoPadrao e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
}