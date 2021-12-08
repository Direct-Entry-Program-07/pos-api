package lk.ijse.dep7.pos.dao.custom.impl;

import lk.ijse.dep7.pos.dao.custom.CustomerDAO;
import lk.ijse.dep7.pos.entity.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDAOImplTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction tx;
    private CustomerDAO customerDAO;

    @BeforeEach
    void setUp() {
        emf = JPAUtil.getEntityManagerFactory();
        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();
        customerDAO = new CustomerDAOImpl();
        customerDAO.setEntityManager(em);
    }

    @AfterEach
    void tearDown() {
        tx.rollback();
        em.close();
    }

    @Test
    void save() {
        Customer customer = new Customer("C100", "Kasun", "Galle");
        assertDoesNotThrow(() -> customerDAO.save(customer));
    }

    @Test
    void update() {
        save();
        Optional<Customer> c100 = customerDAO.findById("C100");
        assertTrue(c100.isPresent());
        Customer customer = c100.get();
        customer.setAddress("Matara");
        customerDAO.update(customer);
        assertTrue(customerDAO.findById("C100").get().getAddress().equals("Matara"));
    }

    @Test
    void deleteById() {
        save();
        assertDoesNotThrow(() -> customerDAO.deleteById("C100"));
        assertFalse(customerDAO.findById("C100").isPresent());
    }

    @Test
    void findById() {
        save();
        assertTrue(customerDAO.findById("C100").isPresent());
    }

    @Test
    void findAll() {
        save();
        Customer manoj = new Customer("C101", "Manoj", "Kadawatha");
        customerDAO.save(manoj);
        assertTrue(customerDAO.findAll().size() >= 2);
        customerDAO.findAll().forEach(System.out::println);
    }

    @Test
    void count() {
        save();
        long count = customerDAO.count();
        System.out.println(count);
        assertTrue(count >= 1);
    }

    @Test
    void existsById() {
        save();
        assertTrue(customerDAO.existsById("C100"));
    }

    @Test
    void testFindAll() {
        Customer manoj = new Customer("C100", "Manoj", "Kadawatha");
        Customer pethum = new Customer("C101", "pethum", "Kadawatha");
        Customer dhanushka = new Customer("C102", "dhanushka", "Kadawatha");
        customerDAO.save(manoj);
        customerDAO.save(pethum);
        customerDAO.save(dhanushka);
        List<Customer> customers = customerDAO.findAll(1, 2);
        customers.forEach(System.out::println);
        assertTrue(customers.size() == 2);
    }

    @Test
    void getLastCustomerId() {
//        save();
//        String lastCustomerId = customerDAO.getLastCustomerId();
//        customerDAO.findAll().forEach(System.out::println);
//        System.out.println(lastCustomerId);
//        assertNotNull(lastCustomerId);
//        session.createQuery("DELETE FROM OrderDetail  od").executeUpdate();
//        session.createQuery("DELETE FROM Order o").executeUpdate();
//        int affectedRows = session.createQuery("DELETE FROM Customer c").executeUpdate();
//        assertTrue(affectedRows > 0);
//        lastCustomerId = customerDAO.getLastCustomerId();
//        System.out.println(lastCustomerId);
//        assertNull(lastCustomerId);
    }
}