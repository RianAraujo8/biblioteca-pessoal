package com.senacsp.projetosemestral.bibliotecapessoal.adapter.persistence.repository.mongodb;

import com.senacsp.projetosemestral.bibliotecapessoal.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookMongoRepository extends MongoRepository<Book, String> {
}
