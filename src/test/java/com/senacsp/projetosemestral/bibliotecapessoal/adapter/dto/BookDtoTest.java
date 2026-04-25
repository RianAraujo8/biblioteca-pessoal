package com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookDtoValidationTest {

    private Validator validator;
    private BookDto validBookDto;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        validBookDto = BookDto.builder()
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
    void shouldPassValidationWhenDtoIsValid() {
        Set<ConstraintViolation<BookDto>> violations =
                validator.validate(validBookDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldFailWhenTituloIsNull() {
        validBookDto.setTitulo(null);

        Set<ConstraintViolation<BookDto>> violations =
                validator.validate(validBookDto);

        assertEquals(1, violations.size());
        assertEquals("Título obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenTituloIsBlank() {
        validBookDto.setTitulo("   ");

        Set<ConstraintViolation<BookDto>> violations =
                validator.validate(validBookDto);

        assertEquals(1, violations.size());
        assertEquals("Título obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenAutorIsNull() {
        validBookDto.setAutor(null);

        Set<ConstraintViolation<BookDto>> violations =
                validator.validate(validBookDto);

        assertEquals(1, violations.size());
        assertEquals("Autor obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenAutorIsBlank() {
        validBookDto.setAutor("");

        Set<ConstraintViolation<BookDto>> violations =
                validator.validate(validBookDto);

        assertEquals(1, violations.size());
        assertEquals("Autor obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenAnoPublicacaoIsNull() {
        validBookDto.setAnoPublicacao(null);

        Set<ConstraintViolation<BookDto>> violations =
                validator.validate(validBookDto);

        assertEquals(1, violations.size());
        assertEquals("Ano de publicação obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailWhenAnoPublicacaoIsZero() {
        validBookDto.setAnoPublicacao(0);

        Set<ConstraintViolation<BookDto>> violations =
                validator.validate(validBookDto);

        assertEquals(1, violations.size());
        assertEquals(
                "Ano de publicação deve ser maior que zero",
                violations.iterator().next().getMessage()
        );
    }

    @Test
    void shouldFailWhenAnoPublicacaoIsNegative() {
        validBookDto.setAnoPublicacao(-1995);

        Set<ConstraintViolation<BookDto>> violations =
                validator.validate(validBookDto);

        assertEquals(1, violations.size());
        assertEquals(
                "Ano de publicação deve ser maior que zero",
                violations.iterator().next().getMessage()
        );
    }
}