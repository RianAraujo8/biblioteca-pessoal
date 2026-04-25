package com.senacsp.projetosemestral.bibliotecapessoal.adapter.persistence.repository.mongodb;

import com.senacsp.projetosemestral.bibliotecapessoal.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<User, String> {
}
