package lk.ijse.dep7.pos.dao.custom.impl;

import lk.ijse.dep7.pos.dao.custom.CustomerDAO;
import lk.ijse.dep7.pos.entity.Customer;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl2 implements CustomerDAO {

    private Session session;

    public CustomerDAOImpl2(Session session) {
        this.session = session;
    }

    @Override
    public void save(Customer entity) throws Exception {
        session.save(entity);
    }

    @Override
    public void update(Customer entity) throws Exception {
        session.update(entity);
    }

    @Override
    public void deleteById(String key) throws Exception {
        Customer customer = session.get(Customer.class, key);
        session.delete(customer);
    }

    @Override
    public Optional<Customer> findById(String key) throws Exception {
        Customer customer = session.find(Customer.class, key);
        if (customer == null){
            return Optional.empty();
        }else{
            return Optional.of(customer);
        }
    }

    @Override
    public List<Customer> findAll() throws Exception {
        return session.createQuery("FROM Customer").list();
    }

    @Override
    public long count() throws Exception {
        return session.createQuery("SELECT COUNT(c) FROM Customer c", Long.class).uniqueResult();
    }

    @Override
    public boolean existsById(String key) throws Exception {
        return findById(key).isPresent();
    }

    @Override
    public List<Customer> findAll(int page, int size) throws Exception {
        return session.createNativeQuery("SELECT * FROM customer LIMIT ?1 OFFSET ?2")
                .setParameter(1, page)
                .setParameter(2, size).addEntity(Customer.class).list();
    }

    @Override
    public String getLastCustomerId() throws Exception {
        return session.createNativeQuery("SELECT id FROM customer ORDER BY id DESC LIMIT 1", String.class).uniqueResult();
    }
}
