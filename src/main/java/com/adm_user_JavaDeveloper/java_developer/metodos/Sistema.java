package com.adm_user_JavaDeveloper.java_developer.metodos;

import java.util.Scanner;

import com.adm_user_JavaDeveloper.java_developer.ProgramJavaDeveloper;
import com.adm_user_JavaDeveloper.java_developer.enums.Response;
import com.adm_user_JavaDeveloper.java_developer.exceptions.ExececaoPadrao;
import com.adm_user_JavaDeveloper.java_developer.validators.ValidPhoneNumber;

public class Sistema {
    
    private static Scanner sc = new Scanner(System.in);

    public static void logarNoSistema(interfaceAcaoLogar usuarioAcao, interfaceAcaoLogar administradorAcao) throws ExececaoPadrao {
        System.out.print("Você é um usuário?: (s / n) ");
        char response = sc.next().charAt(0);

        Response userResponse = Response.fromChar(response);

        switch (userResponse) {
            case YES:
                usuarioAcao.executar();
                break;
            case NO:
                administradorAcao.executar();
                break;
            default:
                throw new ExececaoPadrao("Erro na sintaxe, digite da forma descrita (s/n). ");
        }
    }

    public static void loginUser() {
        try {
            System.out.println("Bem-vindo! Entre com suas informações para cadastro.");
            ProgramJavaDeveloper.loginUser();
        } catch(IllegalAccessError e) {
            e.printStackTrace();
        }
    }

    public static void loginAdm() {
        try {
            System.out.println("Bem-vindo! Entre com suas informações para cadastro.");
            ProgramJavaDeveloper.loginAdm();
        } catch (IllegalAccessError | ExececaoPadrao e) {
            e.getMessage();
            e.printStackTrace();;
        }
    }

    public static void entradaDeNumero(String name) {
            String phoneNumber = "";
			boolean validPhone;
			System.out.print("Entre com seu nome de Usuário: ");
			name = sc.next();

            sc.nextLine();
			try {
				do {
					System.out.print("Entre com seu numero de Telefone: ");
					phoneNumber = sc.nextLine();
					
					validPhone = ValidPhoneNumber.isValidPhoneNumber(phoneNumber);
					
					if (!validPhone) {
						System.out.println("Numero de telefone invalido. Tente novamente.");
					}

				} while(!validPhone);
            } catch(IllegalAccessError e) {
                e.printStackTrace();
            }
        }
}
