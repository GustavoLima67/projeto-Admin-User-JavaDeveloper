package com.adm_user_JavaDeveloper.java_developer.services.enums;

public enum Response {
    
    YES('s'),
    NO('n');

    private final char response;

    Response(char response) {
        this.response = response;
    }

    public char getResponse() {
        return response;
    }

    public static Response fromChar(char response) {
        for(Response ur : values() ) {
            if(ur.getResponse() == response) {
                return ur;
            }
        }
        throw new IllegalArgumentException("Resposta inválida" + response);
    }

}
