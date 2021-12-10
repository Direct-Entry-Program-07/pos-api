package lk.ijse.dep7.pos.api2;

import lk.ijse.dep7.pos.dto.OrderDTO;
import lk.ijse.dep7.pos.service.custom.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RequestMapping("/api/v2/orders")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"page", "size"})
    public ResponseEntity<List<OrderDTO>> getAllOrdersByPageAndSize(@RequestParam int page,
                                                                   @RequestParam int size,
                                                                   @RequestParam(name="q", required = false) String query) {

        if (page <= 0 || size <=0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid page or size");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Count", orderService.getSearchOrdersCount(query == null? "": query) + "");
        return new ResponseEntity<List<OrderDTO>>(orderService.searchOrders(query == null? "": query, page, size), headers, HttpStatus.OK);
    }

    @GetMapping(value = "/{id:OD\\d{3}}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDTO getOrder(@PathVariable String id) {
        return orderService.searchOrder(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveOrder(@RequestBody OrderDTO order) {

        if (order.getOrderId() == null || !order.getOrderId().matches("OD\\d{3}")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid order id");
        }else if (order.getOrderDate() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid order date");
        }else if (order.getCustomerId() == null || !order.getCustomerId().matches("C\\d{3}")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer id");
        }else if (order.getOrderDetails() == null || order.getOrderDetails().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order can't be saved without any order detail");
        }else if (order.getOrderDetails().stream().anyMatch(od ->
            od.getItemCode() == null || !od.getItemCode().matches("I\\d{3}") ||
               od.getUnitPrice() == null || od.getUnitPrice().compareTo(new BigDecimal(0)) <= 0 ||
               od.getQty() <= 0)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid order details");
        }

        orderService.saveOrder(order);
        return order.getOrderId();
    }

}
