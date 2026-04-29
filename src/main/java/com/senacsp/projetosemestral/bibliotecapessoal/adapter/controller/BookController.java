package com.senacsp.projetosemestral.bibliotecapessoal.adapter.controller;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookRequestDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookResponseDto;
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
    public ResponseEntity<BookResponseDto> register(
            @RequestBody @Valid BookRequestDto bookDto
    ) {
        BookResponseDto createdBook =
                bookService.register(bookDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdBook);
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> list() {
        List<BookResponseDto> catalog =
                bookService.list();

        return ResponseEntity.ok(catalog);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getById(
            @PathVariable String id
    ) {
        BookResponseDto book =
                bookService.getById(id);

        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> update(
            @PathVariable String id,
            @RequestBody @Valid BookRequestDto bookDto
    ) {
        BookResponseDto updatedBook =
                bookService.updateById(id, bookDto);

        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String id
    ) {
        bookService.delete(id);

        return ResponseEntity.noContent().build();
    }
}