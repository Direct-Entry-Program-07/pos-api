package lk.ijse.dep7.pos.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JPAUtilTest {

    @Test
    void getSessionFactory() {
        SessionFactory sessionFactory = JPAUtil.getSessionFactory();
        assertNotNull(sessionFactory);
        Session session = sessionFactory.openSession();
        assertNotNull(session);
        session.close();
        sessionFactory.close();
    }
}