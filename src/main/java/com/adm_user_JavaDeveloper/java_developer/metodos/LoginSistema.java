package com.adm_user_JavaDeveloper.java_developer.metodos;

import java.util.Scanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.adm_user_JavaDeveloper.java_developer.ProgramJavaDeveloper;
import com.adm_user_JavaDeveloper.java_developer.db.DB;
import com.adm_user_JavaDeveloper.java_developer.validators.PasswordValidators;
import com.adm_user_JavaDeveloper.java_developer.validators.ValidPhoneNumber;

public class LoginSistema {

    private static Scanner sc = new Scanner(System.in);
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static Connection conn = null;
	public static PreparedStatement st = null;
	public static ResultSet rs = null;
	
    
    public static String getName() {
        System.out.print("Entre com seu nome de login: ");
        String name = sc.next();
        
        return name;
    }

    public static String getPhone() {
        String phoneNumber = " ";
        boolean validPhone;
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
        return phoneNumber;
    }

    public static String getPassword() {
        String userInputsenha;
        boolean senhaValida;
        sc.nextLine();
        do {
            System.out.print("Entre com uma senha valida: ");
            userInputsenha = sc.nextLine();
            
            senhaValida = PasswordValidators.validatePassword(userInputsenha);

            if (!senhaValida) {
                ProgramJavaDeveloper.senhaInvalida();
            }
        }  while (!senhaValida);
        System.out.println("Senha valida! acesso concedido!");
        return userInputsenha;
    } 

    public static LocalDate getBirthDate() {
        LocalDate dataNascimento = null;
        boolean dataValida = false;
		
        if(dataNascimento == null) {
            do {
                System.out.print("Entre com sua data de nascimento: (dd/MM/yyyy) ");
                String dataNasciInput = sc.nextLine();
                try {
                    dataNascimento = LocalDate.parse(dataNasciInput, formatter);
                    dataValida = true;

                } catch(DateTimeParseException e) {
                    System.out.println("Data inválida, tente novamente" );
                    e.printStackTrace();
                }
            } while(!dataValida);
        }
        return dataNascimento;
    }

    public static Connection executeDbConnection(String name, String userInputsenha, String phoneNumber, LocalDate dataNascimento) {
        conn = DB.getConnection(); 
        try {
            st = conn.prepareStatement("INSERT INTO usuario (Nome, Senha, Telefone, DataNascimento)" + " VALUES" + " (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, name);
            st.setString(2, userInputsenha);
            st.setString(3, phoneNumber);
            st.setDate(4, java.sql.Date.valueOf(dataNascimento)); 

            int linhasAfetadas = st.executeUpdate();
        
            if (linhasAfetadas > 0) {
                rs = st.getGeneratedKeys();
                while(rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Usuario inserido com sucesso! Id: " + id);
                }
            } else {
                System.out.println("Sem nenhuma linha afetada");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
            DB.closeConnection();
        }
        
        return conn; 
    }
}
