package com.example.hifi.presentation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.hifi.R;
import com.example.hifi.objects.CartItem;
import com.example.hifi.objects.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class CartItemListAdapter extends ArrayAdapter<Product> {
    private final int layoutResource;
    private final CartItemsChangedCallback cartItemsChangedCallback;
    private final Collection<CartItem> items;
    private List<CartItem> itemList;
    
    public CartItemListAdapter(@NonNull Context context, int layoutResource, Collection<CartItem> items,
                               CartItemsChangedCallback cartItemsChangedCallback) {
        super(context, layoutResource);
        this.layoutResource = layoutResource;
        this.items = items;
        this.cartItemsChangedCallback = cartItemsChangedCallback;
        
        itemList = new ArrayList<>(items);
    }
    
    @Override
    public int getCount() {
        return items.size();
    }
    
    @SuppressLint("DiscouragedApi")
    @Override
    public View getView(int index, View view, ViewGroup parent) {
        CartItem item;
        
        try {
            item = itemList.get(index);
            if (item == null) return null;
            
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(layoutResource, parent, false);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        
        int imageResource = item.getProduct().getProductImage();
        
        if (imageResource == 0) {
            imageResource = getContext().getResources().getIdentifier(
                    item.getProduct().getProductImageName(), "drawable", getContext().getPackageName());
        }
        
        ImageView image = view.findViewById(R.id.product_image);
        TextView name = view.findViewById(R.id.product_name);
        TextView description = view.findViewById(R.id.product_description);
        TextView price = view.findViewById(R.id.result_price);
        RatingBar rating = view.findViewById(R.id.product_rating);
        
        if (image != null) image.setImageResource(imageResource);
        if (name != null) name.setText(item.getProduct().getProductName());
        if (description != null) description.setText(item.getProduct().getProductDes());
        if (price != null)
            price.setText(String.format(getContext().getString(R.string.price_format),
                    item.getProduct().getProductPrice()));
        if (rating != null) rating.setRating(item.getProduct().getRating());
        
        ConstraintLayout options = view.findViewById(R.id.options_container);
        
        if (options != null) {
            TextView quantity = options.findViewById(R.id.item_quantity);
            ImageButton removeItemButton = options.findViewById(R.id.item_remove);
            ImageButton addToQuantityButton = options.findViewById(R.id.quantity_add);
            ImageButton subtractFromQuantityButton = options.findViewById(R.id.quantity_subtract);
            
            if (quantity != null)
                quantity.setText(String.format(Locale.ENGLISH, "%d", item.getQuantity()));
            if (removeItemButton != null)
                removeItemButton.setOnClickListener(v -> {
                    items.remove(item);
                    notifyDataSetChanged();
                    cartItemsChangedCallback.onChange(itemList);
                });
            if (addToQuantityButton != null)
                addToQuantityButton.setOnClickListener(v -> {
                    item.setQuantity(Math.min(item.getQuantity() + 1, 99));
                    notifyDataSetChanged();
                    cartItemsChangedCallback.onChange(itemList);
                });
            if (subtractFromQuantityButton != null)
                subtractFromQuantityButton.setOnClickListener(v -> {
                    item.setQuantity(Math.max(item.getQuantity() - 1, 1));
                    notifyDataSetChanged();
                    cartItemsChangedCallback.onChange(itemList);
                });
        }
        
        return view;
    }
    
    @Override
    public void notifyDataSetChanged() {
        itemList = new ArrayList<>(items);
        super.notifyDataSetChanged();
    }
    
    interface CartItemsChangedCallback {
        void onChange(List<CartItem> items);
    }
}
