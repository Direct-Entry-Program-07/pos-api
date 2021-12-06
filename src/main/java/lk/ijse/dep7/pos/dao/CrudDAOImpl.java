package lk.ijse.dep7.pos.dao;

import lk.ijse.dep7.pos.entity.SuperEntity;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class CrudDAOImpl<T extends SuperEntity, ID extends Serializable> implements CrudDAO<T, ID> {

    private Session session;

    @Override
    public void save(T entity) {
        session.save(entity);
    }

    @Override
    public void update(T entity) {
        session.update(entity);
    }

    @Override
    public void deleteById(ID key) {
//        Customer customer = session.get(Customer.class, key);
//        session.delete(customer);
//
//        Item item = session.get(Item.class, key);
//        session.delete(item);

        T entity = session.get(T, key);
        session.delete(entity);
    }

    @Override
    public Optional<T> findById(ID key) {
//        Customer customer = session.find(Customer.class, key);
//        if (customer == null) {
//            return Optional.empty();
//        } else {
//            return Optional.of(customer);
//        }

//        Item item = session.find(Item.class, key);
//        if (item == null){
//            return Optional.empty();
//        }else{
//            return Optional.of(item);
//        }

        T entity = session.get(T, key);
        if (entity == null){
            return Optional.empty();
        }else{
            return Optional.of(entity);
        }
    }

    @Override
    public List<T> findAll() {
//        return session.createQuery("FROM Customer").list();
//        return session.createQuery("FROM Item i").list();

        return session.createQuery("FROM " + T).list();
    }

    @Override
    public long count() {
//        return session.createQuery("SELECT COUNT(c) FROM Customer c", Long.class).uniqueResult();
//        return session.createQuery("SELECT COUNT(i) FROM Item i", Long.class).uniqueResult();

        return session.createQuery("SELECT COUNT(e) FROM T e", Long.class).uniqueResult();
    }

    @Override
    public boolean existsById(ID key) {
        return findById(key).isPresent();
    }

    @Override
    public List<T> findAll(int page, int size) {
//        return session.createNativeQuery("SELECT * FROM customer LIMIT ?1 OFFSET ?2")
//                .setParameter(2, size * (page - 1))
//                .setParameter(1, size)
//                .addEntity(Customer.class)
//                .list();

//        return session.createNativeQuery("SELECT * FROM item LIMIT ?1 OFFSET ?2")
//                .setParameter(1, size)
//                .setParameter(2, size * (page - 1))
//                .addEntity(Item.class)
//                .list();

//        return session.createNativeQuery("SELECT * FROM T LIMIT ?1 OFFSET ?2")
//                .setParameter(1, size)
//                .setParameter(2, size * (page - 1))
//                .addEntity(T)
//                .list();

        return session.createQuery("FROM T")
                .setFirstResult(size * (page - 1))
                .setMaxResults(size)
                .list();
    }
}
