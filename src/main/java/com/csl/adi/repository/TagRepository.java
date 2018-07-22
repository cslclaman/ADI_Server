package com.csl.adi.repository;

import com.csl.adi.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagRepository extends BaseRepository<Tag, Long> {

    Page<Tag> findTagByTagIgnoreCaseContaining (
            Pageable pageable,
            String tag
    );

    List<Tag> findTagByAdiTag (Long adiTag);

}
