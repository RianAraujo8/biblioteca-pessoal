package com.senacsp.projetosemestral.bibliotecapessoal.adapter.controller;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookDto;
import com.senacsp.projetosemestral.bibliotecapessoal.aplication.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/biblioteca-pessoal/v1/livros")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDto> register(
            @RequestBody @Valid BookDto bookDto
    ) {
        BookDto createdBook = bookService.register(bookDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdBook);
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> list() {
        List<BookDto> catalog = bookService.list();

        return ResponseEntity.ok(catalog);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto> getById(
            @PathVariable String bookId
    ) {
        BookDto book = bookService.getById(bookId);

        return ResponseEntity.ok(book);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookDto> update(
            @PathVariable String bookId,
            @RequestBody @Valid BookDto bookDto
    ) {
        BookDto updatedBook = bookService.updateById(bookId, bookDto);

        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> delete(
            @PathVariable String bookId
    ) {
        bookService.delete(bookId);

        return ResponseEntity.noContent().build();
    }
}
