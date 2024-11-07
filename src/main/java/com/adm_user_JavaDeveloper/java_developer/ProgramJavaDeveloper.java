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
	
	public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public Usuarios users = new Usuarios();
	public Administrador adm = new Administrador();
	public AdmController admController;
	public UsuarioController usuarioController;

	public Connection conn;
	public PreparedStatement st;
	public ResultSet rt;

	
	public void main(String[] args) throws Exception{
		SpringApplication.run(ProgramJavaDeveloper.class, args);

		process();
	}
	
	public void process() { 
		try {
		SistemaService.logarNoSistema(() -> SistemaService.loginUser(), () -> SistemaService.loginAdm());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void lerUsuarios() {
		try {
			String name = MetodosService.procesarNome();

			String userInputsenha = MetodosService.procesarSenha();	

			String emailUser = MetodosService.procesarEmail();
		
			LocalDate dataNascimento = MetodosService.procesarData();

			MetodosService.executeDbConnection(name, userInputsenha, emailUser, dataNascimento);
			System.out.println();	

			users = new Usuarios(name, userInputsenha, emailUser, dataNascimento);

			
			lerFuncionalidadesDeUsuarios();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void lerFuncionalidadesDeUsuarios(){
		try {
			String entidade = MetodosService.procesarEntidade();

			MetodosService.pegarToString(entidade);
			MetodosService.procesarUsuario(usuarioController);

			inform();

		} catch (Exception e) {
			throw new IllegalArgumentException("ERROR NA FUNÇÃO " + e.getLocalizedMessage());
		}
	}

	public void lerAdministrador() {
		try {
			String name = MetodosService.procesarNome();

			String passwordAdm = MetodosService.procesarSenha();

			String email = MetodosService.procesarEmail();
			
			String cargo = MetodosService.pegarCargo();

			System.out.println();

           	adm = new Administrador(name, passwordAdm, email, cargo);
			MetodosService.executeDbConnectionAdm(name, passwordAdm, email, cargo);

			lerFuncionalidadesAdm();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void lerFuncionalidadesAdm() {
		try {
			if (usuarioController != null && admController != null) {
				MetodosService.exibirQuantUsuarios(() -> MetodosService.procesarUsuario(usuarioController), () -> MetodosService.procesarAdm(admController));
			} else {
				System.out.println("Erro: Controladores não inicializados corretamente.");
			}

			inform();

			MetodosService.voltarInicio(() -> lerUsuarios(), () -> lerAdministrador());
		}catch (Exception e) {
			e.printStackTrace();
			
		} 
	}
	
	public void inform() {
		String entidade = MetodosService.procesarEntidade();
		try {
			MetodosService.exibir(users, adm, entidade);
		} catch (Exception e) {
			System.err.println("Erro de exibição"+ e.getMessage());
		}
	}
}