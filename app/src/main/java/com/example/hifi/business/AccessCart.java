package com.example.hifi.business;

import com.example.hifi.application.Services;
import com.example.hifi.objects.Cart;
import com.example.hifi.objects.CartItem;
import com.example.hifi.persistence.CartItemPersistence;

import java.util.ArrayList;
import java.util.List;

public class AccessCart {
    private final CartItemPersistence cartItemPersistence;
    
    public AccessCart(CartItemPersistence cartItemPersistence) {
        this.cartItemPersistence = cartItemPersistence;
    }
    
    public AccessCart() {
        this(Services.getCartItemPersistence());
    }
    
    public Cart getUserCart(String userId) {
        List<CartItem> cartItems = cartItemPersistence.getUserCartItems(userId);
        return new Cart(userId, cartItems);
    }
    
    public void saveCart(Cart cart) {
        List<CartItem> cartItems = new ArrayList<>(cart.getItems());
        cartItemPersistence.mergeCartItems(cart.getUserId(), cartItems);
    }
}
