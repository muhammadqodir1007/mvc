package com.epam.esm.repository;


import com.epam.esm.entity.creteria.EntityPage;
import com.epam.esm.pagination.PaginationResult;

import java.util.Optional;

/**
 * Interface {@code BasicDao} describes CRD operations for working with database tables.
 *
 * @param <T> the type parameter
 */
public interface BasicDao<T> {
    PaginationResult<T> findAll(EntityPage entityPage);

    Optional<T> findById(long id);

    T insert(T object);

    boolean remove(T t);
}
