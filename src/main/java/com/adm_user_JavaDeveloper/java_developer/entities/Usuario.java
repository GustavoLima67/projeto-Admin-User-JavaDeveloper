package com.adm_user_JavaDeveloper.java_developer.entities;

import jakarta.persistence.Entity;

@Entity
public class Usuario {
	
	private String email;
	private String name;
	private String senha;
	
	public Usuario() {
	}
	
	public Usuario(String name, String email, String senha) {
		super();
		this.name = name;
		this.email = email;
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

	@Override
	public String toString() {
		return "Usuario:  nome: " + getName() + ", email: " + getEmail() + ", senha: " + getSenha();
	}
	
	
	
	
	
}
