package com.adm_user_JavaDeveloper.java_developer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adm_user_JavaDeveloper.java_developer.model.Usuarios;
import com.adm_user_JavaDeveloper.java_developer.repositories.UsuarioRepository;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Usuarios> pegarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public Usuarios createUsuarios(@RequestBody Usuarios users) {
        return usuarioRepository.save(users);
    }
}
