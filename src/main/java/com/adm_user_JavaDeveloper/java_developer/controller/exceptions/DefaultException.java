package com.adm_user_JavaDeveloper.java_developer.controller.exceptions;

public class DefaultException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DefaultException() {
		super("Ocorrou um Erro!");
	}
	
	public DefaultException(String mensagem) {
		super(mensagem);
	}
	public DefaultException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	public DefaultException(Throwable causa) {
		super(causa);
	}
	
}
