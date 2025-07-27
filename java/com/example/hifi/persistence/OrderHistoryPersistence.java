package com.example.hifi.persistence;

import com.example.hifi.objects.OrderHistoryRecord;

import java.util.List;

public interface OrderHistoryPersistence {
    List<OrderHistoryRecord> getOrderHistoryRecords(String userId);
    void addOrderHistoryRecords(String userId, List<OrderHistoryRecord> orderHistoryRecords);
}
