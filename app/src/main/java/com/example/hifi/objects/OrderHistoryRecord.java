package com.example.hifi.objects;

import java.sql.Date;

public class OrderHistoryRecord {
    private final Product product;
    private final Date orderDate;
    
    public OrderHistoryRecord(Product product, Date orderDate) {
        this.product = product;
        this.orderDate = orderDate;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public Date getOrderDate() {
        return orderDate;
    }
}
