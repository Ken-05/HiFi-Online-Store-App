package com.example.hifi.tests.business;

import com.example.hifi.business.SearchProducts;
import com.example.hifi.objects.Product;
import com.example.hifi.objects.SearchResult;
import com.example.hifi.persistence.ProductPersistence;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class SearchProductsTest {
    private final ProductPersistence productPersistence;
    private final SearchProducts searchProducts;
    
    public SearchProductsTest() {
        productPersistence = mock(ProductPersistence.class);
        
        List<Product> productList = new ArrayList<>();
        
        {
            Product product = new Product("123") {{
                setProductName("This apple product is a test");
            }};
            
            when(productPersistence.getProductById("123")).thenReturn(product);
            
            productList.add(product);
        }
        
        {
            Product product = new Product("51212312") {{
                setProductName("Apple MacBook X Mini Pro Elite");
                setProductDes("The best MacBook ever created");
                setProductPrice(20000);
            }};
            
            when(productPersistence.getProductById("51212312")).thenReturn(product);
            
            productList.add(product);
        }
        
        when(productPersistence.getProductSequential()).thenReturn(productList);
        
        searchProducts = new SearchProducts(productPersistence);
    }
    
    @Test
    public void testSearchProductName1() {
        List<SearchResult<Product>> searchResults = searchProducts.searchProducts("this");
        Product expectedProduct = productPersistence.getProductById("123");
        
        System.out.println("Starting testSearchProductName1");
        
        Assert.assertEquals(1, searchResults.size());
        Assert.assertEquals(expectedProduct, searchResults.get(0).getValue());
        
        System.out.println("Finished testSearchProductName1\n");
    }
    
    @Test
    public void testSearchProductName2() {
        List<SearchResult<Product>> searchResults = searchProducts.searchProducts("macboOk"); // macbook is in the product's name
        Product expectedProduct = productPersistence.getProductById("51212312");
        
        System.out.println("Starting testSearchProductName2");
        
        Assert.assertNotNull(searchResults);
        Assert.assertEquals(1, searchResults.size());
        Assert.assertEquals(expectedProduct, searchResults.get(0).getValue());
        
        System.out.println("Finished testSearchProductName2\n");
    }
    
    @Test
    public void testSearchProductDescription() {
        List<SearchResult<Product>> searchResults = searchProducts.searchProducts("created"); // created is in the products description
        Product expectedProduct = productPersistence.getProductById("51212312");
        
        System.out.println("Starting testSearchDescription");
        
        Assert.assertNotNull(searchResults);
        Assert.assertEquals(1, searchResults.size());
        Assert.assertEquals(expectedProduct, searchResults.get(0).getValue());
        
        System.out.println("Finished testSearchDescription\n");
    }
    
    @Test
    public void testSearchGetMultipleProducts() {
        // apple is in different casing in 2 product's titles
        List<SearchResult<Product>> searchResults = searchProducts.searchProducts("APPLE");
        
        System.out.println("Starting testSearchGetMultipleProducts");
        
        Assert.assertNotNull(searchResults);
        Assert.assertEquals(2, searchResults.size());
        
        System.out.println("Finished testSearchGetMultipleProducts\n");
    }
    
    @Test
    public void testEmptyAndNullSearch() {
        List<SearchResult<Product>> searchResults;
        
        System.out.println("Starting testEmptyAndNullSearch");
        
        searchResults = searchProducts.searchProducts(null);
        
        Assert.assertNotNull(searchResults);
        Assert.assertEquals(0, searchResults.size());
        
        searchResults = searchProducts.searchProducts("");
        
        Assert.assertNotNull(searchResults);
        Assert.assertEquals(0, searchResults.size());
        
        System.out.println("Finished testEmptyAndNullSearch\n");
    }
}
