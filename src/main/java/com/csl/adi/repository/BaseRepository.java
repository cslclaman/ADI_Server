package com.csl.adi.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends Repository <T, ID> {

    int count();

    List<T> findAll();

    boolean existsById(ID id);

    Optional<T> findById(ID id);

    default T findOneById(ID id){
        return findById(id).orElse(null);
    }

    <S extends T> S save(S entity);

    void delete(T entity);
}
