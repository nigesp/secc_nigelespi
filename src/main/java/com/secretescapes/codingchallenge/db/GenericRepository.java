package com.secretescapes.codingchallenge.db;

import java.util.List;

/**
 * Created by Nigel on 2016-02-29.
 */
public interface GenericRepository<T> {

    T findById(Integer id);

    List<T> findAll();

    T save(T entity);

}
