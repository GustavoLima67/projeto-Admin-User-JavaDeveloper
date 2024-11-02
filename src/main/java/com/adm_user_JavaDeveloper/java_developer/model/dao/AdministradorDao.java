package com.adm_user_JavaDeveloper.java_developer.model.dao;

import java.util.List;

import com.adm_user_JavaDeveloper.java_developer.model.Administrador;

public interface AdministradorDao {
    
    void insert(Administrador administrador);
    void updtade(Administrador administrador);
    void deleteById(Integer id);
    Administrador findById(Integer id);
    List<Administrador> findAll();
}
