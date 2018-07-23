package com.csl.adi.repository;

import com.csl.adi.model.Serie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SerieRepository extends BaseRepository<Serie, Long> {

    Serie findByTagIgnoreCase (String tag);

    Page<Serie> findByNomeIgnoreCaseContainingOrNomeAlternativoIgnoreCaseContaining(
            Pageable pageable, String nome, String nomeAlternativo);

}
