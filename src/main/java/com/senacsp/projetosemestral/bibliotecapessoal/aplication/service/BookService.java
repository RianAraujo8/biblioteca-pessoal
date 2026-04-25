package com.senacsp.projetosemestral.bibliotecapessoal.aplication.service;

import com.senacsp.projetosemestral.bibliotecapessoal.adapter.dto.BookDto;
import com.senacsp.projetosemestral.bibliotecapessoal.aplication.library_data_manager.CatalogManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {

    private final CatalogManager catalogManager;

    public BookDto register(BookDto dto) {
        return catalogManager.catalog(dto);
    }

    public List<BookDto> list() {
        return catalogManager.getCatalog();
    }

    public BookDto getById(String Id){
        return catalogManager.getDetails(Id);
    }

    public BookDto updateById(String id, BookDto dto) {
        return catalogManager.updateInfo(id, dto);
    }

    public void delete(String id){
        catalogManager.removeFromCatalog(id);
    }
}
