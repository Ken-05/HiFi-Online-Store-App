package com.example.hifi.tests.objects;

import com.example.hifi.objects.User;

import org.junit.Assert;
import org.junit.Test;


public class UserTest {
    @Test
    public void testUser() {

        System.out.println("Testing Create User");
        User testUser ;
        testUser= new User("name","email@gmail.com","12345","-1");

        Assert.assertEquals("name", testUser.getUserName());
        Assert.assertEquals("email@gmail.com", testUser.getUserEmail());
        Assert.assertEquals("12345", testUser.getUserPassword());
        Assert.assertEquals("-1", testUser.getUserId());
        System.out.println("End OF Testing Create User");
    }

    @Test
    public void testUserSetters(){
        System.out.println("Testing Setters In User");
        User testUser ;
        testUser= new User("name","email@gmail.com","12345","-1");
        testUser.setName("test");
        testUser.setPassword("123");
        testUser.setAddress("123 drive");
        Assert.assertEquals("test", testUser.getUserName());
        Assert.assertEquals("email@gmail.com", testUser.getUserEmail());
        Assert.assertEquals("123", testUser.getUserPassword());
        Assert.assertEquals("123 drive",testUser.getAddress());
        Assert.assertEquals("-1", testUser.getUserId());

        System.out.println("End OF Testing Setters In User");
    }
}
