package com.senacsp.projetosemestral.bibliotecapessoal.adapter.controller;

import com.senacsp.projetosemestral.bibliotecapessoal.TestcontainersConfiguration;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.UserRequestDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.persistence.repository.mongodb.UserMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = TestcontainersConfiguration.class)
class UserApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserMongoRepository repository;

    private UserRequestDto dto;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        dto = UserRequestDto.builder()
                .cpf("12345678900")
                .firstName("Eduardo")
                .lastName("Lublanski")
                .cep("01001-000")
                .username("eduardo.dev")
                .password("123456")
                .build();
    }

    @Test
    void shouldCreateUser() throws Exception {
        mockMvc.perform(post("/biblioteca-pessoal/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("eduardo.dev"));

        assert repository.count() == 1;
    }

    @Test
    void shouldListUsers() throws Exception {
        mockMvc.perform(post("/biblioteca-pessoal/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        mockMvc.perform(get("/biblioteca-pessoal/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username").value("eduardo.dev"));
    }

    @Test
    void shouldGetUserById() throws Exception {
        mockMvc.perform(post("/biblioteca-pessoal/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        String id = repository.findAll().getFirst().getId();

        mockMvc.perform(get("/biblioteca-pessoal/v1/usuarios/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("eduardo.dev"));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        mockMvc.perform(post("/biblioteca-pessoal/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        String id = repository.findAll().getFirst().getId();

        dto.setFirstName("Carlos");

        mockMvc.perform(put("/biblioteca-pessoal/v1/usuarios/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value("Carlos"));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(post("/biblioteca-pessoal/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        String id = repository.findAll().getFirst().getId();

        mockMvc.perform(delete("/biblioteca-pessoal/v1/usuarios/" + id))
                .andExpect(status().isNoContent());

        assert repository.count() == 0;
    }
}
