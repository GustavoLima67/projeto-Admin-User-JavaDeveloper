package com.adm_user_JavaDeveloper.java_developer.controller;

import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adm_user_JavaDeveloper.java_developer.model.Administrador;
import com.adm_user_JavaDeveloper.java_developer.repositories.AdmRepository;

import jakarta.validation.Valid;

@Controller
@RestController
@RequestMapping("/api/moderadores")
public class AdmController {
  
    private AdmRepository admRepository;

    @Autowired
    public AdmController(AdmRepository admRepository) {
        this.admRepository = admRepository;
    }

    // CRIANDO API TEST - TESTANDO SE FOI CRIADO UM NOVO ADMINISTRADOR
    public ResponseEntity<Administrador> criarAdministrador(@RequestBody Administrador adm) {
        adm.setId(1); 
        return ResponseEntity.status(HttpStatus.SC_CREATED).body(adm);
    }

    // CRIANDO API TEST - PEGANDO TODOS OS ADMINISTRADOR CADASTRADOS
    @GetMapping
    public List<Administrador> pegarTodosAdm() {
        return admRepository.findAll();
    }

     @PostMapping("/cadastrar/moderador")
    public ResponseEntity<String> cadastrarAdministradores(@Valid @RequestBody Administrador administrador) {
        return ResponseEntity.status(HttpStatus.SC_CREATED)
                .body("Moderador cadastrado com sucesso!");
    }
    
}
