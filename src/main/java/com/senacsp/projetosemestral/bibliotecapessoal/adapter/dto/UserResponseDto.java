package com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {

    private String id;
    private String cpf;
    private String firstName;
    private String lastName;
    private String cep;
    private String username;
}
