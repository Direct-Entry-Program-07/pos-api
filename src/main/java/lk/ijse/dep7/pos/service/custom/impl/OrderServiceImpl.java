package lk.ijse.dep7.pos.service.custom.impl;

import lk.ijse.dep7.pos.dao.custom.*;
import lk.ijse.dep7.pos.dto.OrderDTO;
import lk.ijse.dep7.pos.dto.OrderDetailDTO;
import lk.ijse.dep7.pos.entity.Customer;
import lk.ijse.dep7.pos.entity.Item;
import lk.ijse.dep7.pos.entity.Order;
import lk.ijse.dep7.pos.entity.OrderDetail;
import lk.ijse.dep7.pos.service.custom.CustomerService;
import lk.ijse.dep7.pos.service.custom.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static lk.ijse.dep7.pos.service.util.EntityDTOMapper.*;

@Transactional
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderDetailDAO orderDetailDAO;
    @Autowired
    private QueryDAO queryDAO;
    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private CustomerService customerService;

    @Override
    public void saveOrder(OrderDTO order) throws Exception {
        final String orderId = order.getOrderId();
        final String customerId = order.getCustomerId();

        if (orderDAO.existsById(orderId)) {
            throw new RuntimeException("Invalid Order ID." + orderId + " already exists");
        }

        if (!customerService.existCustomer(customerId)) {
            throw new RuntimeException("Invalid Customer ID." + customerId + " doesn't exist");
        }

        orderDAO.save(fromOrderDTO(order));

        for (OrderDetailDTO detail : order.getOrderDetails()) {
            /* We don't need to save order details explicitly here because we have set bi directional relationship between order and order details */
            /* When we save order it automatically saves order details too */
            //orderDetailDAO.save(fromOrderDetailDTO(orderId, detail));

            Item item = itemDAO.findById(detail.getItemCode()).get();
            item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());
            //itemService.updateItem(item);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public long getSearchOrdersCount(String query) throws Exception {
        return queryDAO.countOrders(query);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public List<OrderDTO> searchOrders(String query, int page, int size) throws Exception {
        return toOrderDTO2(queryDAO.findOrders(query, page, size));
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public OrderDTO searchOrder(String orderId) throws Exception {
        Order order = orderDAO.findById(orderId).orElseThrow(() -> new RuntimeException("Invalid Order ID: " + orderId));
        Customer customer = customerDAO.findById(order.getCustomer().getId()).get();
        BigDecimal orderTotal = orderDetailDAO.findOrderTotal(orderId).get();
        List<OrderDetail> orderDetails = orderDetailDAO.findOrderDetailsByOrderId(orderId);
        return toOrderDTO(order, customer, orderTotal, orderDetails);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public String generateNewOrderId() throws Exception {
        String id = orderDAO.getLastOrderId();
        if (id != null) {
            return String.format("OD%03d", (Integer.parseInt(id.replace("OD", "")) + 1));
        } else {
            return "OD001";
        }
    }
}
