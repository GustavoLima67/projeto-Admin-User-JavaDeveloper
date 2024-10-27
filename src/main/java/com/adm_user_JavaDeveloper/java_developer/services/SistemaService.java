package com.adm_user_JavaDeveloper.java_developer.services;

import java.util.Scanner;

import com.adm_user_JavaDeveloper.java_developer.ProgramJavaDeveloper;
import com.adm_user_JavaDeveloper.java_developer.controller.exceptions.ExececaoPadrao;
import com.adm_user_JavaDeveloper.java_developer.repositories.interfaceAcaoRepository;
import com.adm_user_JavaDeveloper.java_developer.services.enums.Response;

public class SistemaService {
    
    private static Scanner sc = new Scanner(System.in);
   
    public static void logarNoSistema(interfaceAcaoRepository usuarioAcao, interfaceAcaoRepository administradorAcao) throws ExececaoPadrao {
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
            System.out.println();
            ProgramJavaDeveloper.getUser();
        } catch(IllegalAccessError e) {
            e.printStackTrace();
        }
    }

    public static void loginAdm() {
        try {
            System.out.println("Bem-vindo! Entre com suas informações para cadastro.");
            System.out.println();
            ProgramJavaDeveloper.processAdm();
        } catch (IllegalAccessError | ExececaoPadrao e) {
            e.getMessage();
            e.printStackTrace();;
        }
    }
}