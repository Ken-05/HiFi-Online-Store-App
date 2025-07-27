package com.example.hifi.presentation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.hifi.R;
import com.example.hifi.objects.OrderHistoryRecord;

import java.util.List;

public class OrderHistoryRecordListAdapter extends ArrayAdapter<OrderHistoryRecord> {
    private final int layoutResource;
    private final List<OrderHistoryRecord> orderHistoryRecordList;
    
    public OrderHistoryRecordListAdapter(@NonNull Context context, int layoutResource, List<OrderHistoryRecord> orderHistoryRecordList) {
        super(context, layoutResource);
        this.layoutResource = layoutResource;
        this.orderHistoryRecordList = orderHistoryRecordList;
    }
    
    @Override
    public int getCount() {
        return orderHistoryRecordList.size();
    }
    
    @SuppressLint("DiscouragedApi")
    @Override
    public View getView(int index, View view, ViewGroup parent) {
        OrderHistoryRecord record;
        
        try {
            record = orderHistoryRecordList.get(index);
            if (record == null) return null;
            
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(layoutResource, parent, false);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        
        int imageResource = record.getProduct().getProductImage();
        
        if (imageResource == 0) {
            imageResource = getContext().getResources().getIdentifier(
                    record.getProduct().getProductImageName(), "drawable", getContext().getPackageName());
        }
        
        ImageView image = view.findViewById(R.id.product_image);
        TextView name = view.findViewById(R.id.product_name);
        TextView orderDate = view.findViewById(R.id.order_date);
        
        if (image != null) image.setImageResource(imageResource);
        if (name != null) name.setText(record.getProduct().getProductName());
        if (orderDate != null)
            orderDate.setText(String.format(getContext().getString(R.string.ordered_on),
                    record.getOrderDate().toString())
            );
        
        return view;
    }
}
