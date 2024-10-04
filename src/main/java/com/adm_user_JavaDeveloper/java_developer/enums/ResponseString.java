package com.adm_user_JavaDeveloper.java_developer.enums;

public enum ResponseString {
    
    YES("User"),
    NO("Adm");

    private final String response;

    ResponseString(String response) {
        this.response = response;
    }

    public String getResponseString() {
        return response;
    }

    public static ResponseString fromChar(String response) {
        for(ResponseString ur : values() ) {
            if(ur.getResponseString().equals(response)) {
                return ur;
            }
        }
        throw new IllegalArgumentException("Resposta inválida " + response);
    }

}
