package com.senacsp.projetosemestral.bibliotecapessoal.adapter.mapper;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookDto;
import com.senacsp.projetosemestral.bibliotecapessoal.domain.Book;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class BookMapper {

    public Book toBook(BookDto dto) {
        if (dto == null) throw new IllegalArgumentException("dto não pode ser nulo");

        return Book.builder()
                .title(dto.getTitulo())
                .author(dto.getAutor())
                .isbn(dto.getIsbn())
                .yearOfPublication(dto.getAnoPublicacao())
                .genre(dto.getGenero())
                .pages(dto.getQuantidadePaginas())
                .isAvailable(dto.getDisponivel())
                .registerDate(LocalDateTime.now())
                .build();
    }

    public BookDto toDto(Book book) {
        if (book == null) throw new IllegalArgumentException("book não pode ser nulo");;

        BookDto dto = new BookDto();
        dto.setTitulo(book.getTitle());
        dto.setAutor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setAnoPublicacao(book.getYearOfPublication());
        dto.setGenero(book.getGenre());
        dto.setQuantidadePaginas(book.getPages());
        dto.setDisponivel(book.getIsAvailable());

        return dto;
    }

    public List<Book> toBookList(List<BookDto> dtos) {
        if (dtos == null) {
            return List.of();
        }

        return dtos.stream()
                .filter(Objects::nonNull)
                .map(this::toBook)
                .toList();
    }


    public List<BookDto> toDtoList(List<Book> entities) {
        if (entities == null) {
            return List.of();
        }

        return entities.stream()
                .filter(Objects::nonNull)
                .map(this::toDto)
                .toList();
    }


    public void updateBook(Book book, BookDto dto) {
        if (book == null || dto == null) throw new IllegalArgumentException("dto ou book não pode ser nulo");;

        book.setTitle(dto.getTitulo());
        book.setAuthor(dto.getAutor());
        book.setIsbn(dto.getIsbn());
        book.setYearOfPublication(dto.getAnoPublicacao());
        book.setGenre(dto.getGenero());
        book.setPages(dto.getQuantidadePaginas());
        book.setIsAvailable(dto.getDisponivel());
    }
}
