package com.csl.adi.repository;

import com.csl.adi.model.Url;

import java.util.List;

public interface UrlRepository extends BaseRepository<Url, Integer> {

    List<Url> findByOrigem (Integer origem);

    Url findByDescricao (String descricao);

    Url findByOrigemAndDescricao (Integer origem, String descricao);

}
