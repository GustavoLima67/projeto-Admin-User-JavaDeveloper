package com.adm_user_JavaDeveloper.java_developer.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Senha {
    
    private static final BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();

    public static String hashSenha(String senha) {
        return enconder.encode(senha);
    }

}
