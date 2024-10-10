package com.adm_user_JavaDeveloper.java_developer.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.adm_user_JavaDeveloper.java_developer.db.DB;

public class SistemaDoAdmService {
    
    private static Connection conn = null;
	private static PreparedStatement st = null;
	private static ResultSet rs = null;

    public static String pegarNomeAdm() {
        return LoginSistemaService.pegarNome();
    }

    public static String pegarSenhaAdm() {
        return LoginSistemaService.pegarSenha();
    }

    public static LocalDate pegarDataAdm() {
       return LoginSistemaService.pegarDataNascimento();
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