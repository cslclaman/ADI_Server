package com.csl.adi.repository;

import com.csl.adi.model.AdiTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdiTagRepository extends BaseRepository<AdiTag, Long> {

    Page<AdiTag> findByTipoIgnoreCaseContainingAndTagIgnoreCaseContaining (
            Pageable pageable, String tipo, String tag
    );

    Page<AdiTag> findByTipoIgnoreCaseContainingAndTagIgnoreCaseContainingAndHasInfo (
            Pageable pageable, String tipo, String tag, Boolean hasInfo
    );

}
