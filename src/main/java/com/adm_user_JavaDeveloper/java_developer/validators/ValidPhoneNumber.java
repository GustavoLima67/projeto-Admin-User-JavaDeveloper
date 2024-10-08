package com.adm_user_JavaDeveloper.java_developer.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidPhoneNumber {
    
    public static boolean isValidPhoneNumber(String phoneNumber) {
	    String regex = "^\\+?[1-9]{1}[0-9]{1,3}[1-9]{1}[0-9]{1,4}[0-9]{4,5}[0-9]{4}$";
	    
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(phoneNumber);
		
		return matcher.matches();
	}
	
}
