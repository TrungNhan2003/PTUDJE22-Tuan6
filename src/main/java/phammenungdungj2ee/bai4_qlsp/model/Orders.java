package phammenungdungj2ee.bai4_qlsp.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    private Long totalAmount;

    public Orders() {
    }

    public Orders(Date orderDate, Long totalAmount) {
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }
}