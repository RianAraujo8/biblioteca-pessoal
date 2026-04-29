package com.senacsp.projetosemestral.bibliotecapessoal.aplication.service;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookRequestDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookResponseDto;
import com.senacsp.projetosemestral.bibliotecapessoal.aplication.library_data_manager.CatalogManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private CatalogManager catalogManager;

    @InjectMocks
    private BookService bookService;

    private BookRequestDto requestDto;
    private BookResponseDto responseDto;

    @BeforeEach
    void setUp() {
        requestDto = BookRequestDto.builder()
                .titulo("The Last Kingdom")
                .autor("Edward Stone")
                .isbn("978-85-0000-000-0")
                .anoPublicacao(2024)
                .genero("Fantasy")
                .quantidadePaginas(512)
                .disponivel(true)
                .build();

        responseDto = BookResponseDto.builder()
                .id("1")
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
    void shouldRegisterBook() {
        when(catalogManager.catalog(requestDto))
                .thenReturn(responseDto);

        BookResponseDto result =
                bookService.register(requestDto);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals(requestDto.getTitulo(), result.getTitulo());

        verify(catalogManager).catalog(requestDto);
    }

    @Test
    void shouldReturnCatalog() {
        List<BookResponseDto> catalog =
                List.of(responseDto);

        when(catalogManager.getCatalog())
                .thenReturn(catalog);

        List<BookResponseDto> result =
                bookService.list();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("The Last Kingdom", result.getFirst().getTitulo());

        verify(catalogManager).getCatalog();
    }

    @Test
    void shouldReturnBookById() {
        when(catalogManager.getBookDetails("1"))
                .thenReturn(responseDto);

        BookResponseDto result =
                bookService.getById("1");

        assertNotNull(result);
        assertEquals(responseDto.getTitulo(), result.getTitulo());

        verify(catalogManager).getBookDetails("1");
    }

    @Test
    void shouldUpdateBookById() {
        when(catalogManager.updateInfo("1", requestDto))
                .thenReturn(responseDto);

        BookResponseDto result =
                bookService.updateById("1", requestDto);

        assertNotNull(result);
        assertEquals(responseDto.getTitulo(), result.getTitulo());

        verify(catalogManager).updateInfo("1", requestDto);
    }

    @Test
    void shouldDeleteBookById() {
        doNothing()
                .when(catalogManager)
                .removeFromCatalog("1");

        bookService.delete("1");

        verify(catalogManager).removeFromCatalog("1");
    }
}