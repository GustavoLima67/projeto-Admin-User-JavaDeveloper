package com.adm_user_JavaDeveloper.java_developer.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    

    @Value("${spring.mail.username}")
    private String remetente;

    public String enviarMensagemEmail(String destinatario, String assunto, String mensagem) {
        try {
            SimpleMailMessage sMM = new SimpleMailMessage();
            sMM.setFrom(remetente);
            sMM.setTo(destinatario);
            sMM.setSubject(assunto);
            sMM.setText(mensagem);
            return "Email enviado com sucesso!.";

        } catch (Exception e) {
            return "Erro ao enviar email" + e.getLocalizedMessage();
        }
    }
}
