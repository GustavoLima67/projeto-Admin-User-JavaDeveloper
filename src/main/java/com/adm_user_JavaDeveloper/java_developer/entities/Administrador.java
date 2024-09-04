package com.adm_user_JavaDeveloper.java_developer.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Administrador {
	Usuario user;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String senha;
	
	public Administrador() {
	}
	
	public Administrador(Long id, String name,String senha) {
		super();
		this.id = id;
		this.name = name;
		this.senha = senha;
	}
	
	public Long getId() {
		return id;
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

	public Usuario getUser() {
		return user;
	}
	
	public void setUser(Usuario user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Administrador: Nome: " + name + ", Id: " + id + ", Senha: " + senha;
	}
	

	
	
}
