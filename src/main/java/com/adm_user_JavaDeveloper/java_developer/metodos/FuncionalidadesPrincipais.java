package com.adm_user_JavaDeveloper.java_developer.metodos;

import java.util.Scanner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.adm_user_JavaDeveloper.java_developer.ProgramJavaDeveloper;
import com.adm_user_JavaDeveloper.java_developer.entities.Usuario;
import com.adm_user_JavaDeveloper.java_developer.exceptions.ExececaoPadrao;
import com.adm_user_JavaDeveloper.java_developer.validators.PasswordValidators;
import com.adm_user_JavaDeveloper.java_developer.validators.ValidPhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class FuncionalidadesPrincipais {
    
    private static Usuario user = new Usuario();
    private static Scanner sc = new Scanner(System.in);

    public static PreparedStatement st = null;
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void pegarToString() {
        System.out.println();
        System.out.println(user.toString());
        System.out.println();
    }

    public static String lerOpcoesUsuario() {
        System.out.print("O que deseja mudar: (nome / telefone / senha / dataNascimento) ");
        String mudar = sc.next();
        return mudar;
    }

    public static String procesarNome(String mudar) {
        mudar = sc.nextLine(); 

        if (mudar.equals("nome")) {
            System.out.print("Entre com o nome desejado: ");
            String name = sc.next();
            
            try {
                st.setString(1, name);
                user.setName(name);
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mudar;
    }

    public static String procesarTelefone(String mudar) throws ExececaoPadrao{
        mudar = sc.nextLine();
        String phoneNumber;
        if (mudar.equals("telefone")) {
            boolean validPhone;
            do {
                System.out.print("Entre com seu numero de Telefone: ");
                phoneNumber = sc.nextLine();
                
                validPhone = ValidPhoneNumber.isValidPhoneNumber(phoneNumber);
                
                if (!validPhone) {
                    System.out.println("Numero de telefone invalido. Tente novamente.");
                }
            } while(!validPhone);
                
            Message message = Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber("+12513021245"), "Numero de Telefone validado no 'Projeto JavaDeveloper' com sucesso!").create();
            System.out.println("Mensagem Enviada com Sucesso!" + message.getSid());

            try {
                user.setPhoneNumber(mudar);
			    st.setString(2, phoneNumber);
                st.executeUpdate();
            }catch (SQLException e) {
                throw new ExececaoPadrao("Erro no cadastro do numero de telefone, tente novamente");
            }
    }
        return mudar;
    }

    public static String procesarSenha(String mudar) {
        mudar = sc.nextLine();
        if (mudar.equals("senha")) { 
            boolean senhaValida;
            String UserInputsenha;
            sc.nextLine();
            do {
                System.out.print("Digite sua senha: ");
                UserInputsenha = sc.nextLine();
                
                senhaValida = PasswordValidators.validatePassword(UserInputsenha);

                if (!senhaValida) {
                    ProgramJavaDeveloper.senhaInvalida();
                }
        } while (!senhaValida);
        System.out.println("Senha valida! acesso concedido!");

        try {
            user.setSenha(UserInputsenha);
            st.setString(3, UserInputsenha);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
        return mudar;
    }

    public static LocalDate procesarData(String mudar) {
        mudar = sc.nextLine();
        boolean dataValida = false;
        LocalDate dataNascimento = null;

        if (mudar.equals("dataNascimento")) {
            if(dataNascimento == null) {
                do {
                    System.out.print("Entre com sua data de nascimento: (dd/MM/yyyy) ");
                    String dataNasciInput = sc.nextLine();
                    try {
                        dataNascimento = LocalDate.parse(dataNasciInput, formatter);
                        dataValida = true;
        
                        user.setDate(dataNascimento);
                        st.setDate(4, java.sql.Date.valueOf(dataNascimento));
                        st.executeUpdate();
                    } catch(DateTimeParseException |  SQLException e) {
                        System.out.println("Data inválida, tente novamente" );
                        e.printStackTrace();
                    }
                   
                } while(!dataValida);
            }
        }
        return dataNascimento;
    }

    public static void InformUser() {
		try {
			System.out.println();
			System.out.println("Exibir Usuário: ");
			System.out.println();
			System.out.println(user.toString());
			System.out.println();
			System.out.println("Obrigador por se cadastrar " + user.getName() + "!. :)");
		} catch (Exception e) {
			System.err.println("Erro em exibir os Usuários. " + e.getMessage());
		}
		return;
	}

}