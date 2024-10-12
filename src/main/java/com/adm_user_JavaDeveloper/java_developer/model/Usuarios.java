package com.adm_user_JavaDeveloper.java_developer.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuarios {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (nullable = false)
	private Long id;
	
	@Column (nullable = false)
	private String name;

	@Column (nullable = false)
	private String phoneNumber;
	
	@Column (nullable = false)
	private String senha;
	
	@Column (nullable = false)
	private LocalDate dataNascimento;
	
	
	public Usuarios() {
	}

	public Usuarios(Long id, String name, String phoneNumber, String senha, LocalDate dataNascimento) {
		super();
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
	}
	
	public Usuarios(String name, String phoneNumber, String senha, LocalDate dataNascimento) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
		return "Usuario:  nome: " + getName() + ", numero de telefone: " + getPhoneNumber() + ", senha: " + getSenha() + "data de nascimento: " + getDataNascimento();
	}
	
	
	
	
	
}
