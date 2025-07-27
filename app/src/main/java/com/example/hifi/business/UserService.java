package com.example.hifi.business;

import com.example.hifi.objects.User;

// TODO: make UserService interface
public class UserService {
    private User currentUser = null;
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }
}
