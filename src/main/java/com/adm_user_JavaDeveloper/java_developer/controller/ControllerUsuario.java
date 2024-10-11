package com.adm_user_JavaDeveloper.java_developer.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adm_user_JavaDeveloper.java_developer.model.Usuarios;

@RestController
@RequestMapping("/api/usuarios")
public class ControllerUsuario {

    private String dataNascimento = "02/05/1898";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private LocalDate localDate = LocalDate.parse(dataNascimento, formatter);

    @GetMapping("/api/usuario/{id}")
    public ResponseEntity<Usuarios> pegarUserPorId(@RequestBody Long id) {
        return ResponseEntity.ok(new Usuarios(id, "joao", "+5511943546341", "joaoSenha1#", localDate));
        
    }
}
