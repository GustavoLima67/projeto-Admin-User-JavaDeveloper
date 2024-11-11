package com.adm_user_JavaDeveloper.java_developer.services.mesage;

import com.adm_user_JavaDeveloper.java_developer.controller.exceptions.DefaultException;
import com.adm_user_JavaDeveloper.java_developer.controller.security.TwilioAuthentication;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;



public class SmsMesage {


	public static final String accountSid = TwilioAuthentication.getAccountSid();
    public static final String accountAuthToken = TwilioAuthentication.getAuthToken();

    public static void sendSms(String phoneNumber) throws DefaultException {
	 Twilio.init(accountSid, accountAuthToken);
	 try {

	   	 Message message = Message.creator(
	                new PhoneNumber(phoneNumber), 
	                new PhoneNumber("+12513021245"),    
	                "Seu número foi cadastrado no 'Projeto JavaDeveloper Adm / User' com sucesso!")
	            .create();

	        System.out.println("Mensagem enviada com sucesso: " + message.getSid());
 
	 	} catch (Exception e) {
	 		throw new DefaultException("Erro na no envio da mensagem: " + e.getMessage());
	 	}
	 
	}
}
