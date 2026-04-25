package com.senacsp.projetosemestral.bibliotecapessoal.aplication.library_data_manager;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookDto;

import java.util.List;

public interface CatalogManager {
    BookDto catalog(BookDto dto);

    List<BookDto> getCatalog();

    BookDto getDetails(String id);

    BookDto updateInfo(String id, BookDto dto);

    void removeFromCatalog(String id);

    boolean isAvailable(String id);
}
