package com.example.hifi.tests.objects;

import com.example.hifi.objects.OrderHistory;
import com.example.hifi.objects.OrderHistoryRecord;
import com.example.hifi.objects.Product;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class OrderHistoryTest {
    @Test
    public void testHistory()
    {
        System.out.println("Testing order history");
        OrderHistory oH = new OrderHistory("test");
        Assert.assertEquals("test", oH.getUserId());
        Assert.assertEquals(true,oH.isEmpty());
        for(int i = 0;i<10;i++) {
            Product product = new Product(Integer.toString(i), "test", "test");
            oH.addProduct(product);
            Assert.assertEquals(i+1,oH.getRecordList().size());
            Assert.assertEquals(product,oH.getRecordList().get(i).getProduct());
        }

        System.out.println("End of testing order history");
    }
}
