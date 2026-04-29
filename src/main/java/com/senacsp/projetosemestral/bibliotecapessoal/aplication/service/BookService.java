package com.senacsp.projetosemestral.bibliotecapessoal.aplication.service;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookRequestDto;
import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookResponseDto;
import com.senacsp.projetosemestral.bibliotecapessoal.aplication.library_data_manager.CatalogManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {

    private final CatalogManager catalogManager;

    public BookResponseDto register(BookRequestDto dto) {
        return catalogManager.catalog(dto);
    }

    public List<BookResponseDto> list() {
        return catalogManager.getCatalog();
    }

    public BookResponseDto getById(String id) {
        return catalogManager.getBookDetails(id);
    }

    public BookResponseDto updateById(
            String id,
            BookRequestDto dto
    ) {
        return catalogManager.updateInfo(id, dto);
    }

    public void delete(String id) {
        catalogManager.removeFromCatalog(id);
    }
}