package com.csl.adi.repository;

import com.csl.adi.model.Origem;

public interface OrigemRepository extends BaseRepository<Origem, Long> {

    Origem findBySigla(String sigla);


}
