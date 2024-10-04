package com.adm_user_JavaDeveloper.java_developer.metodos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.function.Function;

import com.adm_user_JavaDeveloper.java_developer.ProgramJavaDeveloper;
import com.adm_user_JavaDeveloper.java_developer.entities.Administrador;
import com.adm_user_JavaDeveloper.java_developer.entities.Usuario;
import com.adm_user_JavaDeveloper.java_developer.enums.Response;
import com.adm_user_JavaDeveloper.java_developer.enums.ResponseString;
import com.adm_user_JavaDeveloper.java_developer.exceptions.ExececaoPadrao;
import com.adm_user_JavaDeveloper.java_developer.validators.PasswordValidators;

public class AdmFuncionalidades {
 
    
    private static Scanner sc = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    
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
                    ProgramJavaDeveloper.UserFunc();
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return func;
    }

    public static String processEqualsAdm(String mudar) {
        String func = sc.next();
        if (func.equals("Adm")) {
            System.out.print("O que deseja mudar: (nome / senha / dataNascimento) ");
            mudar = sc.next();
        } 
        return mudar;
    }

    public static void updateEqualsName(String mudar) {
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
    }

    public static String updateEqualsPassword() {
        String mudar = sc.nextLine();
        if (mudar.equals("senha")) {
            boolean senhaValida;
            String UserInputsenha;
            do {
                System.out.print("Digite sua senha: ");
                UserInputsenha = sc.nextLine();
                
                senhaValida = PasswordValidators.validatePassword(UserInputsenha);

                if (!senhaValida) {
                    ProgramJavaDeveloper.senhaInvalida();
                }
            } while(!senhaValida);

            System.out.println("Senha valida! acesso concedido!");
			try {
                st.setString(2, UserInputsenha);
                adm.setSenha(UserInputsenha);
            } catch (SQLException e) {
                e.printStackTrace();
            }		
            System.out.println(adm.toString());
        }
        return mudar;
    }
    
    public static String updateEqualsBirthDate() {
        String mudar = sc.nextLine();
         if(mudar.equals("dataNascimento")) {
            LocalDate dataNascimento = null;
            boolean dataValida = false;
            do {
                System.out.print("Entre com uma data de nascimento diferente: ");
                String dataNascimInput = sc.nextLine();

                try {
                    dataNascimento = LocalDate.parse(dataNascimInput, formatter);
                    dataValida = true;

                    adm.setDataNascimento(dataNascimento);
                    st.setDate(3, java.sql.Date.valueOf(dataNascimInput));

                } catch(DateTimeParseException | SQLException e) {
                    System.out.println("Data inválida! Tente novamente");
                    e.printStackTrace();
                }
            } while (!dataValida); 
        }
        return mudar;
    }
}
