package net.therap.notestasks.dao;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
public interface Dao<T> {

    Optional<T> find(Long id);

    Optional<T> find(T item);

    List<T> findAll();

    T saveOrUpdate(T item);

    void delete(T item);
}