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
}
