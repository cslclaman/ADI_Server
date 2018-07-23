package com.csl.adi.repository;

import com.csl.adi.model.Info;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InfoRepository extends BaseRepository<Info, Long> {

    Page<Info> findByTipoIgnoreCaseContainingAndNomeIgnoreCaseContaining (
            Pageable pageable,
            String tipo,
            String nome
    );

    Info findByAdiTag (Long adiTag);

    Info findByTipoIgnoreCaseAndTagIgnoreCase (String tipo, String tag);

}
