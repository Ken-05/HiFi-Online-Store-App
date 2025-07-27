package com.example.hifi.persistence;

import com.example.hifi.objects.CartItem;

import java.util.List;

public interface CartItemPersistence {
    List<CartItem> getUserCartItems(String userId);
    void mergeCartItems(String userId, List<CartItem> cartItems);
}
