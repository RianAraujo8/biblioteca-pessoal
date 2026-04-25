package com.senacsp.projetosemestral.bibliotecapessoal.aplication.service;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookDto;
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
    void shouldRegisterBook() {
        when(catalogManager.catalog(bookDto))
                .thenReturn(bookDto);

        BookDto result = bookService.register(bookDto);

        assertNotNull(result);
        assertEquals(bookDto.getTitulo(), result.getTitulo());

        verify(catalogManager).catalog(bookDto);
    }

    @Test
    void shouldReturnCatalog() {
        List<BookDto> catalog = List.of(bookDto);

        when(catalogManager.getCatalog())
                .thenReturn(catalog);

        List<BookDto> result = bookService.list();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("The Last Kingdom", result.get(0).getTitulo());

        verify(catalogManager).getCatalog();
    }

    @Test
    void shouldReturnBookById() {
        when(catalogManager.getDetails("1"))
                .thenReturn(bookDto);

        BookDto result = bookService.getById("1");

        assertNotNull(result);
        assertEquals(bookDto.getTitulo(), result.getTitulo());

        verify(catalogManager).getDetails("1");
    }

    @Test
    void shouldUpdateBookById() {
        when(catalogManager.updateInfo("1", bookDto))
                .thenReturn(bookDto);

        BookDto result = bookService.updateById("1", bookDto);

        assertNotNull(result);
        assertEquals(bookDto.getTitulo(), result.getTitulo());

        verify(catalogManager).updateInfo("1", bookDto);
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