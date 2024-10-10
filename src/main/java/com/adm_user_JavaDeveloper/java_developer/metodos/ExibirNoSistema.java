package com.adm_user_JavaDeveloper.java_developer.metodos;

import java.util.Scanner;

import com.adm_user_JavaDeveloper.java_developer.ProgramJavaDeveloper;
import com.adm_user_JavaDeveloper.java_developer.enums.Response;
import com.adm_user_JavaDeveloper.java_developer.exceptions.ExececaoPadrao;
import com.adm_user_JavaDeveloper.java_developer.model.Administrador;
import com.adm_user_JavaDeveloper.java_developer.model.Usuario;
import com.adm_user_JavaDeveloper.java_developer.services.AdministradorServices;
import com.adm_user_JavaDeveloper.java_developer.services.UsuarioService;

public class ExibirNoSistema {

    private static Scanner sc = new Scanner(System.in);
    private static Administrador adm = new Administrador();
	private static Usuario user = new Usuario();
    

    public static void exibirAdm() {
        try {
            char response;
			do {
				System.out.println("Bem Vindo "+adm.getName()+"!");
				System.out.println("FUNCIONALIDADES DO ADMINSTRADOR. ");
				System.out.print("Quer mudar suas informações: (y/n) ");
				sc.nextLine();
				response = sc.next().charAt(0);
				
				Response userResponse = Response.fromChar(response);

				switch (userResponse) {
					case YES:
						ProgramJavaDeveloper.pegarFuncionalidadesAdm();
						break;
					case NO:
						AdministradorServices.informacoesAdm();
					default:
						throw new ExececaoPadrao("Erro na sintexe, digite da forma descrita (s / n): ");
				}

			} while(response != 'n');
        } catch (Exception e) {
			System.err.println("Erro de exibição"+ e.getMessage());
		}
    }

    public static void exibirUsuario() {
        try {
			char response;
			do {
				System.out.println("Bem Vindo "+user.getName()+"!");
				System.out.println("FUNCIONALIDADES DO USUARIO. ");
				System.out.print("Quer mudar suas informações: (s/n) ");
				response = sc.next().charAt(0);
				
				Response userResponse = Response.fromChar(response);

				switch (userResponse) {
					case YES:
						ProgramJavaDeveloper.UserFunc();
						break;
					case NO: 
						UsuarioService.InformUser();
						break;
					default:
						throw new ExececaoPadrao("Erro na sintexe!, digite da forma descrita (s/n)");
				}
			} while(response != 'n');
			
			
		} catch (Exception e) {
			System.err.println("Erro de exibição" + e.getMessage());
		}
    }
}
