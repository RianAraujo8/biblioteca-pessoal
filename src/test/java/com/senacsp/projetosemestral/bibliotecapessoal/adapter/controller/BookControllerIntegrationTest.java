package com.senacsp.projetosemestral.bibliotecapessoal.adapter.controller;


import com.senacsp.projetosemestral.bibliotecapessoal.TestcontainersConfiguration;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.persistence.repository.mongodb.BookMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
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
@Import(TestcontainersConfiguration.class)
class BookApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookMongoRepository repository;

    private BookDto dto;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        dto = BookDto.builder()
                .titulo("The Last Kingdom")
                .autor("Edward Stone")
                .isbn("978-85-0000-000-0")
                .anoPublicacao(2024)
                .genero("Fantasy")
                .quantidadePaginas(512)
                .disponivel(true)
                .build();
    }

    @Test
    void shouldCreateBook() throws Exception {
        mockMvc.perform(post("/biblioteca-pessoal/v1/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("The Last Kingdom"));

        assert repository.count() == 1;
    }

    @Test
    void shouldListBooks() throws Exception {
        mockMvc.perform(post("/biblioteca-pessoal/v1/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        mockMvc.perform(get("/biblioteca-pessoal/v1/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].titulo").value("The Last Kingdom"));
    }

    @Test
    void shouldCreateAndFetchById() throws Exception {
        String response =
                mockMvc.perform(post("/biblioteca-pessoal/v1/livros")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        String id = repository.findAll().getFirst().getId();

        mockMvc.perform(get("/biblioteca-pessoal/v1/livros/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("The Last Kingdom"));
    }

    @Test
    void shouldUpdateBook() throws Exception {
        mockMvc.perform(post("/biblioteca-pessoal/v1/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        String id = repository.findAll().getFirst().getId();

        dto.setTitulo("Updated Book");

        mockMvc.perform(put("/biblioteca-pessoal/v1/livros/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Updated Book"));

        assert repository.findById(id).orElseThrow().getTitle().equals("Updated Book");
    }

    @Test
    void shouldDeleteBook() throws Exception {
        mockMvc.perform(post("/biblioteca-pessoal/v1/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        String id = repository.findAll().getFirst().getId();

        mockMvc.perform(delete("/biblioteca-pessoal/v1/livros/" + id))
                .andExpect(status().isNoContent());

        assert repository.count() == 0;
    }
}

