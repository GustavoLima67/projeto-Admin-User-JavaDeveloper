package com.adm_user_JavaDeveloper.java_developer.entities;

import jakarta.persistence.Entity;

@Entity
public class Usuario {
	
	private String phoneNumber;
	private String name;
	private String senha;
	
	public Usuario() {
	}
	
	public Usuario(String name, String phoneNumber, String senha) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.senha = senha;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
		return "Usuario:  nome: " + getName() + ", email: " + getPhoneNumber() + ", senha: " + getSenha();
	}
	
	
	
	
	
}
