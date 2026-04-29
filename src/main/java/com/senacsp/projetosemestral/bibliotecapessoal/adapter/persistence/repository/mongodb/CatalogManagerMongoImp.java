package com.senacsp.projetosemestral.bibliotecapessoal.adapter.persistence.repository.mongodb;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookRequestDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookResponseDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.mapper.BookMapper;
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
    public BookResponseDto catalog(BookRequestDto dto) {
        Book newBook = bookMapper.toBook(dto);
        Book savedBook = bookMongoRepository.save(newBook);

        return bookMapper.toResponseDto(savedBook);
    }

    @Override
    public List<BookResponseDto> getCatalog() {
        List<Book> catalog = bookMongoRepository.findAll();

        return bookMapper.toResponseDtoList(catalog);
    }

    @Override
    public BookResponseDto getBookDetails(String id) {
        Book book = bookMongoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "não há livros catalogados com id " + id));

        return bookMapper.toResponseDto(book);
    }

    @Override
    public BookResponseDto updateInfo(String id, BookRequestDto dto) {
        Book bookToUpdate = bookMongoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "não há livros catalogados com id " + id));

        bookMapper.updateBook(bookToUpdate, dto);

        Book updatedBook = bookMongoRepository.save(bookToUpdate);

        return bookMapper.toResponseDto(updatedBook);
    }

    @Override
    public void removeFromCatalog(String id) {
        if (!bookMongoRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "não há livros catalogados com id " + id);
        }

        bookMongoRepository.deleteById(id);
    }

    @Override
    public boolean isAvailable(String id) {
        Book book = bookMongoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "não há livros catalogados com id " + id));

        return book.getIsAvailable();
    }
}