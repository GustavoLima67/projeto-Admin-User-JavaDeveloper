package com.adm_user_JavaDeveloper.java_developer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adm_user_JavaDeveloper.java_developer.model.Usuarios;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Integer> {
}
