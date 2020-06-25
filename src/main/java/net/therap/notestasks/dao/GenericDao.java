package net.therap.notestasks.dao;

import net.therap.notestasks.domain.BasicEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static net.therap.notestasks.util.Constants.PERSISTENCE_UNIT_NAME;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
public abstract class GenericDao<T extends BasicEntity> implements Dao<T> {

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    protected EntityManager em;

    private final Class<T> persistentClass;

    protected GenericDao(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @Override
    public Optional<T> find(long id) {
        T item = em.find(persistentClass, id);
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<T> find(T item) {
        return find(item.getId());
    }

    @Override
    public List<T> findAll() {
        String queryName = persistentClass.getSimpleName() + ".findAll";
        return em.createNamedQuery(queryName, persistentClass).getResultList();
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
    public void destroy(T item) {
        find(item).ifPresent(persistedItem -> {
            em.remove(persistedItem);
            em.flush();
        });
    }
}
