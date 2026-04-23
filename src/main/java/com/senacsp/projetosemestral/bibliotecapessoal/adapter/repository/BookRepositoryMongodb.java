package com.senacsp.projetosemestral.bibliotecapessoal.adapter.repository;

import com.senacsp.projetosemestral.bibliotecapessoal.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepositoryMongodb extends MongoRepository<Book, String> {
}
