package com.senacsp.projetosemestral.bibliotecapessoal.aplication.library_data_manager;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookDto;
import com.senacsp.projetosemestral.bibliotecapessoal.domain.Book;

import java.util.List;

public interface BooksManager {
    Book createBook(BookDto dto);

    List<BookDto> getCatalog();

    BookDto getBookDetails(String id);

    Book updateBookInfo(String id, BookDto dto);

    void removeBookFromCatalog(String id);

    boolean isBookAvailable(String id);
}
