package com.adm_user_JavaDeveloper.java_developer.metodos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.adm_user_JavaDeveloper.java_developer.ProgramJavaDeveloper;
import com.adm_user_JavaDeveloper.java_developer.entities.Administrador;
import com.adm_user_JavaDeveloper.java_developer.entities.Usuario;
import com.adm_user_JavaDeveloper.java_developer.enums.ResponseString;
import com.adm_user_JavaDeveloper.java_developer.exceptions.ExececaoPadrao;

public class AdmFuncionalidades {
 
    
    private static Scanner sc = new Scanner(System.in);

    
	private static Usuario user = new Usuario();
    private static Administrador adm = new Administrador();
    
    private static Connection conn = null;
	private static PreparedStatement st = null;
	private static ResultSet rs = null;


    
    public static void getUserOuAdm(interfaceAcaoLogar userAcao, interfaceAcaoLogar admAcao) throws ExececaoPadrao {
        System.out.print("O que deseja fazer?: (Escreva exatamente como esta abaixo) ");
        System.out.println();
        System.out.println("'User' | 'Adm'");
        System.out.print("Escreva: ");
        String response = sc.next();

        ResponseString userResponse = ResponseString.fromChar(response);

        switch (userResponse) {
            case YES:
                userAcao.executar();
                break;
            case NO:
                admAcao.executar();
                break;
            default:
                throw new ExececaoPadrao("Erro na sintaxe, digite da forma descrita (s/n). ");
        }
        
    }

    public static String processEqualsUser() {

        String func = sc.next();
        if (func.equals("User")) {
            try {
                if(user == null) {
                    System.out.print("Usuario não existe:\nCrie um novo usuario para prosseguir:\n");
                    ProgramJavaDeveloper.loginUser();
                }
                else {
                    ProgramJavaDeveloper.InformUser();
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return func;
    }

    public static String processEqualsAdm() {
        String response = sc.next();

        ResponseString.fromChar(response);

        if (response.equals("Adm")) {
            System.out.print("O que deseja mudar: (nome / senha / dataNascimento) ");
            String mudar = sc.next();
            if (mudar.equals("nome")) {
                FuncionalidadesPrincipais.processName(mudar);
            }
            else if (mudar.equals("senha")) {
                FuncionalidadesPrincipais.processPassword(mudar);
            }
            else if (mudar.equals("dataNascimento")) {
                FuncionalidadesPrincipais.processBirthDate(mudar);
            }
        } 
        return response;
    }

    public static String updateEqualsName(String mudar) {
        mudar = sc.nextLine();
        if (mudar.equals("nome")) {
            System.out.print("Entre com o nome desejado: ");
            String name = sc.next();

            try {
                st.setString(1, name);
                adm.setName(name);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(adm.toString());
        }
        return mudar;
    }

    public static String updateEqualsPassword(String mudar) {
        return FuncionalidadesPrincipais.processPassword(mudar);
    }

}
