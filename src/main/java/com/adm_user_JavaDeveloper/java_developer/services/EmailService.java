package com.adm_user_JavaDeveloper.java_developer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.adm_user_JavaDeveloper.java_developer.model.Email;

@Service
public class EmailService {
    

    @Value("${spring.mail.username}")
    private String remetente;

    @Autowired
    private JavaMailSender mailSender;

    public String enviarMensagemEmail(Email email) {
        try {
            SimpleMailMessage sMM = new SimpleMailMessage();
            sMM.setFrom(remetente);
            sMM.setTo(email.to());
            sMM.setSubject(email.subject());
            sMM.setText(email.body());
            mailSender.send(sMM);
            return "Email enviado com sucesso!.";

        } catch (Exception e) {
            return "Erro ao enviar email" + e.getLocalizedMessage();
        }
    }
}
