package com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserRequestDtoTest {

    private Validator validator;
    private UserRequestDto dto;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();

        dto = UserRequestDto.builder()
                .cpf("12345678900")
                .firstName("Eduardo")
                .lastName("Lublanski")
                .cep("01001000")
                .username("edulub")
                .password("123456")
                .build();
    }

    @Test
    void shouldValidateSuccessfullyWhenDtoIsValid() {
        Set<ConstraintViolation<UserRequestDto>> violations =
                validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldFailWhenCpfIsNull() {
        dto.setCpf(null);

        Set<ConstraintViolation<UserRequestDto>> violations =
                validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals(
                "Cpf obrigatório",
                violations.iterator().next().getMessage()
        );
    }

    @Test
    void shouldFailWhenCpfIsBlank() {
        dto.setCpf("");

        Set<ConstraintViolation<UserRequestDto>> violations =
                validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals(
                "Cpf obrigatório",
                violations.iterator().next().getMessage()
        );
    }

    @Test
    void shouldFailWhenFirstNameIsNull() {
        dto.setFirstName(null);

        Set<ConstraintViolation<UserRequestDto>> violations =
                validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals(
                "Nome obrigatório",
                violations.iterator().next().getMessage()
        );
    }

    @Test
    void shouldFailWhenFirstNameIsBlank() {
        dto.setFirstName("");

        Set<ConstraintViolation<UserRequestDto>> violations =
                validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals(
                "Nome obrigatório",
                violations.iterator().next().getMessage()
        );
    }

    @Test
    void shouldFailWhenUsernameIsNull() {
        dto.setUsername(null);

        Set<ConstraintViolation<UserRequestDto>> violations =
                validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals(
                "Usuário obrigatório",
                violations.iterator().next().getMessage()
        );
    }

    @Test
    void shouldFailWhenUsernameIsBlank() {
        dto.setUsername("");

        Set<ConstraintViolation<UserRequestDto>> violations =
                validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals(
                "Usuário obrigatório",
                violations.iterator().next().getMessage()
        );
    }

    @Test
    void shouldFailWhenPasswordIsNull() {
        dto.setPassword(null);

        Set<ConstraintViolation<UserRequestDto>> violations =
                validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals(
                "Senha obrigatória",
                violations.iterator().next().getMessage()
        );
    }

    @Test
    void shouldFailWhenPasswordIsBlank() {
        dto.setPassword("");

        Set<ConstraintViolation<UserRequestDto>> violations =
                validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals(
                "Senha obrigatória",
                violations.iterator().next().getMessage()
        );
    }

    @Test
    void shouldFailWhenMultipleRequiredFieldsAreInvalid() {
        dto.setCpf(null);
        dto.setFirstName("");
        dto.setUsername(null);
        dto.setPassword("");

        Set<ConstraintViolation<UserRequestDto>> violations =
                validator.validate(dto);

        assertEquals(4, violations.size());
    }
}