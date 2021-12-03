package lk.ijse.dep7.pos.dao.custom.impl;

import lk.ijse.dep7.pos.dao.HibernateUtil;
import lk.ijse.dep7.pos.dao.custom.CustomerDAO;
import lk.ijse.dep7.pos.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDAOImpl2Test {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction tx;
    private CustomerDAO customerDAO;

    @BeforeEach
    void setUp() {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        customerDAO = new CustomerDAOImpl2(session);
    }

    @AfterEach
    void tearDown() {
        tx.rollback();
        session.close();
    }

    @Test
    void save() {
        Customer customer = new Customer("C100", "Kasun", "Galle");
        assertDoesNotThrow(()-> customerDAO.save(customer));
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void count() {
    }

    @Test
    void existsById() {
    }

    @Test
    void testFindAll() {
    }

    @Test
    void getLastCustomerId() {
    }
}