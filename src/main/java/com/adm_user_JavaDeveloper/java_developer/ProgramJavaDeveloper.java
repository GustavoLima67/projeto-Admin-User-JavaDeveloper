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
import com.adm_user_JavaDeveloper.java_developer.controller.exceptions.DefaultException;
import com.adm_user_JavaDeveloper.java_developer.model.Administrador;
import com.adm_user_JavaDeveloper.java_developer.model.Usuarios;
import com.adm_user_JavaDeveloper.java_developer.services.EmailService;
import com.adm_user_JavaDeveloper.java_developer.services.MetodosService;
import com.adm_user_JavaDeveloper.java_developer.services.SistemaService;

@SpringBootApplication(scanBasePackages = "com.adm_user_JavaDeveloper.java_developer")
@EntityScan(basePackages = "com.adm_user_JavaDeveloper.java_developer.model")
@EnableJpaRepositories(basePackages = "com.adm_user_JavaDeveloper.java_developer.repositories")
public class ProgramJavaDeveloper {
	
	private static final MetodosService metodosService = new MetodosService();
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

	public static void process() throws DefaultException{ 
		try {
			SistemaService.logarNoSistema(() -> SistemaService.loginUser(), () -> SistemaService.loginAdm());
		} catch (RuntimeException e) {
			throw new DefaultException("Erro na função principal 'process()', provavel erro na expressão lambda!" + e.getLocalizedMessage());
		}
		
	}
	
	public static void lerUsuarios() throws DefaultException {
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
		} catch (RuntimeException e) {
			throw new DefaultException("Erro na função 'lerUsuarios()', analise dado por dado!!" + e.getLocalizedMessage());
		}
	}

	public static void lerFuncionalidadesDeUsuarios() throws DefaultException {
		try {
			String entidade = metodosService.procesarEntidade();
			String informDeletar = metodosService.pegarInformDeleteOuVisualizar();

			if (informDeletar.equals('s')) {
				metodosService.deletarAdmOuUser(entidade);
			} 
			else {
				if (usuarioController != null && admController != null) {
					metodosService.lerQuantidadeUserOuAdm(entidade, usuarioController, admController);
				} 
				else {
					System.out.println("Erro: Controladores não inicializados corretamente.");
				}
			}
			
			metodosService.voltarInicio(() -> lerUsuarios(), () -> lerAdministrador());
		} catch (RuntimeException e) {
			throw new DefaultException("Erro na função 'lerFuncionalidadesDeUsuarios()', analise dado por dado! " + e.getLocalizedMessage());
		}
	}

	public static void lerAdministrador() throws DefaultException {
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
		} catch (RuntimeException e) {
			throw new DefaultException("Erro na função 'lerAdministrador()', analise dado por dado! " + e.getLocalizedMessage());
		}
	}
	
	public static void lerFuncionalidadesAdm() throws DefaultException {
		try {
			String entidade = metodosService.procesarEntidade();
			String informDeletar = metodosService.pegarInformDeleteOuVisualizar();
			
			if (informDeletar.equals('s')) {
				metodosService.deletarAdmOuUser(entidade);
			} 
			else {
				if (usuarioController != null && admController != null) {
					metodosService.lerQuantidadeUserOuAdm(entidade, usuarioController, admController);
				} 
				else {
					System.out.println("Erro: Controladores não inicializados corretamente.");
				}
			}
		
		metodosService.voltarInicio(() -> lerUsuarios(), () -> lerAdministrador());
		} catch (RuntimeException e) {
			throw new DefaultException("Erro na função 'lerFuncionalidadesAdm()' analise um dado por vez! " + e.getLocalizedMessage());
		} 
	}
	
	public static void inform() {
		String entidade = metodosService.procesarEntidade();
		metodosService.exibir(users, adm, entidade);
	}
}