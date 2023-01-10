package lk.ijse.dep7.pos.dao.custom.impl;

import lk.ijse.dep7.pos.dao.CrudDAOImpl;
import lk.ijse.dep7.pos.dao.custom.OrderDetailDAO;
import lk.ijse.dep7.pos.entity.OrderDetail;
import lk.ijse.dep7.pos.entity.OrderDetailPK;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class OrderDetailDAOImpl extends CrudDAOImpl<OrderDetail, OrderDetailPK> implements OrderDetailDAO {

    @Override
    public Optional<BigDecimal> findOrderTotal(String orderId)  {
        BigDecimal total = (BigDecimal) em.
                createNativeQuery("SELECT SUM(unit_price * qty) as total FROM order_detail WHERE order_id=?1 GROUP BY order_id;")
                .setParameter(1, orderId).getSingleResult();
        return (total == null) ? Optional.empty() : Optional.of(total);
    }

    @Override
    public List<OrderDetail> findOrderDetailsByOrderId(String orderId)  {
        return em.createNativeQuery("SELECT * FROM `order_detail` WHERE order_id =?1", OrderDetail.class)
                .setParameter(1, orderId).getResultList();
    }

}
