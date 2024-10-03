package com.adm_user_JavaDeveloper.java_developer.metodos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.adm_user_JavaDeveloper.java_developer.ProgramJavaDeveloper;
import com.adm_user_JavaDeveloper.java_developer.validators.PasswordValidators;

public class LoginAdm {
    
    private static Scanner sc = new Scanner(System.in);
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String getAdmName() {
        System.out.print("Entre com seu nome de login administrador: ");
        String name = sc.next();
        return name;
    }

    public static String getPasswordAdm() {
        boolean passwordValid;
        String userInputPassword;
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
            System.out.print("Entre com sua data de nascimento: ");
            String dataNascimInput = sc.nextLine();

            try {
                dataNascimento = LocalDate.parse(dataNascimInput, formatter);
                
                if (!dataValida) {
                    System.out.println("Data inválida! Tente novamente");
                }
            } catch(DateTimeParseException e) {
                e.printStackTrace();
            }
        } while (!dataValida);

        return dataNascimento;
    }
}
