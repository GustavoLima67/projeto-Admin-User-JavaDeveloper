package com.adm_user_JavaDeveloper.java_developer.controller;

import com.adm_user_JavaDeveloper.java_developer.model.Usuario;
import com.adm_user_JavaDeveloper.java_developer.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ControllerUsuario.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    public void testGetClienteById() throws Exception {
        // Simulação do objeto Usuario
        Usuario usuario = new Usuario(1, "João", "joao@example.com");

        // Mock do serviço
        BDDMockito.given(usuarioService.getUsuarioById(1)).willReturn(usuario);

        // Executando o teste
        mockMvc.perform(get("/api/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.email").value("joao@example.com"));
    }
}
