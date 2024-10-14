package com.adm_user_JavaDeveloper.java_developer.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuarios implements Serializable  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column (name = "Nome", nullable = false)
	private String name;

	@Column (name = "Telefone", nullable = false, unique = true)
	private String telefone;
	
	@Column (name = "Senha",  nullable = false)
	private String senha;

	@Column(name = "Data_Nascimento", nullable = false)
	private LocalDate dataNascimento;
	
	
	public Usuarios() {
	}

	public Usuarios(Integer id, String name, String telefone, String senha, LocalDate dataNascimento) {
		super();
		this.id = id;
		this.name = name;
		this.telefone = telefone;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
	}
	
	public Usuarios(String name, String telefone, String senha, LocalDate dataNascimento) {
		super();
		this.name = name;
		this.telefone = telefone;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelegone(String telefone) {
		this.telefone = telefone;
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
		return "Usuario:  nome: " + getName() + ", numero de telefone: " + getTelefone() + ", senha: " + getSenha() + "data de nascimento: " + getDataNascimento();
	}
}
