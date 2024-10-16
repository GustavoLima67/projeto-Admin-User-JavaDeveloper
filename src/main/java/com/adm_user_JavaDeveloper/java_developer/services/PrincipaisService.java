package com.adm_user_JavaDeveloper.java_developer.services;

import java.util.Scanner;

import com.adm_user_JavaDeveloper.java_developer.controller.validators.Validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PrincipaisService {

    private static Scanner sc = new Scanner(System.in);
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String pegarNome() {
        System.out.print("Entre com seu nome de login: ");
        String name = sc.next();
        
        return name;
    }

    public static String pegarTelefone() {
        String phoneNumber = " ";
        boolean validPhone;
        sc.nextLine();
        try {
            do {
                System.out.print("Entre com seu numero de Telefone: ");
                phoneNumber = sc.nextLine();
                
                validPhone = Validation.isValidPhoneNumber(phoneNumber);
                
                if (!validPhone) {
                    System.out.println("Numero de telefone invalido. Tente novamente.");
                }
            } while(!validPhone);
        } catch(IllegalAccessError e) {
            e.printStackTrace();
        }
        return phoneNumber;
    }

    public static String pegarSenha() {
        String userInputsenha;
        boolean senhaValida;
        sc.nextLine();
        do {
            System.out.print("Entre com uma senha valida: ");
            userInputsenha = sc.nextLine();
            
            senhaValida = Validation.validatePassword(userInputsenha);

            if (!senhaValida) {
                Validation.senhaInvalida();
            }
        }  while (!senhaValida);
        System.out.println("Senha valida! acesso concedido!");
        return userInputsenha;
    } 

    public static LocalDate pegarDataNascimento() {
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

}
