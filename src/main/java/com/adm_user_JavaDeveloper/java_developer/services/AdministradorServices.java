package com.adm_user_JavaDeveloper.java_developer.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import org.springframework.stereotype.Repository;

import com.adm_user_JavaDeveloper.java_developer.ProgramJavaDeveloper;
import com.adm_user_JavaDeveloper.java_developer.controller.exceptions.ExececaoPadrao;
import com.adm_user_JavaDeveloper.java_developer.controller.security.Senha;
import com.adm_user_JavaDeveloper.java_developer.db.DB;
import com.adm_user_JavaDeveloper.java_developer.model.Administrador;
import com.adm_user_JavaDeveloper.java_developer.model.Usuarios;
import com.adm_user_JavaDeveloper.java_developer.repositories.interfaceAcaoRepository;
import com.adm_user_JavaDeveloper.java_developer.services.enums.Response;


@Repository
public class AdministradorServices{

    private static Scanner sc = new Scanner(System.in);
    private static Usuarios user = new Usuarios();
    private static Administrador adm = new Administrador();
    
    private static Connection conn = null ;
    private static PreparedStatement st = null;
    private static ResultSet rs = null;


    public static void pegarToString() {
        System.out.println();
        System.out.println(adm.toString());
        System.out.println();
    }

    public static void getUserOuAdm(interfaceAcaoRepository userAcao, interfaceAcaoRepository admAcao) throws ExececaoPadrao {
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
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void procesarIgualAdm() {
        System.out.print("O que deseja mudar: (nome / senha / dataNascimento) ");
        String mudar = sc.nextLine();
        String entidade = UsuarioService.procesarEntidade();

        switch (mudar.toLowerCase()) {
            case "nome":
                UsuarioService.procesarNome(entidade);  
                break;
            case "senha":
                UsuarioService.procesarSenha(entidade); 
                break;
            case "datanascimento":
                UsuarioService.procesarData(entidade);  
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
    }

    public static Connection procesarConnectionSQL(String upName, String upPassw, LocalDate procesDate){
        try {
            conn = DB.getConnection();

            st = conn.prepareStatement("UPDATE administrador SET nome = ?, senha = ?,  data_nascimento = ? WHERE id = (SELECT MAX(id) FROM administrador) ");
            st.setString(1, upName);
            st.setString(2, Senha.hashSenha(upPassw));
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

    public static void informacoesAdm(Administrador adm) {
        try {
            System.out.println();
            System.out.println("Exibir Administrador: ");
            System.out.println();
            System.out.println(adm.toString());
            System.out.println();
            System.out.println("Obrigado pelo dia administrador " +adm.getName() + "!. :)");
            
        } catch (Exception e) {
            System.err.println("Erro em exibir o Adm. " + e.getMessage());
        }
    }

    public static void exibirAdm(Administrador adm) {
        try {
            char response;
            do {
                System.out.println("Bem Vindo "+adm.getName()+"!");
                System.out.println("FUNCIONALIDADES DO ADMINSTRADOR. ");
                System.out.print("Quer mudar suas informações: (y/n) ");
                sc.nextLine();
                response = sc.next().charAt(0);
                
                Response userResponse = Response.fromChar(response);

                switch (userResponse) {
                    case YES:
                        ProgramJavaDeveloper.lerFuncionalidadesAdm();
                        break;
                    case NO:
                        AdministradorServices.informacoesAdm(adm);
                    default:
                        throw new ExececaoPadrao("Erro na sintexe, digite da forma descrita (s / n): ");
                }

            } while(response != 'n');
        } catch (Exception e) {
            System.err.println("Erro de exibição"+ e.getMessage());
        }
    }
}

