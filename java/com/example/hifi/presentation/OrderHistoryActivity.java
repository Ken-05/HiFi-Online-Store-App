package com.example.hifi.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hifi.R;
import com.example.hifi.application.Services;
import com.example.hifi.business.AccessOrderHistory;
import com.example.hifi.objects.OrderHistory;
import com.example.hifi.objects.User;

public class OrderHistoryActivity extends MenuActivity {
    private AccessOrderHistory accessOrderHistory;
    private OrderHistory orderHistory;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_order_history);
        
        accessOrderHistory = new AccessOrderHistory();
        orderHistory = getOrderHistory();
        
        if (orderHistory == null) {
            TextView noAccountTextView = findViewById(R.id.no_account);
            noAccountTextView.setVisibility(View.VISIBLE);
            return;
        }
        
        if (orderHistory.isEmpty()) {
            TextView noItemsTextView = findViewById(R.id.no_items);
            noItemsTextView.setVisibility(View.VISIBLE);
        }
        
        setupOrderHistoryRecordListAdapter();
    }
    
    private OrderHistory getOrderHistory() {
        User user = Services.getUserService().getCurrentUser();
        
        if (user == null) {
            return null;
        }
        
        return accessOrderHistory.getOrderHistory(user.getUserId());
    }
    
    private void setupOrderHistoryRecordListAdapter() {
        assert orderHistory != null;
        
        OrderHistoryRecordListAdapter adapter = new OrderHistoryRecordListAdapter(
                getApplicationContext(),
                R.layout.order_history_item,
                orderHistory.getRecordList());
        
        ListView listView = findViewById(R.id.order_history_items);
        listView.setAdapter(adapter);
    }
}
