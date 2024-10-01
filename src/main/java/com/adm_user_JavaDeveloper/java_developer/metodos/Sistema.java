package com.adm_user_JavaDeveloper.java_developer.metodos;

import java.util.Scanner;

import com.adm_user_JavaDeveloper.java_developer.enums.Response;
import com.adm_user_JavaDeveloper.java_developer.exceptions.ExececaoPadrao;

@FunctionalInterface interface acaoLogar {
        void executar();
    }

public class Sistema {
    
    
    private static Scanner sc = new Scanner (System.in);

    public static void logarNoSistema(acaoLogar usuarioAcao, acaoLogar administradorAcao) throws ExececaoPadrao{
        System.out.println("Você é um usuário?: (s / n) ");
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
        System.out.println("Bem vindo! entre com suas informações para cadastro.");
    }

    public static void loginAdm() {
        System.out.println("Bem vindo! entre com suas informações para cadastro.");
    }
}

