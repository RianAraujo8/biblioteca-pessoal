package com.senacsp.projetosemestral.bibliotecapessoal.adapter.persistence;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.mapper.BookMapper;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.persistence.repository.mongodb.BookMongoRepository;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.persistence.repository.mongodb.CatalogManagerMongoImp;
import com.senacsp.projetosemestral.bibliotecapessoal.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogManagerMongoImpTest {

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookMongoRepository bookMongoRepository;

    @InjectMocks
    private CatalogManagerMongoImp catalogManager;

    private BookDto bookDto;
    private Book book;

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

        book = Book.builder()
                .id("1")
                .title("The Last Kingdom")
                .author("Edward Stone")
                .isbn("978-85-0000-000-0")
                .yearOfPublication(2024)
                .genre("Fantasy")
                .pages(512)
                .isAvailable(true)
                .build();
    }

    @Test
    void shouldCatalogBook() {
        when(bookMapper.toBook(bookDto)).thenReturn(book);
        when(bookMongoRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = catalogManager.catalog(bookDto);

        assertNotNull(result);
        assertEquals(bookDto.getTitulo(), result.getTitulo());

        verify(bookMapper).toBook(bookDto);
        verify(bookMongoRepository).save(book);
        verify(bookMapper).toDto(book);
    }

    @Test
    void shouldReturnCatalog() {
        List<Book> books = List.of(book);
        List<BookDto> dtos = List.of(bookDto);

        when(bookMongoRepository.findAll()).thenReturn(books);
        when(bookMapper.toDtoList(books)).thenReturn(dtos);

        List<BookDto> result = catalogManager.getCatalog();

        assertEquals(1, result.size());
        assertEquals("The Last Kingdom", result.get(0).getTitulo());

        verify(bookMongoRepository).findAll();
        verify(bookMapper).toDtoList(books);
    }

    @Test
    void shouldReturnBookDetails() {
        when(bookMongoRepository.findById("1"))
                .thenReturn(Optional.of(book));

        when(bookMapper.toDto(book))
                .thenReturn(bookDto);

        BookDto result = catalogManager.getBookDetails("1");

        assertNotNull(result);
        assertEquals("The Last Kingdom", result.getTitulo());

        verify(bookMongoRepository).findById("1");
        verify(bookMapper).toDto(book);
    }

    @Test
    void shouldThrowExceptionWhenBookNotFound() {
        when(bookMongoRepository.findById("1"))
                .thenReturn(Optional.empty());

        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> catalogManager.getBookDetails("1")
                );

        assertEquals(
                "não há livros catalogados com id 1",
                ex.getMessage()
        );

        verify(bookMongoRepository).findById("1");
        verifyNoInteractions(bookMapper);
    }

    @Test
    void shouldUpdateBook() {
        when(bookMongoRepository.findById("1"))
                .thenReturn(Optional.of(book));

        when(bookMongoRepository.save(any(Book.class)))
                .thenReturn(book);

        when(bookMapper.toDto(any(Book.class)))
                .thenReturn(bookDto);

        BookDto result = catalogManager.updateInfo("1", bookDto);

        assertNotNull(result);
        assertEquals(bookDto.getTitulo(), result.getTitulo());

        verify(bookMongoRepository).findById("1");
        verify(bookMongoRepository).save(any(Book.class));
        verify(bookMapper).toDto(any(Book.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingBook() {
        when(bookMongoRepository.findById("1"))
                .thenReturn(Optional.empty());

        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> catalogManager.updateInfo("1", bookDto)
                );

        assertEquals(
                "não há livros catalogados com id 1",
                ex.getMessage()
        );

        verify(bookMongoRepository).findById("1");
        verify(bookMongoRepository, never()).save(any());
    }

    @Test
    void shouldDeleteBook() {
        doNothing().when(bookMongoRepository).deleteById("1");

        catalogManager.removeFromCatalog("1");

        verify(bookMongoRepository).deleteById("1");
    }
}