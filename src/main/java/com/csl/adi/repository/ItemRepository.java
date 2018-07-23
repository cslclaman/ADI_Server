package com.csl.adi.repository;

import com.csl.adi.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepository extends BaseRepository<Item, Long> {

    Item findByTagIgnoreCase(String tag);

    Page<Item> findByNomeIgnoreCaseContaining(Pageable pageable, String nome);

    Page<Item> findByNomeIgnoreCaseContainingAndAlcance(Pageable pageable, String nome, Integer alcance);

}
