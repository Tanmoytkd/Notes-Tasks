package net.therap.notestasks.dao;

import java.util.List;
import java.util.Optional;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
public interface Dao<T> {

    Optional<T> find(long id);

    Optional<T> find(T item);

    T refresh(T item);

    List<T> findAll();

    T saveOrUpdate(T item);

    void delete(T item);

    void destroy(T item);
}
