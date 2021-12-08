package lk.ijse.dep7.pos.dao;

import lk.ijse.dep7.pos.entity.SuperEntity;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class CrudDAOImpl<T extends SuperEntity, ID extends Serializable> implements CrudDAO<T, ID> {

    protected EntityManager em;
    private Class<T> entityClzObj;

    public CrudDAOImpl() {
        entityClzObj = (Class<T>)(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
    }

    @PersistenceContext
    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(T entity) {
        em.persist(entity);
    }

    @Override
    public void update(T entity) {
        em.merge(entity);
    }

    @Override
    public void deleteById(ID key) {
        T entity = em.find(entityClzObj, key);
        em.remove(entity);
    }

    @Override
    public Optional<T> findById(ID key) {
        T entity = em.find(entityClzObj, key);
        if (entity == null) {
            return Optional.empty();
        } else {
            return Optional.of(entity);
        }
    }

    @Override
    public List<T> findAll() {
        return em.createQuery(String.format("SELECT e FROM %s e", entityClzObj.getName())).getResultList();
    }

    @Override
    public long count() {
        return em.createQuery("SELECT COUNT(e) FROM " + entityClzObj.getName() + " e", Long.class).getSingleResult();
    }

    @Override
    public boolean existsById(ID key) {
        return findById(key).isPresent();
    }

    @Override
    public List<T> findAll(int page, int size) {
        return em.createQuery("FROM " + entityClzObj.getName())
                .setFirstResult(size * (page - 1))
                .setMaxResults(size)
                .getResultList();
    }
}
