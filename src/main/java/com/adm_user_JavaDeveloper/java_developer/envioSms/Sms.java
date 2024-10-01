package com.adm_user_JavaDeveloper.java_developer.envioSms;

import com.adm_user_JavaDeveloper.java_developer.authenticator.TwilioAuthentication;
import com.adm_user_JavaDeveloper.java_developer.exceptions.ExececaoPadrao;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;



public class Sms {


	public static final String accountSid = TwilioAuthentication.getAccountSid();
    public static final String accountAuthToken = TwilioAuthentication.getAuthToken();

    public static void sendSms(String phoneNumber) throws ExececaoPadrao {
	 Twilio.init(accountSid, accountAuthToken);
	 try {

	   	 Message message = Message.creator(
	                new PhoneNumber(phoneNumber), 
	                new PhoneNumber("+12513021245"),    
	                "Seu número foi cadastrado no 'Projeto JavaDeveloper Adm / User' com sucesso!")
	            .create();

	        System.out.println("Mensagem enviada com sucesso: " + message.getSid());
 
	 	} catch (Exception e) {
	 		throw new ExececaoPadrao("Erro na no envio da mensagem: " + e.getMessage());
	 	}
	 
	}
}
