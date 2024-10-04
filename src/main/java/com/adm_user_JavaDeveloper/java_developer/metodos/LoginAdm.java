package com.adm_user_JavaDeveloper.java_developer.metodos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.adm_user_JavaDeveloper.java_developer.ProgramJavaDeveloper;
import com.adm_user_JavaDeveloper.java_developer.db.DB;
import com.adm_user_JavaDeveloper.java_developer.validators.PasswordValidators;

public class LoginAdm {
    
    private static Scanner sc = new Scanner(System.in);
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static Connection conn = null;
	private static PreparedStatement st = null;
	private static ResultSet rs = null;

    public static String getAdmName() {
        System.out.print("Entre com seu nome de login administrador: ");
        String name = sc.next();
        return name;
    }

    public static String getPasswordAdm() {
        boolean passwordValid;
        String userInputPassword;
        sc.nextLine();
        
        do {
            System.out.print("Entre com uma senha valida: ");
            userInputPassword = sc.nextLine();
            
            passwordValid = PasswordValidators.validatePassword(userInputPassword);

            if (!passwordValid) {
                ProgramJavaDeveloper.senhaInvalida();
            }
			}  while (!passwordValid);

        return userInputPassword;
    }

    public static LocalDate getDateAdm() {
        LocalDate dataNascimento = null;
        boolean dataValida = false;
        do {
            System.out.print("Entre com sua data de nascimento: (dd/MM/yyyy) ");
            String dataNascimInput = sc.nextLine();

            try {
                dataNascimento = LocalDate.parse(dataNascimInput, formatter);
                dataValida = true;

            } catch(DateTimeParseException e) {
                System.out.println("Data inválida! Tente novamente");
                e.printStackTrace();
            }
        } while (!dataValida);

        return dataNascimento;
    }

    public static Connection executeDbConnection(String name, String passwordAdm, LocalDate dataNascimento) {
        try { 
            conn = DB.getConnection();

            st = conn.prepareStatement("INSERT INTO administrador (Nome, Senha, DataNascimento) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, name);
            st.setString(2, passwordAdm);
            st.setDate(3, java.sql.Date.valueOf(dataNascimento));

            int linhasAfetadas = st.executeUpdate();

            if(linhasAfetadas > 0) {
                rs = st.getGeneratedKeys();
                while (rs.next()) {
                    int idAdm = rs.getInt(1);
                    System.out.println("Administrador inserido com sucesso! Id: " + idAdm);
                }
            } else {
                System.out.println("Erro ao inserir administrador");
            }


        } catch (SQLException e) {
            System.out.println("Erro na entrada de valores SQL" + e.getMessage());
            e.printStackTrace();
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
            DB.closeConnection();
        }
        return conn;
    }
}
