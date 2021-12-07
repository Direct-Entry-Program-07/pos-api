package lk.ijse.dep7.pos.dao.custom.impl;

import lk.ijse.dep7.pos.dao.CrudDAOImpl;
import lk.ijse.dep7.pos.dao.custom.OrderDAO;
import lk.ijse.dep7.pos.entity.Order;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class OrderDAOImpl extends CrudDAOImpl<Order, String> implements OrderDAO {

    @Override
    public String getLastOrderId() {
        em.flush();
        return (String) em.createNativeQuery("SELECT id FROM `order` ORDER BY id DESC LIMIT 1").getSingleResult();
    }
}
