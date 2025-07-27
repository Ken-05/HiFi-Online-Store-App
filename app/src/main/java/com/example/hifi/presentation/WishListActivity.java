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
import com.example.hifi.objects.Cart;
import com.example.hifi.objects.User;

public class WishListActivity extends MenuActivity {
    private AccessCart accessCart;
    private Cart wishList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cart);

        accessCart = new AccessCart();
        wishList = getCart();

        if (wishList == null) {
            TextView noAccountTextView = findViewById(R.id.no_account);
            noAccountTextView.setVisibility(View.VISIBLE);
            return;
        }

        setupCartItemListAdapter();
        updateAll();
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            if (wishList != null) {
                accessCart.saveCart(wishList);
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
        assert wishList != null;

        CartItemListAdapter adapter = new CartItemListAdapter(getApplicationContext(),
                R.layout.cart_item, wishList.getItems(),
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
            noItemsTextView.setVisibility(wishList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    private void updateCheckoutInfo() {
        assert wishList != null;

        TextView cartQuantity = findViewById(R.id.cart_quantity);
        TextView cartSubtotal = findViewById(R.id.cart_subtotal);
        Button goToCheckoutButton = findViewById(R.id.go_to_checkout);

        if (cartQuantity != null)
            cartQuantity.setText(
                    String.format(getString(R.string.cart_item_quantity), wishList.getQuantity()));
        if (cartSubtotal != null)
            cartSubtotal.setText(
                    String.format(getString(R.string.cart_subtotal), wishList.getCost()));
        if (goToCheckoutButton != null)
            goToCheckoutButton.setEnabled(!wishList.isEmpty());
    }

    private void updateAll() {
        updateCartEmptyText();
        updateCheckoutInfo();
    }
}
