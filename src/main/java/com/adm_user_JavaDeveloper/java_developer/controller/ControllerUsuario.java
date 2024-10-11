package com.adm_user_JavaDeveloper.java_developer.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adm_user_JavaDeveloper.java_developer.model.Usuarios;
import com.adm_user_JavaDeveloper.java_developer.repositories.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class ControllerUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Usuarios> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> getClienteById(@PathVariable Long id) {
        Optional<Usuarios> users = usuarioRepository.findById(id);
        return users.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
