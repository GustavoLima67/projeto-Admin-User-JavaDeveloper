package com.adm_user_JavaDeveloper.java_developer.exceptions;

public class ExceptionDefault extends Exception{
	private static final long serialVersionUID = 1L;
	
	public ExceptionDefault() {
		super("Ocorrou um Erro!");
	}
	
	public ExceptionDefault(String mensagem) {
		super(mensagem);
	}
	public ExceptionDefault(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	public ExceptionDefault(Throwable causa) {
		super(causa);
	}
	
}
