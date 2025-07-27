package com.example.hifi.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Product implements Serializable {
    private String productID;
    private String productName;
    private String productDes;
    private String productImageName;
    private int productImage;
    private int productQuantity;
    private double productPrice;
    private List<String> keywords;
    private float rating;
    
    public Product(final String productID) {
        this(productID, null, null);
    }
    
    public Product(final String productID, final String productName, String productDes) {
        this.productID = productID;
        this.productName = productName;
        this.productDes = productDes;
        this.keywords = new ArrayList<>();
    }
    
    public String getProductID() {
        return (productID);
    }
    
    public void setProductID(String productID) {
        this.productID = productID;
    }
    
    public String getProductName() {
        return (productName);
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public String getProductDes() {
        return productDes;
    }
    
    public void setProductDes(String productDes) {
        this.productDes = productDes;
    }
    
    public int getProductImage() {
        return productImage;
    }
    
    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }
    
    public String getProductImageName() {
        return productImageName;
    }
    
    public void setProductImageName(String productImageName) {
        this.productImageName = productImageName;
    }
    
    public double getProductPrice() {
        return productPrice;
    }
    
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
    
    public int getProductQuantity() {
        return productQuantity;
    }
    
    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
    
    public float getRating() {
        return rating;
    }
    
    public void setRating(float rating) {
        this.rating = rating;
    }
    
    public List<String> getKeywords() {
        return Collections.unmodifiableList(keywords);
    }
    
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
    
    /**
     * Accepts a comma delimited list of keywords and converts the list to a List object
     * @param keywords comma delimited string of keywords
     */
    public void setKeywords(String keywords) {
        final List<String> keywordList = new ArrayList<>();
        
        for (String keyword : keywords.split(",")) {
            if (keyword.length() > 0) {
                keywordList.add(keyword);
            }
        }
        
        setKeywords(keywordList);
    }
}
