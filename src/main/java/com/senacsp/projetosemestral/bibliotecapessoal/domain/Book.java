package com.senacsp.projetosemestral.bibliotecapessoal.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "book")
public class Book {

    @Id()
    private String id;
    private String title;
    private String author;
    private String isbn;
    private Integer yearOfPublication;
    private String genre;
    private Integer pages;
    private Boolean isAvailable;
    private LocalDateTime registerDate;
}
