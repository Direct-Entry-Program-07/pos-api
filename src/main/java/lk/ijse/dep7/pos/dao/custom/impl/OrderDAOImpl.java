package lk.ijse.dep7.pos.dao.custom.impl;

import lk.ijse.dep7.pos.dao.CrudDAOImpl;
import lk.ijse.dep7.pos.dao.custom.OrderDAO;
import lk.ijse.dep7.pos.entity.Order;
import org.hibernate.Session;

public class OrderDAOImpl extends CrudDAOImpl<Order, String> implements OrderDAO {

    private final Session session;

    public OrderDAOImpl(Session session) {
        super(session);
        this.session = session;
    }

    @Override
    public String getLastOrderId() throws Exception {
        return (String) session.createNativeQuery("SELECT id FROM `order` ORDER BY id DESC LIMIT 1").uniqueResult();
    }
}
