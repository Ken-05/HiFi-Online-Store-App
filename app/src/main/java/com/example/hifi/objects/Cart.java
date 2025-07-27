package com.example.hifi.objects;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private final String userId;
    private final Map<String, CartItem> items;
    
    public Cart(String userId) {
        this.userId = userId;
        this.items = new HashMap<>();
    }
    
    public Cart(String userId, List<CartItem> cartItems) {
        this(userId);
        
        for (CartItem cartItem : cartItems) {
            items.put(cartItem.getProduct().getProductID(), cartItem);
        }
    }
    
    private CartItem getItem(Product product) {
        if (items.containsKey(product.getProductID())) {
            return items.get(product.getProductID());
        }
        return null;
    }
    
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    public Collection<CartItem> getItems() {
        return items.values();
    }
    
    public int getQuantity() {
        int quantity = 0;
        
        for (CartItem item : items.values()) {
            quantity += item.getQuantity();
        }
        
        return quantity;
    }
    
    public double getCost() {
        double amount = 0.0;
        
        for (CartItem item : items.values()) {
            amount += item.getCost();
        }
        
        return amount;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void addProduct(Product product, int quantity) {
        CartItem item = getItem(product);
        
        if (item != null) {
            int existingQuantity = item.getQuantity();
            item.setQuantity(existingQuantity + quantity);
            return;
        }
        
        item = new CartItem(product, quantity);
        items.put(item.getProduct().getProductID(), item);
    }
    
    public void removeProduct(Product product) {
        items.remove(product.getProductID());
    }
    
    public void removeAll() {
        items.clear();
    }
    
    public boolean isProductInCart(String productId) {
        return items.containsKey(productId);
    }
    
    public boolean isProductInCart(Product product) {
        return isProductInCart(product.getProductID());
    }
}
