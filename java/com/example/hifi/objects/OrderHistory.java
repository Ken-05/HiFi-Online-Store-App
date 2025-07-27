package com.example.hifi.objects;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class OrderHistory {
    private final String userId;
    private final List<OrderHistoryRecord> recordList;
    
    public OrderHistory(String userId) {
        this.userId = userId;
        this.recordList = new ArrayList<>();
    }
    
    public OrderHistory(String userId, List<OrderHistoryRecord> recordList) {
        this(userId);
        this.recordList.addAll(recordList);
    }
    
    public String getUserId() {
        return userId;
    }
    
    public boolean isEmpty() {
        return recordList.isEmpty();
    }
    
    public void addProducts(List<Product> products) {
        for (Product product : products) {
            addProduct(product);
        }
    }
    
    public void addProduct(Product product) {
        this.recordList.add(
                new OrderHistoryRecord(product,
                        new Date(new java.util.Date().getTime()))
        );
    }
    
    public List<OrderHistoryRecord> getRecordList() {
        return recordList;
    }
}
