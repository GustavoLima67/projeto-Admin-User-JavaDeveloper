package com.adm_user_JavaDeveloper.java_developer.controller.exceptions;

public class ExececaoPadrao extends Exception{
	private static final long serialVersionUID = 1L;
	
	public ExececaoPadrao() {
		super("Ocorrou um Erro!");
	}
	
	public ExececaoPadrao(String mensagem) {
		super(mensagem);
	}
	public ExececaoPadrao(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	public ExececaoPadrao(Throwable causa) {
		super(causa);
	}
	
}
