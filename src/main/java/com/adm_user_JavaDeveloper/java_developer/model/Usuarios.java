package com.adm_user_JavaDeveloper.java_developer.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

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

	@Column (name = "email", nullable = false)
	@NotBlank(message = "o email é obrigatório")
	@Email(message = "o email deve ser válido")
	private String email;
	
	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;
	
	public Usuarios() {
	}
	
	public Usuarios(String nome, String email, String senha, LocalDate dataNascimento) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj ) 
			return true;
		if (obj == null) 
			return false;
		if (getClass() != obj.getClass())
			return false;
			Usuarios other = (Usuarios) obj;
		if (id == null) {
			if (other.id != null)	
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario: Id: " + getId() +", Nome: " + getNome() + ", senha: " + getSenha() + ", numero de email: " + getEmail() + ", data de nascimento: " + getDataNascimento();
	}
}
