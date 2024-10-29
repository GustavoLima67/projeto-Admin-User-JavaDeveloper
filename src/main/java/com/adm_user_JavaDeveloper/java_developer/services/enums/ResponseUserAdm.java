package com.adm_user_JavaDeveloper.java_developer.services.enums;

public enum ResponseUserAdm {
    USER('u'),
    ADM('a');

    private final char responseUserAdm;

    ResponseUserAdm(char responseUserAdm) {
        this.responseUserAdm = responseUserAdm;
    }

    public char getResponse() {
        return responseUserAdm;
    }

    public static ResponseUserAdm fromChar(char responseUserAdm) {
        for(ResponseUserAdm ur : values() ) {
            if(ur.getResponse() == responseUserAdm) {
                return ur;
            }
        }
        throw new IllegalArgumentException("Resposta inválida" + responseUserAdm);
    }
}