package com.senacsp.projetosemestral.bibliotecapessoal.adapter.mapper;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookRequestDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookResponseDto;
import com.senacsp.projetosemestral.bibliotecapessoal.domain.Book;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class BookMapper {

    public Book toBook(BookRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("BookRequestDto não pode ser nulo");
        }

        return Book.builder()
                .title(dto.getTitulo())
                .author(dto.getAutor())
                .isbn(dto.getIsbn())
                .yearOfPublication(dto.getAnoPublicacao())
                .genre(dto.getGenero())
                .pages(dto.getQuantidadePaginas())
                .isAvailable(true)
                .registerDate(LocalDateTime.now())
                .build();
    }

    public BookResponseDto toResponseDto(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book não pode ser nulo");
        }

        return BookResponseDto.builder()
                .id(book.getId())
                .titulo(book.getTitle())
                .autor(book.getAuthor())
                .isbn(book.getIsbn())
                .anoPublicacao(book.getYearOfPublication())
                .genero(book.getGenre())
                .quantidadePaginas(book.getPages())
                .disponivel(book.getIsAvailable())
                .build();
    }

    public List<Book> toBookList(List<BookRequestDto> dtos) {
        if (dtos == null) {
            return List.of();
        }

        return dtos.stream()
                .filter(Objects::nonNull)
                .map(this::toBook)
                .toList();
    }

    public List<BookResponseDto> toResponseDtoList(List<Book> books) {
        if (books == null) {
            return List.of();
        }

        return books.stream()
                .filter(Objects::nonNull)
                .map(this::toResponseDto)
                .toList();
    }

    public void updateBook(Book book, BookRequestDto dto) {
        if (book == null || dto == null) {
            throw new IllegalArgumentException(
                    "Book ou BookRequestDto não pode ser nulo");
        }

        book.setTitle(dto.getTitulo());
        book.setAuthor(dto.getAutor());
        book.setIsbn(dto.getIsbn());
        book.setYearOfPublication(dto.getAnoPublicacao());
        book.setGenre(dto.getGenero());
        book.setPages(dto.getQuantidadePaginas());
        book.setIsAvailable(dto.getDisponivel());
    }
}