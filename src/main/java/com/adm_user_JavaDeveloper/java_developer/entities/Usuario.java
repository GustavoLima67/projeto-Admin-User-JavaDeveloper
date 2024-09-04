package com.adm_user_JavaDeveloper.java_developer.entities;

import jakarta.persistence.Entity;

@Entity
public class Usuario {
	
	private String email;
	private String name;
	private String senha;
	
	public Usuario() {
	}
	
	public Usuario(String email, String name, String senha) {
		super();
		this.email = email;
		this.name = name;
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	
}
