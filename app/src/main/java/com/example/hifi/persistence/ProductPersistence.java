package com.example.hifi.persistence;

import java.util.List;

import com.example.hifi.objects.Product;

public interface ProductPersistence {
    List<Product> getProductSequential();
    
    Product getProductById(String productId);

    Product insertProduct(final Product product);

    Product updateProduct(final Product product);

    void deleteProduct(final Product product);
}
