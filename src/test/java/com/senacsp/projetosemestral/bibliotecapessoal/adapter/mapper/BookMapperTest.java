package com.senacsp.projetosemestral.bibliotecapessoal.adapter.mapper;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookDto;
import com.senacsp.projetosemestral.bibliotecapessoal.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    private BookMapper bookMapper;
    private BookDto bookDto;
    private Book book;

    @BeforeEach
    void setUp() {
        bookMapper = new BookMapper();

        bookDto = BookDto.builder()
                .titulo("The Last Kingdom")
                .autor("Edward Stone")
                .isbn("9781234567890")
                .anoPublicacao(2024)
                .genero("Fantasy")
                .quantidadePaginas(512)
                .disponivel(true)
                .build();

        book = Book.builder()
                .id("book-001")
                .title("The Last Kingdom")
                .author("Edward Stone")
                .isbn("9781234567890")
                .yearOfPublication(2024)
                .genre("Fantasy")
                .pages(512)
                .isAvailable(true)
                .build();
    }

    @Test
    void shouldConvertDtoToBook() {
        Book mappedBook = bookMapper.toBook(bookDto);

        assertNotNull(mappedBook);
        assertEquals(bookDto.getTitulo(), mappedBook.getTitle());
        assertEquals(bookDto.getAutor(), mappedBook.getAuthor());
        assertEquals(bookDto.getIsbn(), mappedBook.getIsbn());
        assertEquals(bookDto.getAnoPublicacao(), mappedBook.getYearOfPublication());
        assertEquals(bookDto.getGenero(), mappedBook.getGenre());
        assertEquals(bookDto.getQuantidadePaginas(), mappedBook.getPages());
        assertEquals(bookDto.getDisponivel(), mappedBook.getIsAvailable());
        assertNotNull(mappedBook.getRegisterDate());
    }

    @Test
    void shouldThrowExceptionWhenDtoIsNull() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> bookMapper.toBook(null)
                );

        assertEquals("dto não pode ser nulo", exception.getMessage());
    }

    @Test
    void shouldConvertBookToDto() {
        BookDto mappedDto = bookMapper.toDto(book);

        assertNotNull(mappedDto);
        assertEquals(book.getTitle(), mappedDto.getTitulo());
        assertEquals(book.getAuthor(), mappedDto.getAutor());
        assertEquals(book.getIsbn(), mappedDto.getIsbn());
        assertEquals(book.getYearOfPublication(), mappedDto.getAnoPublicacao());
        assertEquals(book.getGenre(), mappedDto.getGenero());
        assertEquals(book.getPages(), mappedDto.getQuantidadePaginas());
        assertEquals(book.getIsAvailable(), mappedDto.getDisponivel());
    }

    @Test
    void shouldThrowExceptionWhenBookIsNull() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> bookMapper.toDto(null)
                );

        assertEquals("book não pode ser nulo", exception.getMessage());
    }

    @Test
    void shouldReturnEmptyListWhenDtoListIsNull() {
        List<Book> books = bookMapper.toBookList(null);

        assertNotNull(books);
        assertTrue(books.isEmpty());
    }

    @Test
    void shouldIgnoreNullItemsWhenConvertingDtoList() {
        List<BookDto> dtos = new ArrayList<>();
        dtos.add(bookDto);
        dtos.add(null);

        List<Book> books = bookMapper.toBookList(dtos);

        assertEquals(1, books.size());
        assertEquals(bookDto.getTitulo(), books.get(0).getTitle());
    }

    @Test
    void shouldReturnEmptyListWhenBookListIsNull() {
        List<BookDto> dtos = bookMapper.toDtoList(null);

        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    @Test
    void shouldConvertBookListToDtoList() {
        List<Book> books = List.of(book);

        List<BookDto> dtos = bookMapper.toDtoList(books);

        assertEquals(1, dtos.size());
        assertEquals(book.getTitle(), dtos.get(0).getTitulo());
    }

    @Test
    void shouldUpdateExistingBook() {
        BookDto newInfo = BookDto.builder()
                .titulo("The Last Kingdom - Revised Edition")
                .autor("Edward Stone Jr.")
                .isbn("9780987654321")
                .anoPublicacao(2025)
                .genero("Epic Fantasy")
                .quantidadePaginas(650)
                .disponivel(false)
                .build();

        bookMapper.updateBook(book, newInfo);

        assertEquals(newInfo.getTitulo(), book.getTitle());
        assertEquals(newInfo.getAutor(), book.getAuthor());
        assertEquals(newInfo.getIsbn(), book.getIsbn());
        assertEquals(newInfo.getAnoPublicacao(), book.getYearOfPublication());
        assertEquals(newInfo.getGenero(), book.getGenre());
        assertEquals(newInfo.getQuantidadePaginas(), book.getPages());
        assertEquals(newInfo.getDisponivel(), book.getIsAvailable());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNullObjects() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> bookMapper.updateBook(null, bookDto)
                );

        assertEquals("dto ou book não pode ser nulo", exception.getMessage());
    }
}