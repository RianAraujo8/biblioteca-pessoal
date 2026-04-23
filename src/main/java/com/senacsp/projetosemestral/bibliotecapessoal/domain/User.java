package com.senacsp.projetosemestral.bibliotecapessoal.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Document(collection = "user")
public class User {

    @Id
    private String id;
    private String cpf;
    private String firstName;
    private String lastName;
    private String cep;
    private String username;
    private String password;
}
