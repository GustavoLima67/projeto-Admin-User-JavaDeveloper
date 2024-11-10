package com.adm_user_JavaDeveloper.java_developer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.adm_user_JavaDeveloper.java_developer.controller.AdmController;
import com.adm_user_JavaDeveloper.java_developer.controller.UsuarioController;
import com.adm_user_JavaDeveloper.java_developer.model.Administrador;
import com.adm_user_JavaDeveloper.java_developer.model.Usuarios;
import com.adm_user_JavaDeveloper.java_developer.services.EmailService;
import com.adm_user_JavaDeveloper.java_developer.services.MetodosService;
import com.adm_user_JavaDeveloper.java_developer.services.SistemaService;

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

	public static ApplicationContext context;
    private static EmailService emailService;

	public Connection conn;
	public PreparedStatement st;
	public ResultSet rt;

	public static void main(String[] args) throws Exception{
		SpringApplication.run(ProgramJavaDeveloper.class, args);

		process();
	}

    @Autowired
    public void setApplicationContext(ApplicationContext context) {
        ProgramJavaDeveloper.context = context;
        emailService = context.getBean(EmailService.class);
		usuarioController = context.getBean(UsuarioController.class);
		admController = context.getBean(AdmController.class);
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

			emailService.enviarMensagemEmail(emailUser, "Email Cadastrado com sucesso!",
													"Você foi cadastrado como usuário no projeto pessoal de Gustavo \\n" + 
													"Obs: seu email está protejo \\n" +
													"Att. Gustavo");
													
			users = new Usuarios(name, userInputsenha, emailUser, dataNascimento);
	
			inform();
			lerFuncionalidadesDeUsuarios();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void lerFuncionalidadesDeUsuarios(){
		try {
			String entidade = metodosService.procesarEntidade();

			if (usuarioController != null && admController != null) {
				metodosService.procesarUsuario(entidade, usuarioController, admController);
			} 
			else {
				System.out.println("Erro: Controladores não inicializados corretamente.");
			}

			metodosService.deletarAdmOuUser(entidade);
			
			metodosService.voltarInicio(() -> lerUsuarios(), () -> lerAdministrador());
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
			emailService.enviarMensagemEmail(email, "Email Cadastrado com sucesso! ", 
																"Você foi cadastrado como moderador no projeto pessoal de Gustavo\\n" +
																"Seu email está protejo\\n" +
																"Att. Gustavo");
			metodosService.executeDbConnectionAdm(name, passwordAdm, email, cargo);

			process();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void lerFuncionalidadesAdm() {
		String entidade = metodosService.procesarEntidade();
		try {
			if (usuarioController != null && admController != null) {
				metodosService.procesarUsuario(entidade, usuarioController, admController);
			} 
			else {
				System.out.println("Erro: Controladores não inicializados corretamente.");
			}
			inform();

			metodosService.deletarAdmOuUser(entidade);

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