package com.adm_user_JavaDeveloper.java_developer.validators;
import java.util.regex.Pattern;


public class PasswordValidators {
	private static final String PASSWORD_PATTERN = 
			 			"^(?=.*[0-9])" +          
			            "(?=.*[a-z])" +           
			            "(?=.*[A-Z])" +           
			            "(?=.*[@#$%^&+=])" +      
			            "(?=\\S+$)" +             
			            ".{8,}$";                 
	
	private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
	
	public static boolean validatePassword(String password) {
		return pattern.matcher(password).matches();
	}

	public static void senhaInvalida() {
		System.out.println("Senha Invalida!!");
		System.out.println("Sua senha deve conter: ");
		System.out.println("1. Deve ter no mínimo 8 caracteres");
		System.out.println("2. Deve conter pelo menos uma letra maiúscula.");
		System.out.println("3. Deve conter pelo menos uma letra minúscula.");
		System.out.println("4. Deve conter pelo menos um número.");
		System.out.println("5. Deve conter pelo menos um caractere especial (por exemplo: @, #, !, etc.).");
	}
}
