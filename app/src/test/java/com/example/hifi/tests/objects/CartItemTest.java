package com.example.hifi.tests.objects;

import com.example.hifi.objects.CartItem;
import com.example.hifi.objects.Product;

import org.junit.Assert;
import org.junit.Test;

public class CartItemTest {
    @Test
    public void testCartItem(){
        System.out.println("Testing Cart Item");
        Product product1 = new Product("1", "Apple MacBook", "Apple MacBook Description");
        product1.setProductPrice(5.6);

        //single
        CartItem cartItem1 = new CartItem(product1);

        Assert.assertEquals(1,cartItem1.getQuantity());
        Assert.assertEquals(5.6,cartItem1.getCost(),0);
        Assert.assertEquals(product1,cartItem1.getProduct());

        Product product2 = new Product("2", "Windows Laptop", "Description");
        product2.setProductPrice(10);

        //multiple items
        CartItem cartItem2 = new CartItem(product2,100);
        Assert.assertEquals(100,cartItem2.getQuantity());
        Assert.assertEquals(1000,cartItem2.getCost(),0);
        Assert.assertEquals(product2,cartItem2.getProduct());

        System.out.println("End of Testing Cart Item");
    }
}
