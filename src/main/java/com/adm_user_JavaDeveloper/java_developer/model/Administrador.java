package com.adm_user_JavaDeveloper.java_developer.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity(name = "administrador")
@Table(name  = "administrador")
public class Administrador implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name  = "nome", nullable = false)
	private String name;
	
	@Column(name = "senha", nullable = false)
	private String senha;

	@Column(name = "email", nullable = false )
	@Email(message = "email deve ser válido")
	@NotBlank(message = "informar seu email é obrigatório")
	private String email;
	
	@Column(name = "cargo")
	private String cargo;

	public Administrador() {
	}

	public Administrador(String name,String senha, String email, String cargo) {
		super();
		this.name = name;
		this.senha = senha;
		this.email = email;
		this.cargo = cargo;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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
			Administrador other = (Administrador) obj;
		if (id == null) {
			if (other.id != null)	
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Administrador: Nome: " + getNome() + ", Senha: " + getSenha() + "Email: " + getEmail() + ", Cargo do administrador: " + getCargo();
	}


	
	
}
