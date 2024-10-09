package com.adm_user_JavaDeveloper.java_developer.metodos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import com.adm_user_JavaDeveloper.java_developer.ProgramJavaDeveloper;
import com.adm_user_JavaDeveloper.java_developer.db.DB;
import com.adm_user_JavaDeveloper.java_developer.entities.Administrador;
import com.adm_user_JavaDeveloper.java_developer.entities.Usuario;
import com.adm_user_JavaDeveloper.java_developer.exceptions.ExececaoPadrao;

public class AdmFuncionalidades {
 
    
    private static Scanner sc = new Scanner(System.in);
	private static Usuario user = new Usuario();
    private static Administrador adm = new Administrador();
    
    private static Connection conn = null;
	private static PreparedStatement st = null;
	private static ResultSet rs = null;


    public static void pegarToString() {
        System.out.println();
        System.out.println(adm.toString());
        System.out.println();
    }

    public static void getUserOuAdm(interfaceAcaoLogar userAcao, interfaceAcaoLogar admAcao) throws ExececaoPadrao {
        System.out.println("O que deseja fazer?: (Escreva exatamente como esta abaixo) ");
        System.out.println("'User' | 'Adm'");
        System.out.print("Escreva: ");
        String response = sc.nextLine();


        if (response.equals("User")) {
            procesarIqualUsuario();
        }
        if (response.equals("Adm")) {
            procesarIgualAdm();
        }
        
    }

    public static void procesarIqualUsuario() {
        try {
            if(user == null) {
                System.out.print("Usuario não existe:\nCrie um novo usuario para prosseguir:\n");
                ProgramJavaDeveloper.getUser();
            }
            else {
                FuncionalidadesPrincipais.InformUser();
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void procesarIgualAdm() {
        System.out.print("O que deseja mudar: (nome / senha / dataNascimento) ");
        String mudar = sc.nextLine();
        String entidade = FuncionalidadesPrincipais.procesarEntidade();

        switch (mudar.toLowerCase()) {
            case "nome":
                FuncionalidadesPrincipais.procesarNome(entidade);  
                break;
            case "senha":
                FuncionalidadesPrincipais.procesarSenha(entidade); 
                break;
            case "datanascimento":
                FuncionalidadesPrincipais.procesarData(entidade);  
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
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

    public static void informacoesAdm() {
		try {
			System.out.println();
			System.out.println("Exibir Administrador: ");
			System.out.println();
			System.out.println(adm.toString());
			System.out.println();
			System.out.println("Obrigado pelo esforço administrador " +adm.getName() + "!. :)");
			
		} catch (Exception e) {
			System.err.println("Erro em exibir o Adm. " + e.getMessage());
		}
		return;
	}
}
