package com.elearning.demo.Model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Products products;


    private int quantity;

    private BigDecimal price;
}
