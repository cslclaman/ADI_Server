package com.csl.adi.repository;

import com.csl.adi.model.Origem;

public interface OrigemRepository extends BaseRepository<Origem, Integer> {

    Origem findBySigla(String sigla);


}
