package com.adm_user_JavaDeveloper.java_developer.metodos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.adm_user_JavaDeveloper.java_developer.ProgramJavaDeveloper;
import com.adm_user_JavaDeveloper.java_developer.entities.Usuario;

public class AdmFuncionalidades {
 
    
    private static Scanner sc = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    
	public static Usuario user = new Usuario();

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

}
