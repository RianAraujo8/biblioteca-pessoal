package com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookResponseDto {

    private String id;

    private String titulo;

    private String autor;

    private String isbn;

    private Integer anoPublicacao;

    private String genero;

    private Integer quantidadePaginas;

    private Boolean disponivel;
}