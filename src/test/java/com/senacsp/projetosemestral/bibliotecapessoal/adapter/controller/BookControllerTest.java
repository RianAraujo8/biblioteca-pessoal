package com.senacsp.projetosemestral.bibliotecapessoal.adapter.controller;


import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookDto;
import com.senacsp.projetosemestral.bibliotecapessoal.aplication.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookService bookService;

    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        bookDto = BookDto.builder()
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
    void shouldRegisterBook() throws Exception {
        when(bookService.register(any(BookDto.class)))
                .thenReturn(bookDto);

        mockMvc.perform(post("/biblioteca-pessoal/v1/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.titulo").value("The Last Kingdom"))
                .andExpect(jsonPath("$.autor").value("Edward Stone"))
                .andExpect(jsonPath("$.isbn").value("978-85-0000-000-0"))
                .andExpect(jsonPath("$.anoPublicacao").value(2024))
                .andExpect(jsonPath("$.genero").value("Fantasy"))
                .andExpect(jsonPath("$.quantidadePaginas").value(512))
                .andExpect(jsonPath("$.disponivel").value(true));

        verify(bookService).register(any(BookDto.class));
    }

    @Test
    void shouldReturnCatalog() throws Exception {
        when(bookService.list())
                .thenReturn(List.of(bookDto));

        mockMvc.perform(get("/biblioteca-pessoal/v1/livros"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].titulo").value("The Last Kingdom"))
                .andExpect(jsonPath("$[0].autor").value("Edward Stone"));

        verify(bookService).list();
    }

    @Test
    void shouldReturnBookById() throws Exception {
        when(bookService.getById("1"))
                .thenReturn(bookDto);

        mockMvc.perform(get("/biblioteca-pessoal/v1/livros/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.titulo").value("The Last Kingdom"))
                .andExpect(jsonPath("$.autor").value("Edward Stone"));

        verify(bookService).getById("1");
    }

    @Test
    void shouldUpdateBook() throws Exception {
        when(bookService.updateById(eq("1"), any(BookDto.class)))
                .thenReturn(bookDto);

        mockMvc.perform(put("/biblioteca-pessoal/v1/livros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.titulo").value("The Last Kingdom"))
                .andExpect(jsonPath("$.autor").value("Edward Stone"));

        verify(bookService).updateById(eq("1"), any(BookDto.class));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        doNothing().when(bookService).delete("1");

        mockMvc.perform(delete("/biblioteca-pessoal/v1/livros/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(bookService).delete("1");
    }

    @Test
    void shouldReturnBadRequestWhenBodyIsInvalid() throws Exception {
        BookDto invalidDto = BookDto.builder()
                .titulo("")
                .autor("")
                .anoPublicacao(0)
                .build();

        mockMvc.perform(post("/biblioteca-pessoal/v1/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(bookService);
    }
}