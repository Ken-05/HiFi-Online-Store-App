package com.example.hifi.persistence;

import com.example.hifi.objects.User;

import java.util.List;

public interface UserPersistence {
    List<User> getUserSequential();

    User getUserById(String userId);

    User getUserByEmail(String userEmail);

    User insertUser(final User currentUser);

    User updateUser(final User currentUser);

    void deleteUser(final User currentUser);
}
