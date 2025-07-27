package com.example.hifi.tests.objects;

import com.example.hifi.objects.Product;

import org.junit.Assert;
import org.junit.Test;

public class ProductTest {
    @Test
    public void testProduct()
    {
        Product product;
        
        System.out.println("Starting testProduct");
    
        product = new Product("1", "Apple MacBook", "Apple MacBook Description");
        Assert.assertNotNull(product);
        Assert.assertEquals("1", product.getProductID());
        Assert.assertEquals("Apple MacBook", product.getProductName());
        Assert.assertEquals("Apple MacBook Description", product.getProductDes());
        
        System.out.println("Finished testProduct\n");
    }

    @Test
    public void testProductSetters()
    {
        Product product = new Product("1","test","test");
        String keywords ="";
        for(int i = 0;i<100;i++)
        {
            product.setProductPrice(i);
            keywords+=i+",";
            Assert.assertEquals(i,product.getProductPrice(),0);
            product.setRating(i);
            Assert.assertEquals(i,product.getRating(),0);

        }
        product.setKeywords(keywords);
        product.setProductQuantity(10);
        Assert.assertEquals(100,product.getKeywords().size());
        Assert.assertEquals(10, product.getProductQuantity());
    }



}
