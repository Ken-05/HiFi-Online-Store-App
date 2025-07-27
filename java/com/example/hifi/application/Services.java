package com.example.hifi.application;

import com.example.hifi.business.UserService;
import com.example.hifi.persistence.CartItemPersistence;
import com.example.hifi.persistence.OrderHistoryPersistence;
import com.example.hifi.persistence.ProductPersistence;
import com.example.hifi.persistence.UserPersistence;
import com.example.hifi.persistence.hsqldb.CartItemPersistenceHSQLDB;
import com.example.hifi.persistence.hsqldb.OrderHistoryPersistenceHSQLDB;
import com.example.hifi.persistence.hsqldb.ProductPersistenceHSQLDB;
import com.example.hifi.persistence.hsqldb.UserPersistenceHSQLDB;

public class Services {
    private static ProductPersistence productPersistence = null;
    private static UserPersistence userPersistence = null;
    private static CartItemPersistence cartItemPersistence = null;
    private static OrderHistoryPersistence orderHistoryPersistence = null;
    private static UserService userService;

    public static synchronized ProductPersistence getProductPersistence() {
        if (productPersistence == null) {
            productPersistence = new ProductPersistenceHSQLDB(HiFiApplication.getDBPathName());
        }
        return productPersistence;
    }

    public static synchronized UserPersistence getUserPersistence() {
        if (userPersistence == null) {
            userPersistence = new UserPersistenceHSQLDB(HiFiApplication.getDBPathName());
        }
        return userPersistence;
    }
    
    public static synchronized CartItemPersistence getCartItemPersistence() {
        if (cartItemPersistence == null) {
            cartItemPersistence = new CartItemPersistenceHSQLDB(HiFiApplication.getDBPathName());
        }
        return cartItemPersistence;
    }
    
    public static synchronized OrderHistoryPersistence getOrderHistoryPersistence() {
        if (orderHistoryPersistence == null) {
            orderHistoryPersistence = new OrderHistoryPersistenceHSQLDB(HiFiApplication.getDBPathName());
        }
        return orderHistoryPersistence;
    }
    
    public static synchronized UserService getUserService() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }
}
