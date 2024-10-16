package com.adm_user_JavaDeveloper.java_developer.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "usuarios")
@Table(name = "usuarios")
public class Usuarios implements Serializable  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column (name = "nome", nullable = false)
	private String nome;

	@Column (name = "senha",  nullable = false)
	private String senha;

	@Column (name = "telefone", length = 50)
	private String telefone;
	
	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;
	
	
	public Usuarios() {
	}

	public Usuarios(Integer id, String nome, String telefone, String senha, LocalDate dataNascimento) {
		super();
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
	}
	
	public Usuarios(String nome, String telefone, String senha, LocalDate dataNascimento) {
		super();
		this.nome = nome;
		this.telefone = telefone;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setName(String nome) {
		this.nome = nome;
	}


	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	@Override
	public String toString() {
		return "Usuario:  nome: " + getNome() + ", senha: " + getSenha() + ", numero de telefone: " + getTelefone() + ", data de nascimento: " + getDataNascimento();
	}
}
