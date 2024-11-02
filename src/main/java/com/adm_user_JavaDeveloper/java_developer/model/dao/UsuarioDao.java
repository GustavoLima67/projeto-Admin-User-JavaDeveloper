package com.adm_user_JavaDeveloper.java_developer.model.dao;

import java.util.List;

import com.adm_user_JavaDeveloper.java_developer.model.Usuarios;

public interface UsuarioDao {
    

    void insert(Usuarios usuarios);
    void updtade(Usuarios usuarios);
    void deleteById(Integer id);
    Usuarios findById(Integer id);
    List<Usuarios> findAll();
}
