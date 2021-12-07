package lk.ijse.dep7.pos.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

class JPAUtilTest {

    @Test
    void getEntityManagerFactory() {
        EntityManagerFactory entityManagerFactory = JPAUtil.getEntityManagerFactory();
        assertNotNull(entityManagerFactory);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        assertNotNull(entityManager);
    }
}