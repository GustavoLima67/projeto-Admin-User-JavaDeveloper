package com.adm_user_JavaDeveloper.java_developer.controller.validators;


import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class Validation {
    private static final String PASSWORD_PATTERN = 
			 			"^(?=.*[0-9])" +          
			            "(?=.*[a-z])" +           
			            "(?=.*[A-Z])" +           
			            "(?=.*[@#$%^&+=])" +      
			            "(?=\\S+$)" +             
			            ".{8,}$";                 
	
	private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

	private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
	
	public static boolean validarSenha(String password) {
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

	
	public static boolean validarEmail(String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }

	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validarEmailException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
