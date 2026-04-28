package com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRequestDto {

    @NotBlank(message = "Cpf obrigatório")
    private String cpf;

    @NotBlank(message = "Nome obrigatório")
    private String firstName;

    private String lastName;

    private String cep;

    @NotBlank(message = "Usuário obrigatório")
    private String username;

    @NotBlank(message = "Senha obrigatória")
    private String password;
}
