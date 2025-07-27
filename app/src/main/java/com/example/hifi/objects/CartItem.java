package com.example.hifi.objects;

public class CartItem {
    private final Product product;
    private int quantity;
    
    public CartItem(Product product) {
        this(product, 1);
    }
    
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        if (quantity <= 0) return;
        this.quantity = quantity;
    }
    
    public double getCost() {
        return product.getProductPrice() * quantity;
    }
}
