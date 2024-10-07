package com.adm_user_JavaDeveloper.java_developer.metodos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.query.IllegalSelectQueryException;

import com.adm_user_JavaDeveloper.java_developer.ProgramJavaDeveloper;
import com.adm_user_JavaDeveloper.java_developer.db.DB;
import com.adm_user_JavaDeveloper.java_developer.entities.Administrador;
import com.adm_user_JavaDeveloper.java_developer.entities.Usuario;
import com.adm_user_JavaDeveloper.java_developer.exceptions.ExececaoPadrao;
import com.twilio.rest.api.v2010.account.availablephonenumbercountry.Local;

public class AdmFuncionalidades {
 
    
    private static Scanner sc = new Scanner(System.in);
	private static Usuario user = new Usuario();
    private static Administrador adm = new Administrador();
    
    private static Connection conn = null;
	private static PreparedStatement st = null;
	private static ResultSet rs = null;


    public static void getUserOuAdm(interfaceAcaoLogar userAcao, interfaceAcaoLogar admAcao) throws ExececaoPadrao {
        System.out.println("O que deseja fazer?: (Escreva exatamente como esta abaixo) ");
        System.out.println("'User' | 'Adm'");
        System.out.print("Escreva: ");
        String response = sc.nextLine();
        if (response.equals("User")) {
            procesarIqualUsuario(response);
        }
        if (response.equals("Adm")) {
            procesarIgualAdm(response);
        }
        
    }

    public static String procesarIqualUsuario(String response) {
        response = sc.next();

        if (response.equals("User")) {
            try {
                if(user == null) {
                    System.out.print("Usuario não existe:\nCrie um novo usuario para prosseguir:\n");
                    ProgramJavaDeveloper.getUser();
                }
                else {
                    ProgramJavaDeveloper.InformUser();
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return response;
    }

    public static String procesarIgualAdm(String response) {
        if (response.equals("Adm")) {
            System.out.print("O que deseja mudar: (nome / senha / dataNascimento) ");
            String mudar = sc.nextLine();
            if (mudar.equals("nome")) {
                updateEqualsName(mudar);
            }
            else if (mudar.equals("senha")) {
                FuncionalidadesPrincipais.procesarSenha(mudar);
            }
            else if (mudar.equals("dataNascimento")) {
                FuncionalidadesPrincipais.procesarData(mudar);
            }
        } 
        return response;
    }

    public static String atualizarIgualNome(String mudar) {
        mudar = sc.nextLine();
        try {
            if (mudar.equals("nome")) {
                System.out.print("Entre com o nome desejado: ");
                String name = sc.next();
                
                st.setString(1, name);
                adm.setName(name);

                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(adm.toString());
      
        return mudar;
    }

    public static String atualizarIgualSenha(String mudar) {
        return FuncionalidadesPrincipais.procesarSenha(mudar);
    }

    public static Connection procesarConnectionSQL(String upName, String upPassw, LocalDate procesDate){
        try {
            conn = DB.getConnection();

            st = conn.prepareStatement("UPDATE administrador SET Name = ?, Password = ?,  BirthDate = ? WHERE id = (SELECT MAX(id) FROM administrador) ");
			st.setString(1, upName);
			st.setString(2, upPassw);
			st.setDate(3, java.sql.Date.valueOf(procesDate));

            int linhasAtualizadas = st.executeUpdate();
            if (linhasAtualizadas > 0) {
                System.out.println("Tabela atualizada com sucesso");
            }
            else {
                System.out.println("Nenhuma linha afetada");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DB.closeResultSet(rs);
            DB.closeResultSet(rs);
            DB.closeConnection();
        }
        return conn;
    }


}
