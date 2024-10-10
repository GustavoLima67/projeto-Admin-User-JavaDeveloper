package com.adm_user_JavaDeveloper.java_developer.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adm_user_JavaDeveloper.java_developer.entities.Usuario;

@RestController
@RequestMapping("/api/usuario")
public class ControllerUsuario {

    String dataTes = "22/03/1994";

    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    LocalDate localDate = LocalDate.parse(dataTes, dtf);

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> pegarUserPorId(@RequestBody Long id) {
        Usuario user = new Usuario(id, "Liminha", "+5532933556523", "sapoDoido1#", localDate);

        return ResponseEntity.ok(user);
        
    }
}
