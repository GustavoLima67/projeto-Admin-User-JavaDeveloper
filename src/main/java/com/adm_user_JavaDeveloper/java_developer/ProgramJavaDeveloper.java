package com.adm_user_JavaDeveloper.java_developer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.adm_user_JavaDeveloper.java_developer.controller.exceptions.ExececaoPadrao;
import com.adm_user_JavaDeveloper.java_developer.model.Administrador;
import com.adm_user_JavaDeveloper.java_developer.model.Usuarios;
import com.adm_user_JavaDeveloper.java_developer.services.AdministradorServices;
import com.adm_user_JavaDeveloper.java_developer.services.PrincipaisService;
import com.adm_user_JavaDeveloper.java_developer.services.SistemaService;
import com.adm_user_JavaDeveloper.java_developer.services.UsuarioService;

@SpringBootApplication(scanBasePackages = "com.adm_user_JavaDeveloper.java_developer")
@EntityScan(basePackages = "com.adm_user_JavaDeveloper.java_developer.model")
@EnableJpaRepositories(basePackages = "com.adm_user_JavaDeveloper.java_developer.repositories")
public class ProgramJavaDeveloper {
	
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public static Usuarios users = new Usuarios();
	
	public static void main(String[] args) throws Exception{
		SpringApplication.run(ProgramJavaDeveloper.class, args);
		try {
			process();	
		} catch (Exception e) {
			throw new ExececaoPadrao("Error na chamada da função!");
		}
		
	}
	
	public static void process() throws ExececaoPadrao { 
		
		try {
			System.out.println("Bem-vindo");
			SistemaService.logarNoSistema(() -> SistemaService.loginUser(), () -> SistemaService.loginAdm());

		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		
	}
	
	public static void getUser() {
		try {
			String name = PrincipaisService.pegarNome();

			String phoneNumber = PrincipaisService.pegarTelefone();
			
			String userInputsenha = PrincipaisService.pegarSenha();	
		
			LocalDate dataNascimento = PrincipaisService.pegarDataNascimento();

			UsuarioService.executeDbConnection(name, phoneNumber, userInputsenha, dataNascimento);
			System.out.println();	

			users = new Usuarios(name, phoneNumber, userInputsenha, dataNascimento);

			informUser();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void lerFuncionalidades(){
		try {
			String entidade = UsuarioService.procesarEntidade();

			UsuarioService.pegarToString();

			UsuarioService.lerOpcoes();

			UsuarioService.procesarNome(UsuarioService.procesarEntidade());
	
			UsuarioService.procesarTelefone(entidade);

			UsuarioService.procesarSenha(entidade);

			UsuarioService.procesarData(entidade);
			
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	
	public static void processAdm() throws ExececaoPadrao{
		try {
			String name = SistemaService.pegarNomeAdm();

			String passwordAdm = SistemaService.pegarSenhaAdm();

			LocalDate dataNascimento = SistemaService.pegarDataAdm();

           	Administrador adm = new Administrador(name, passwordAdm, dataNascimento);

			informAdm(adm);
            
			lerFuncionalidadesAdm();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void lerFuncionalidadesAdm() throws ExececaoPadrao{
		
		try {
			String entidade = UsuarioService.procesarEntidade();
			
			AdministradorServices.pegarToString();
			AdministradorServices.getUserOuAdm(() ->  AdministradorServices.procesarIqualUsuario(), () -> AdministradorServices.procesarIgualAdm());

			String upName = UsuarioService.procesarNome(entidade);
			String upPassw = UsuarioService.procesarSenha(entidade);
			LocalDate procesDate = UsuarioService.procesarData(entidade);

			AdministradorServices.procesarConnectionSQL(upName, upPassw, procesDate);
			
		}catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
			
		} 
	}
	
	public static void informAdm(Administrador adm) {
		try {
			AdministradorServices.informacoesAdm(adm);
			
		} catch (Exception e) {
			System.err.println("Erro de exibição"+ e.getMessage());
		}
		
	}
	
	public static void informUser() {
		try {
			UsuarioService.Inform(users);
			
		} catch (Exception e) {
			System.err.println("Erro de exibição" + e.getMessage());
		}
	}
}