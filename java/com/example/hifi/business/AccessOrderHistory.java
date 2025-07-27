package com.example.hifi.business;

import com.example.hifi.application.Services;
import com.example.hifi.objects.OrderHistory;
import com.example.hifi.objects.OrderHistoryRecord;
import com.example.hifi.persistence.OrderHistoryPersistence;

import java.util.ArrayList;
import java.util.List;

public class AccessOrderHistory {
    private final OrderHistoryPersistence orderHistoryPersistence;
    
    public AccessOrderHistory(OrderHistoryPersistence orderHistoryPersistence) {
        this.orderHistoryPersistence = orderHistoryPersistence;
    }
    
    public AccessOrderHistory() {
        this(Services.getOrderHistoryPersistence());
    }
    
    public OrderHistory getOrderHistory(String userId) {
        List<OrderHistoryRecord> orderHistoryRecords =
                orderHistoryPersistence.getOrderHistoryRecords(userId);
        return new OrderHistory(userId, orderHistoryRecords);
    }
    
    public void saveOrderHistory(OrderHistory orderHistory) {
        List<OrderHistoryRecord> orderHistoryRecords = orderHistory.getRecordList();
        orderHistoryPersistence.addOrderHistoryRecords(orderHistory.getUserId(), orderHistoryRecords);
    }
}
