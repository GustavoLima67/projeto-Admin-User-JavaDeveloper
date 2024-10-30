package com.adm_user_JavaDeveloper.java_developer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adm_user_JavaDeveloper.java_developer.model.Usuarios;
import com.adm_user_JavaDeveloper.java_developer.repositories.UsuarioRepository;

import jakarta.validation.Valid;

@Controller
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<Usuarios> pegarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    @PostMapping("/cadastrar/usuarios")
    public ResponseEntity<String> cadastrarUsuario(@Valid @RequestBody Usuarios usuario) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuário cadastrado com sucesso!");
    }
}
