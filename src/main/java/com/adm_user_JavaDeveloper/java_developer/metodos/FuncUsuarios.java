package com.adm_user_JavaDeveloper.java_developer.metodos;

import java.util.Scanner;

import com.adm_user_JavaDeveloper.java_developer.ProgramJavaDeveloper;
import com.adm_user_JavaDeveloper.java_developer.validators.PasswordValidators;
import com.adm_user_JavaDeveloper.java_developer.validators.ValidPhoneNumber;

public class FuncUsuarios {

    private static Scanner sc = new Scanner(System.in);
    
    public static String entradaDeNome(String name) {
        System.out.print("Entre com seu nome de Usuário: ");
        name = sc.next();
        
        return name;
    }

    public static String entradaDeNumero(String phoneNumber) {
        
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

    public static String entradaDeSenha(String userInputsenha) {
        boolean senhaValida;

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
}
