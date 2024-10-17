package com.adm_user_JavaDeveloper.java_developer.model;

import java.io.Serializable;
import java.time.LocalDate;

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
	
	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;

	public Administrador() {
	}

	public Administrador(Long id, String name,String senha, LocalDate dataNascimento) {
		super();
		this.id = id;
		this.name = name;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
	}
	
	public Administrador(String name,String senha, LocalDate dataNascimento) {
		super();
		this.name = name;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
		throw new UnsupportedOperationException("Método não implementado 'setId'");
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

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Override
	public String toString() {
		return "Administrador: Nome: " + getName() + ", Senha: " + getSenha() + "Data de nascimento: " + getDataNascimento();
	}


	
	
}
