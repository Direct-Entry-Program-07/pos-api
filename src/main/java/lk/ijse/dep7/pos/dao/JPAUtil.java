package lk.ijse.dep7.pos.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JPAUtil {

    private static final EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {
        InputStream is = JPAUtil.class.getResourceAsStream("/application.properties");
        Properties jpaProps = new Properties();
        try {
            jpaProps.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read configuration properties",e);
        }

        return Persistence.createEntityManagerFactory("pos", jpaProps);
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
