package com.example.hifi.tests.objects;

import com.example.hifi.objects.OrderHistoryRecord;
import com.example.hifi.objects.Product;

import org.junit.Assert;
import org.junit.Test;
import java.sql.Date;
public class OrderHistoryRecordsTest {
    @Test
    public void testOHR()
    {
        System.out.println("Testing order history records");
        Date date = new Date(50);
        Product product = new Product("1");
        OrderHistoryRecord oHR = new OrderHistoryRecord(product,date);
        Assert.assertEquals(date,oHR.getOrderDate());
        Assert.assertEquals(product,oHR.getProduct());
        System.out.println("End of testing order history records");
    }
}
