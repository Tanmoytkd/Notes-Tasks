package net.therap.notestasks.dao;

import net.therap.notestasks.domain.BasicEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
public abstract class BasicDao<T extends BasicEntity> implements Dao<T> {

    @PersistenceContext(unitName = "notestasks")
    protected EntityManager em;

    private final Class<T> persistentClass;

    protected BasicDao(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @Override
    public Optional<T> find(Long id) {
        T item = em.find(persistentClass, id);
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<T> find(T item) {
        return find(item.getId());
    }

    @Override
    public List<T> findAll() {
        String queryString = persistentClass.getSimpleName() + ".findAll";
        return em.createNamedQuery(queryString, persistentClass).getResultList();
    }

    @Override
    @Transactional
    public T saveOrUpdate(T item) {
        T persistedItem;

        if (item.isNew()) {
            em.persist(item);
            persistedItem = item;
        } else {
            persistedItem = em.merge(item);
        }

        em.flush();
        return persistedItem;
    }

    @Override
    @Transactional
    public void delete(T item) {
        find(item).ifPresent(persistedItem -> em.remove(persistedItem));
        em.flush();
    }
}
