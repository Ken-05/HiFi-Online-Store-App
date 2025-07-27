package com.example.hifi.objects;

public class User {
    private String address;
    private String name;
    private final String email;
    private String password;
    private final String userId;
    
    public User(String name, String email, String password, String userId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setName(String name){ this.name = name;}
    
    public void setAddress(String addr)
    {
        address=addr;
    }
    
    public void setPassword(String pass) {password = pass;}

    public String getUserName()
    {
        return name;
    }

    public String getUserEmail() {
        return email;
    }

    public String getUserPassword() {
        return password;
    }


    public String getAddress() {
        return  address;
    }

}
