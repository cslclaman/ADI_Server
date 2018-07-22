package com.csl.adi.repository;

import com.csl.adi.model.Url;

import java.util.List;

public interface UrlRepository extends BaseRepository<Url, Long> {

    List<Url> findByOrigem (Long origem);

    Url findByDescricao (String descricao);

    Url findByOrigemAndDescricao (Long origem, String descricao);

}
