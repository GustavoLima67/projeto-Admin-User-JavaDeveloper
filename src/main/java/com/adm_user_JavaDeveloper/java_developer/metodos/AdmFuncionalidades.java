package com.adm_user_JavaDeveloper.java_developer.metodos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.adm_user_JavaDeveloper.java_developer.ProgramJavaDeveloper;
import com.adm_user_JavaDeveloper.java_developer.entities.Administrador;
import com.adm_user_JavaDeveloper.java_developer.entities.Usuario;

public class AdmFuncionalidades {
 
    
    private static Scanner sc = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    
	private static Usuario user = new Usuario();
    private static Administrador adm = new Administrador();
    
    private static Connection conn = null;
	private static PreparedStatement st = null;
	private static ResultSet rs = null;

    public static String getAdmFuncionalidades() {
        System.out.print("O que deseja fazer?: (Escreva exatamente como esta abaixo) ");
        System.out.println();
        System.out.print("User | Adm");
        String func = sc.nextLine();
        return func;
    }

    public static String processEqualsUser(String func) {
        if (func.equals("User")) {
            try {
                if(user.getName() == null && user.getPhoneNumber() == null && user.getSenha() == null) {
                    System.out.print("Usuario não existe:\nCrie um novo usuario para prosseguir:\n");
                    ProgramJavaDeveloper.loginUser();
                }
                else {
                    ProgramJavaDeveloper.UserFunc();
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return func;
    }

    public static void processEqualsAdm(String mudar) {
        String func = sc.nextLine();
        if (func.equals("Adm")) {
            System.out.println(adm.toString());
            System.out.print("O que deseja mudar: (nome / senha) ");
            mudar = sc.next();
        } 
    }

    public static void updateEqualsName(String mudar) {
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
    }
}
