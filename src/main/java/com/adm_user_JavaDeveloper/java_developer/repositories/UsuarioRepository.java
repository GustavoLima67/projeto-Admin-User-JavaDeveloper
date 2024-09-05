package com.adm_user_JavaDeveloper.java_developer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adm_user_JavaDeveloper.java_developer.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
