package com.adm_user_JavaDeveloper.java_developer.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "administrador")
@Table(name  = "administrador")
public class Administrador implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name  = "nome", nullable = false)
	private String name;
	
	@Column(name = "senha", nullable = false)
	private String senha;

	@Column(name = "email", nullable = false )
	private String email;
	
	@Column(name = "cargo")
	private String cargo;

	public Administrador() {
	}

	public Administrador(Long id, String name,String senha, String email, String cargo) {
		super();
		this.id = id;
		this.name = name;
		this.senha = senha;
		this.email = email;
		this.cargo = cargo;
	}
	
	public Administrador(String name,String senha, String email, String cargo) {
		super();
		this.name = name;
		this.senha = senha;
		this.email = email;
		this.cargo = cargo;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
		throw new UnsupportedOperationException("Método não implementado 'setId'");
	}
	
	public String getNome() {
		return name;
	}

	public void setNome(String name) {
		this.name = name;
	}

	public String getSenha() {
		return senha;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	} 
	
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCargo() {
		return cargo;
	}

	public void setcargo(String cargo) {
		this.cargo = cargo;
	}

	@Override
	public String toString() {
		return "Administrador: Nome: " + getNome() + ", Senha: " + getSenha() + "Email: " + getEmail() + "Cargo do administrador: " + getCargo();
	}


	
	
}
