package lk.ijse.dep7.pos.service.custom;

import lk.ijse.dep7.pos.dto.OrderDTO;
import lk.ijse.dep7.pos.service.SuperService;

import java.util.List;

public interface OrderService extends SuperService {

    void saveOrder(OrderDTO order) ;

    long getSearchOrdersCount(String query) ;

    List<OrderDTO> searchOrders(String query, int page, int size) ;

    OrderDTO searchOrder(String orderId) ;

    String generateNewOrderId() ;
}
