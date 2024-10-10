package com.adm_user_JavaDeveloper.java_developer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adm_user_JavaDeveloper.java_developer.model.Administrador;

@Repository
public interface AdmRepository extends JpaRepository<Administrador, Long>{
    
}
