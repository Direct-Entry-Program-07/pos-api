package lk.ijse.dep7.pos.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HibernateUtilTest {

    @Test
    void getSessionFactory() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        assertNotNull(sessionFactory);
        Session session = sessionFactory.openSession();
        assertNotNull(session);
        session.close();
        sessionFactory.close();
    }
}