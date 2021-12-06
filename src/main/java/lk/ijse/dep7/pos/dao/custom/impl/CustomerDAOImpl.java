package lk.ijse.dep7.pos.dao.custom.impl;

import lk.ijse.dep7.pos.dao.CrudDAOImpl;
import lk.ijse.dep7.pos.dao.custom.CustomerDAO;
import lk.ijse.dep7.pos.entity.Customer;
import org.hibernate.Session;

public class CustomerDAOImpl extends CrudDAOImpl<Customer, String> implements CustomerDAO {

    private final Session session;

    public CustomerDAOImpl(Session session) {
        super(session);
        this.session = session;
    }

    @Override
    public String getLastCustomerId() {
        session.flush();
        return (String) session.createNativeQuery("SELECT id FROM customer ORDER BY id DESC LIMIT 1").uniqueResult();
    }
}
