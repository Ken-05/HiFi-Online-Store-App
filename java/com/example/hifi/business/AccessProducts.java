package com.example.hifi.business;

import java.util.Collections;
import java.util.List;

import com.example.hifi.application.Services;
import com.example.hifi.objects.Product;
import com.example.hifi.persistence.ProductPersistence;

public class AccessProducts {
    private final ProductPersistence productPersistence;
    private List<Product> products;
    
    public AccessProducts() {
        this(Services.getProductPersistence());
    }
    
    public AccessProducts(final ProductPersistence productPersistence) {
        products = null;
        this.productPersistence = productPersistence;
    }
    
    public List<Product> getProducts() {
        products = productPersistence.getProductSequential();
        return Collections.unmodifiableList(products);
    }
    
    public Product getProductById(String id) {
        try {
            return productPersistence.getProductById(id);
        } catch (Exception e) {
            return null;
        }
    }
}
