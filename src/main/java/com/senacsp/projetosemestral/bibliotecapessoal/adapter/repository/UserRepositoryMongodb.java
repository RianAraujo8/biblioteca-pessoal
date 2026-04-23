package com.senacsp.projetosemestral.bibliotecapessoal.adapter.repository;

import com.senacsp.projetosemestral.bibliotecapessoal.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepositoryMongodb extends MongoRepository<User, String> {
}
