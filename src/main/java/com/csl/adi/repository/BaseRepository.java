package com.csl.adi.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends Repository <T, ID> {

    Integer count();

    List<T> findAll();

    T findOneById(ID id);

    T save(T entity);

    void delete(T entity);
}
