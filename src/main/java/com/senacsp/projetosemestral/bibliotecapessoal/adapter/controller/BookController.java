package com.senacsp.projetosemestral.bibliotecapessoal.adapter.controller;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RestController
@RequestMapping("/biblioteca-pessoal/v1/livro")
public class BookController {

    private BookService bookservice;

    @PostMapping
    public ResponseEntity<BookDto> register(@RequestBody @Valid BookDto bookDto){

        BookDto newBookDto = bookService.catalog(bookDto);

        return ResponseEntity.ok(newBookDto);
    }
}
