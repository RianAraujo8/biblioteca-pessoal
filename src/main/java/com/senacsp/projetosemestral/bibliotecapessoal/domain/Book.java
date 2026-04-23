package com.senacsp.projetosemestral.bibliotecapessoal.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Document(collection = "book")
public class Book {

    @Id
    private String id;
    private String titulo;
    private String autor;
    private String isbn;
    private Integer anoPublicacao;
    private String genero;
    private Integer quantidadePaginas;
    private Boolean disponivel;
    private LocalDateTime dataCadastro;
}
