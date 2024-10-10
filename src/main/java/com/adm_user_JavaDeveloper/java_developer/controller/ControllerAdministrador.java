package com.adm_user_JavaDeveloper.java_developer.controller;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adm_user_JavaDeveloper.java_developer.entities.Administrador;

@RestController
@RequestMapping
public class ControllerAdministrador {
    

    // CRIANDO API TEST - TESTANDO SE FOI CRIADO UM NOVO ADMINISTRADOR
    public ResponseEntity<Administrador> criarAdministrador(@RequestBody Administrador adm) {
        adm.setId(1L); 
        return ResponseEntity.status(HttpStatus.SC_CREATED).body(adm);
    }
}
