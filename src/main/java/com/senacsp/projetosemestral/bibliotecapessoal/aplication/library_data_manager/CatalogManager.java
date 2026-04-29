package com.senacsp.projetosemestral.bibliotecapessoal.aplication.library_data_manager;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookRequestDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookResponseDto;

import java.util.List;

public interface CatalogManager {

    BookResponseDto catalog(BookRequestDto dto);

    List<BookResponseDto> getCatalog();

    BookResponseDto getBookDetails(String id);

    BookResponseDto updateInfo(String id, BookRequestDto dto);

    void removeFromCatalog(String id);

    boolean isAvailable(String id);
}