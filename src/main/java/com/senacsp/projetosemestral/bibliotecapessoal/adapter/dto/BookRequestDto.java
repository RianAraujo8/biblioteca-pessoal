package com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class BookRequestDto {

    @NotBlank(message = "Título obrigatório")
    private String titulo;

    @NotBlank(message = "Autor obrigatório")
    private String autor;

    private String isbn;

    @NotNull(message = "Ano de publicação obrigatório")
    @Positive(message = "Ano de publicação deve ser maior que zero")
    private Integer anoPublicacao;

    private String genero;

    private Integer quantidadePaginas;

    private Boolean disponivel;
}
