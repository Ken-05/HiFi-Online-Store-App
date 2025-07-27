package com.example.hifi.tests.objects;

import com.example.hifi.objects.Cart;
import com.example.hifi.objects.CartItem;
import com.example.hifi.objects.Product;

import org.junit.Assert;
import org.junit.Test;

public class CartTest {
    @Test
    public void testCart()
    {
        System.out.println("Testing Cart");
        String userId = "abc";
        Product product1 = new Product("1", "Apple MacBook", "Apple MacBook Description");
        product1.setProductPrice(5.6);

        Cart cart1 = new Cart(userId);
        //single
        CartItem cartItem1 = new CartItem(product1);
        cart1.addProduct(product1,1);
        Assert.assertEquals(false, cart1.isEmpty());
        Assert.assertEquals(true,cart1.isProductInCart("1"));
        Assert.assertEquals(5.6,cart1.getCost(),0);

        cart1.removeProduct(product1);
        Assert.assertEquals(true,cart1.isEmpty());

        //multiple items
        for(int i = 0;i<10;i++)
        {
            Product newProduct = new Product(Integer.toString(i),"Multi", "Multi");
            newProduct.setProductPrice(1.5);
            cart1.addProduct(newProduct,1);
            Assert.assertEquals(true,cart1.isProductInCart(Integer.toString(i)));
        }
        Assert.assertEquals(10,cart1.getQuantity());
        Assert.assertEquals(15,cart1.getCost(),0);

        System.out.println("End of testing Cart");
    }
    @Test
    public void testEmptyCart()
    {
        System.out.println("Testing empty cart");
        Cart cart2 = new Cart("empty");
        Assert.assertEquals(true, cart2.isEmpty());

        Assert.assertEquals(0,cart2.getCost(),0);
        Assert.assertEquals(0,cart2.getQuantity());
        System.out.println("End of testing empty cart");
    }
}
