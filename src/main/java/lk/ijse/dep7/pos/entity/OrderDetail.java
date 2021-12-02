package lk.ijse.dep7.pos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "order_detail")
@Entity
public class OrderDetail implements SuperEntity {
    @EmbeddedId
    private OrderDetailPK orderDetailPK;
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;
    @Column(nullable = false)
    private int qty;

    public OrderDetail(String orderId, String itemCode, BigDecimal unitPrice, int qty) {
        this.orderDetailPK = new OrderDetailPK(orderId, itemCode);
        this.unitPrice = unitPrice;
        this.qty = qty;
    }
}
