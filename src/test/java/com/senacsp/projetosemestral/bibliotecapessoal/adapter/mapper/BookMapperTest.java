package com.senacsp.projetosemestral.bibliotecapessoal.adapter.mapper;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookRequestDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookResponseDto;
import com.senacsp.projetosemestral.bibliotecapessoal.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    private BookMapper bookMapper;
    private BookRequestDto bookRequestDto;
    private Book book;

    @BeforeEach
    void setUp() {
        bookMapper = new BookMapper();

        bookRequestDto = BookRequestDto.builder()
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
    void shouldConvertRequestDtoToBook() {
        Book mappedBook = bookMapper.toBook(bookRequestDto);

        assertNotNull(mappedBook);
        assertEquals(bookRequestDto.getTitulo(), mappedBook.getTitle());
        assertEquals(bookRequestDto.getAutor(), mappedBook.getAuthor());
        assertEquals(bookRequestDto.getIsbn(), mappedBook.getIsbn());
        assertEquals(bookRequestDto.getAnoPublicacao(), mappedBook.getYearOfPublication());
        assertEquals(bookRequestDto.getGenero(), mappedBook.getGenre());
        assertEquals(bookRequestDto.getQuantidadePaginas(), mappedBook.getPages());
        assertEquals(bookRequestDto.getDisponivel(), mappedBook.getIsAvailable());
        assertNotNull(mappedBook.getRegisterDate());
    }

    @Test
    void shouldThrowExceptionWhenDtoIsNull() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> bookMapper.toBook(null)
                );

        assertEquals(
                "BookRequestDto não pode ser nulo",
                exception.getMessage()
        );
    }

    @Test
    void shouldConvertBookToResponseDto() {
        BookResponseDto mappedDto = bookMapper.toResponseDto(book);

        assertNotNull(mappedDto);
        assertEquals(book.getId(), mappedDto.getId());
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
                        () -> bookMapper.toResponseDto(null)
                );

        assertEquals(
                "Book não pode ser nulo",
                exception.getMessage()
        );
    }

    @Test
    void shouldReturnEmptyListWhenDtoListIsNull() {
        List<Book> books = bookMapper.toBookList(null);

        assertNotNull(books);
        assertTrue(books.isEmpty());
    }

    @Test
    void shouldIgnoreNullItemsWhenConvertingDtoList() {
        List<BookRequestDto> dtos = new ArrayList<>();
        dtos.add(bookRequestDto);
        dtos.add(null);

        List<Book> books = bookMapper.toBookList(dtos);

        assertEquals(1, books.size());
        assertEquals(bookRequestDto.getTitulo(), books.getFirst().getTitle());
    }

    @Test
    void shouldReturnEmptyListWhenBookListIsNull() {
        List<BookResponseDto> dtos =
                bookMapper.toResponseDtoList(null);

        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    @Test
    void shouldConvertBookListToResponseDtoList() {
        List<Book> books = List.of(book);

        List<BookResponseDto> dtos =
                bookMapper.toResponseDtoList(books);

        assertEquals(1, dtos.size());
        assertEquals(book.getId(), dtos.getFirst().getId());
        assertEquals(book.getTitle(), dtos.getFirst().getTitulo());
    }

    @Test
    void shouldUpdateExistingBook() {
        BookRequestDto newInfo = BookRequestDto.builder()
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
        assertFalse(book.getIsAvailable());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNullObjects() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> bookMapper.updateBook(null, bookRequestDto)
                );

        assertEquals(
                "Book ou BookRequestDto não pode ser nulo",
                exception.getMessage()
        );
    }
}