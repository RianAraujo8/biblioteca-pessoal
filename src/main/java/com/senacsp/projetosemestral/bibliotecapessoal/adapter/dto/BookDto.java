package com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookDto {

    @NotBlank(message = "Título obrigatório")
    private String titulo;
    @NotBlank(message = "Autor obrigatório")
    private String autor;
    private String isbn;
    @NotBlank(message = "Ano de publicação obrigatório")
    private Integer anoPublicacao;
    private String genero;
    private Integer quantidadePaginas;
    private Boolean disponivel;
}
