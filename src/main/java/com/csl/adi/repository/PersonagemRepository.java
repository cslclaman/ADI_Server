package com.csl.adi.repository;

import com.csl.adi.model.Personagem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonagemRepository extends BaseRepository<Personagem, Long> {

    Personagem findByTagIgnoreCase (String tag);

    Page<Personagem> findByNomeIgnoreCaseContaining(Pageable pageable, String nome);

    Page<Personagem> findByNomeIgnoreCaseContainingAndSerie(Pageable pageable, String nome, Long serie);

    Page<Personagem> findByNomeIgnoreCaseContainingAndSerieAndArtista(Pageable pageable, String nome, Long serie, Long artista);

}
