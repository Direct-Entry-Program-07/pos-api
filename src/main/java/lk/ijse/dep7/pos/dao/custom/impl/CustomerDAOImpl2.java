package lk.ijse.dep7.pos.dao.custom.impl;

import lk.ijse.dep7.pos.dao.CrudDAOImpl;
import lk.ijse.dep7.pos.dao.custom.CustomerDAO;
import lk.ijse.dep7.pos.entity.Customer;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl2 extends CrudDAOImpl<Customer, String> implements CustomerDAO {

    private final Session session;

    public CustomerDAOImpl2(Session session) {
        this.session = session;
    }

//    @Override
//    public void save(Customer entity) {
//        session.save(entity);
//    }
//
//    @Override
//    public void update(Customer entity) {
//        session.update(entity);
//    }
//
//    @Override
//    public void deleteById(String key) {
//        Customer customer = session.get(Customer.class, key);
//        session.delete(customer);
//    }
//
//    @Override
//    public Optional<Customer> findById(String key) {
//        Customer customer = session.find(Customer.class, key);
//        if (customer == null) {
//            return Optional.empty();
//        } else {
//            return Optional.of(customer);
//        }
//    }
//
//    @Override
//    public List<Customer> findAll() {
//        return session.createQuery("FROM Customer").list();
//    }
//
//    @Override
//    public long count() {
//        return session.createQuery("SELECT COUNT(c) FROM Customer c", Long.class).uniqueResult();
//    }
//
//    @Override
//    public boolean existsById(String key) {
//        return findById(key).isPresent();
//    }
//
//    @Override
//    public List<Customer> findAll(int page, int size) {
//        return session.createNativeQuery("SELECT * FROM customer LIMIT ?1 OFFSET ?2")
//                .setParameter(2, size * (page - 1))
//                .setParameter(1, size).addEntity(Customer.class).list();
//    }

    @Override
    public String getLastCustomerId() {
        session.flush();
        return (String) session.createNativeQuery("SELECT id FROM customer ORDER BY id DESC LIMIT 1").uniqueResult();
    }
}
