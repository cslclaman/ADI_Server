package com.csl.adi.repository;

import com.csl.adi.model.Artista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArtistaRepository extends BaseRepository<Artista, Long> {

    Artista findByTagIgnoreCase (String tag);
    Page<Artista> findByNomeIgnoreCaseContaining (Pageable pageable, String nome);
    Page<Artista> findByNomeIgnoreCaseContainingAndAtivo (Pageable pageable, String tag, Boolean ativo);

}
