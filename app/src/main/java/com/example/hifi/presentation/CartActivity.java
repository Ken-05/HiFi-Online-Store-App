package com.example.hifi.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hifi.R;
import com.example.hifi.application.Services;
import com.example.hifi.business.AccessCart;
import com.example.hifi.business.AccessOrderHistory;
import com.example.hifi.objects.Cart;
import com.example.hifi.objects.CartItem;
import com.example.hifi.objects.OrderHistory;
import com.example.hifi.objects.Product;
import com.example.hifi.objects.User;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends MenuActivity {
    private AccessCart accessCart;
    private Cart cart;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_cart);
        
        accessCart = new AccessCart();
        cart = getCart();
        
        if (cart == null) {
            TextView noAccountTextView = findViewById(R.id.no_account);
            noAccountTextView.setVisibility(View.VISIBLE);
            return;
        }
        
        Button checkoutButton = findViewById(R.id.go_to_checkout);
        if (checkoutButton != null) {
            checkoutButton.setOnClickListener(v -> checkout());
        }
        
        setupCartItemListAdapter();
        updateAll();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        
        try {
            if (cart != null) {
                accessCart.saveCart(cart);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Toast.makeText(this, R.string.error_cart_save, Toast.LENGTH_LONG).show();
        }
    }
    
    private Cart getCart() {
        User user = Services.getUserService().getCurrentUser();
        
        if (user == null) {
            return null;
        }
        
        return accessCart.getUserCart(user.getUserId());
    }
    
    private void setupCartItemListAdapter() {
        assert cart != null;
        
        CartItemListAdapter adapter = new CartItemListAdapter(getApplicationContext(),
                R.layout.cart_item, cart.getItems(),
                items -> {
                    updateCartEmptyText();
                    updateCheckoutInfo();
                });
        
        ListView listView = findViewById(R.id.cart_items);
        listView.setAdapter(adapter);
    }
    
    private void updateCartEmptyText() {
        TextView noItemsTextView = findViewById(R.id.no_items);
        
        if (noItemsTextView != null)
            noItemsTextView.setVisibility(cart.isEmpty() ? View.VISIBLE : View.GONE);
    }
    
    private void updateCheckoutInfo() {
        assert cart != null;
        
        TextView cartQuantity = findViewById(R.id.cart_quantity);
        TextView cartSubtotal = findViewById(R.id.cart_subtotal);
        Button goToCheckoutButton = findViewById(R.id.go_to_checkout);
        
        if (cartQuantity != null)
            cartQuantity.setText(
                    String.format(getString(R.string.cart_item_quantity), cart.getQuantity()));
        if (cartSubtotal != null)
            cartSubtotal.setText(
                    String.format(getString(R.string.cart_subtotal), cart.getCost()));
        if (goToCheckoutButton != null)
            goToCheckoutButton.setEnabled(!cart.isEmpty());
    }
    
    private void updateAll() {
        updateCartEmptyText();
        updateCheckoutInfo();
    }
    
    private List<Product> getCartProducts(Cart cart) {
        final List<Product> cartProducts = new ArrayList<>();
    
        for (CartItem item : cart.getItems()) {
            cartProducts.add(item.getProduct());
        }
        
        return cartProducts;
    }
    
    public void checkout() {
        if (cart == null || cart.isEmpty())
            return;
        
        AccessOrderHistory accessOrderHistory = new AccessOrderHistory();
        
        User user = Services.getUserService().getCurrentUser();
        OrderHistory orderHistory = accessOrderHistory.getOrderHistory(user.getUserId());
        
        orderHistory.addProducts(getCartProducts(cart));
        cart.removeAll();
        
        accessOrderHistory.saveOrderHistory(orderHistory);
        accessCart.saveCart(cart);
        
        Toast.makeText(this, R.string.cart_ordered, Toast.LENGTH_LONG).show();
        finish();
    }
}
