package com.senacsp.projetosemestral.bibliotecapessoal.adapter.controller;



import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.UserRequestDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.UserResponseDto;
import com.senacsp.projetosemestral.bibliotecapessoal.aplication.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    private UserRequestDto requestDto;
    private UserResponseDto responseDto;

    @BeforeEach
    void setUp() {
        requestDto = UserRequestDto.builder()
                .cpf("12345678900")
                .firstName("Eduardo")
                .lastName("Lublanski")
                .cep("01001-000")
                .username("eduardo.dev")
                .password("123456")
                .build();

        responseDto = UserResponseDto.builder()
                .id("abc123")
                .cpf("12345678900")
                .firstName("Eduardo")
                .lastName("Lublanski")
                .cep("01001-000")
                .username("eduardo.dev")
                .build();
    }

    @Test
    void shouldRegisterUser() throws Exception {
        when(userService.register(Mockito.any()))
                .thenReturn(responseDto);

        mockMvc.perform(post("/biblioteca-pessoal/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("abc123"))
                .andExpect(jsonPath("$.username").value("eduardo.dev"));
    }

    @Test
    void shouldListUsers() throws Exception {
        when(userService.list())
                .thenReturn(List.of(responseDto));

        mockMvc.perform(get("/biblioteca-pessoal/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username").value("eduardo.dev"));
    }

    @Test
    void shouldGetUserById() throws Exception {
        when(userService.getById("abc123"))
                .thenReturn(responseDto);

        mockMvc.perform(get("/biblioteca-pessoal/v1/usuarios/abc123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("abc123"))
                .andExpect(jsonPath("$.username").value("eduardo.dev"));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UserResponseDto updated =
                responseDto.toBuilder()
                        .firstName("Carlos")
                        .username("carlos.dev")
                        .build();

        when(userService.updateById(Mockito.eq("abc123"), Mockito.any()))
                .thenReturn(updated);

        mockMvc.perform(put("/biblioteca-pessoal/v1/usuarios/abc123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value("Carlos"))
                .andExpect(jsonPath("$.username").value("carlos.dev"));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        doNothing().when(userService).delete("abc123");

        mockMvc.perform(delete("/biblioteca-pessoal/v1/usuarios/abc123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnBadRequestWhenRequestIsInvalid() throws Exception {
        UserRequestDto invalid =
                UserRequestDto.builder().build();

        mockMvc.perform(post("/biblioteca-pessoal/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }
}