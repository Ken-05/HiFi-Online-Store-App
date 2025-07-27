package com.example.hifi.presentation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.hifi.R;
import com.example.hifi.objects.Product;

import java.util.List;

public class ProductListAdapter extends ArrayAdapter<Product> {
    private final int resource;
    private final List<Product> products;
    private final OnProductClick onProductClick;
    
    public ProductListAdapter(@NonNull Context context, int resource, List<Product> products, OnProductClick onProductClick) {
        super(context, resource);
        this.resource = resource;
        this.products = products;
        this.onProductClick = onProductClick;
    }
    
    @Override
    public int getCount() {
        return products.size();
    }
    
    @SuppressLint("DiscouragedApi")
    @Override
    public View getView(int index, View view, ViewGroup parent) {
        Product product;
        
        try {
            product = products.get(index);
            if (product == null) return null;
            
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        
        int imageResource = product.getProductImage();
        
        if (imageResource <= 0) {
            imageResource = getContext().getResources().getIdentifier(
                    product.getProductImageName(), "drawable", getContext().getPackageName());
        }
        
        ImageView image = view.findViewById(R.id.product_image);
        TextView name = view.findViewById(R.id.product_name);
        TextView description = view.findViewById(R.id.product_description);
        TextView price = view.findViewById(R.id.result_price);
        RatingBar rating = view.findViewById(R.id.product_rating);
        
        if (image != null) image.setImageResource(imageResource);
        if (name != null) name.setText(product.getProductName());

        if (description != null) {
            String totalDes = product.getProductDes();
            int period_index = totalDes.indexOf(".");
            String shortDes = totalDes.substring(0, period_index + 1);
            description.setText(shortDes);
        }
        if (price != null)
            price.setText(String.format(getContext().getString(R.string.price_format),
                    product.getProductPrice()));
        if (rating != null) rating.setRating(product.getRating());
        
        view.setOnClickListener(v -> onProductClick.onClick(product));
        
        return view;
    }
    
    interface OnProductClick {
        void onClick(Product product);
    }
}
