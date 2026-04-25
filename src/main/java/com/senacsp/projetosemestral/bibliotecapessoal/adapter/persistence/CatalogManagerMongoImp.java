package com.senacsp.projetosemestral.bibliotecapessoal.adapter.persistence;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.mapper.BookMapper;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.persistence.repository.mongodb.BookMongoRepository;
import com.senacsp.projetosemestral.bibliotecapessoal.aplication.library_data_manager.CatalogManager;
import com.senacsp.projetosemestral.bibliotecapessoal.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CatalogManagerMongoImp implements CatalogManager {

    private final BookMapper bookMapper;
    private final BookMongoRepository bookMongoRepository;

    @Override
    public BookDto catalog(BookDto dto) {
        Book newBook = bookMapper.toBook(dto);
        Book savedBook = bookMongoRepository.save(newBook);

        BookDto savedBookInfo = bookMapper.toDto(savedBook);
        return savedBookInfo;
    }

    @Override
    public List<BookDto> getCatalog() {
        List<Book> catalog = bookMongoRepository.findAll();

        List<BookDto> dtoCatalog = bookMapper.toDtoList(catalog);
        return dtoCatalog;
    }

    @Override
    public BookDto getDetails(String id) {
        Book book = bookMongoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("não há livros catalogados com id "+id));

        BookDto bookDto = bookMapper.toDto(book);
        return bookDto;
    }

    @Override
    public BookDto updateInfo(String id, BookDto dto) {
        Book bookToUpdate = bookMongoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "não há livros catalogados com id " + id));

        bookToUpdate.setTitle(dto.getTitulo());
        bookToUpdate.setAuthor(dto.getAutor());
        bookToUpdate.setIsbn(dto.getIsbn());
        bookToUpdate.setYearOfPublication(dto.getAnoPublicacao());
        bookToUpdate.setGenre(dto.getGenero());
        bookToUpdate.setPages(dto.getQuantidadePaginas());
        bookToUpdate.setIsAvailable(dto.getDisponivel());

        Book updatedBook = bookMongoRepository.save(bookToUpdate);

        return bookMapper.toDto(updatedBook);
    }

    @Override
    public void removeFromCatalog(String id) {
        bookMongoRepository.deleteById(id);
    }

    @Override
    public boolean isAvailable(String id) {
        return true;
    }
}
