package com.example.hifi.presentation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.hifi.R;
import com.example.hifi.application.Services;
import com.example.hifi.business.AccessCart;
import com.example.hifi.business.AccessProducts;
import com.example.hifi.objects.Cart;
import com.example.hifi.objects.Product;
import com.example.hifi.objects.User;

public class ProductDescriptionActivity extends MenuActivity {
    private AccessProducts accessProducts;
    private AccessCart accessCart;
    private Product product;
    private Cart cart;

    private Cart wishList;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // load UI layer first and find the id of each piece
        setContentView(R.layout.activity_item_description);
        
        accessProducts = new AccessProducts();
        accessCart = new AccessCart();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    
        getProductInfo();
    }
    
    private Product getProduct() {
        String productId = getIntent().getStringExtra("productID");
        
        if (productId == null || productId.length() == 0) {
            throw new IllegalArgumentException("productId was not supplied to ProductDescriptionActivity");
        }
        
        return accessProducts.getProductById(productId);
    }
    
    private Cart getCart() {
        User currentUser = Services.getUserService().getCurrentUser();
        
        if (currentUser == null) {
            return null;
        }
        
        return accessCart.getUserCart(currentUser.getUserId());
    }
    
    private void getProductInfo() {
        if (product == null)
            product = getProduct();
        
        if (product == null) {
            // product could not be found, display error text
            displayErrorText();
            return;
        }
        
        cart = getCart();
        setViewData(product);
    }
    
    @SuppressLint("DiscouragedApi")
    private void setViewData(final Product product) {
        ImageView productImageView = findViewById(R.id.imageView);
        TextView productNameView = findViewById(R.id.product_name);
        TextView productDescriptionView = findViewById(R.id.product_description);
        TextView productPriceView = findViewById(R.id.product_price);
        Button addToCartButton = findViewById(R.id.add_to_cart);
        
        int productImageResource = product.getProductImage();
        if (productImageResource == 0) {
            productImageResource = getResources().getIdentifier(
                    product.getProductImageName(), "drawable", getPackageName());
        }
        
        String productPriceText = String.format(getString(R.string.price_format),
                product.getProductPrice());
        
        if (productImageView != null)
            productImageView.setImageResource(productImageResource);
        if (productNameView != null)
            productNameView.setText(product.getProductName());
        if (productDescriptionView != null)
            productDescriptionView.setText(product.getProductDes());
        if (productPriceView != null)
            productPriceView.setText(productPriceText);
        
        if (cart != null && cart.isProductInCart(product)) {
            if (addToCartButton != null) {
                addToCartButton.setEnabled(false);
                addToCartButton.setText(R.string.in_cart);
            }
        }
    }
    
    private void displayErrorText() {
        View productDetailsView = findViewById(R.id.product_details);
        View productErrorView = findViewById(R.id.product_error);
        
        if (productDetailsView != null)
            productDetailsView.setVisibility(View.GONE);
        if (productErrorView != null)
            productErrorView.setVisibility(View.VISIBLE);
    }
    
    public void addToCart(View view) {
        assert product != null;
        
        if (cart == null) {
            Toast.makeText(this, R.string.add_to_cart_no_account, Toast.LENGTH_SHORT).show();
            gotoSignIn();
            return;
        }
        
        try {
            cart.addProduct(product, 1);
            accessCart.saveCart(cart);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Toast.makeText(this, R.string.error_add_to_cart, Toast.LENGTH_LONG).show();
            return;
        }
        
        Toast.makeText(
                this,
                String.format(getString(R.string.added_product_to_cart), product.getProductName()),
                Toast.LENGTH_SHORT).show();
        
        if (view instanceof TextView) {
            // TextView is a super class of Button
            TextView textView = (TextView) view;
            
            textView.setEnabled(false);
            textView.setText(R.string.added_to_cart);
        }
    }

    public void addToCart2(View view) {
        assert product != null;

        if (wishList == null) {
            Toast.makeText(this, R.string.add_to_cart_no_account, Toast.LENGTH_SHORT).show();
            gotoSignIn();
            return;
        }

        try {
            wishList.addProduct(product, 1);
            accessCart.saveCart(wishList);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Toast.makeText(this, R.string.error_add_to_cart, Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(
                this,
                String.format(getString(R.string.added_product_to_cart), product.getProductName()),
                Toast.LENGTH_SHORT).show();

        if (view instanceof TextView) {
            // TextView is a super class of Button
            TextView textView = (TextView) view;

            textView.setEnabled(false);
            textView.setText(R.string.added_to_cart);
        }
    }
    
    private void gotoSignIn() {
        Intent signInActivityIntent = new Intent(this, UserSignInActivity.class);
        startActivity(signInActivityIntent);
    }
}
