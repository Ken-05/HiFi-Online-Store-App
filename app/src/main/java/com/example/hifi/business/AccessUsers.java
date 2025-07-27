package com.example.hifi.business;

import com.example.hifi.application.Services;
import com.example.hifi.objects.Product;
import com.example.hifi.objects.User;
import com.example.hifi.persistence.ProductPersistence;
import com.example.hifi.persistence.UserPersistence;

import java.util.Collections;
import java.util.List;

public class AccessUsers
{
	private final UserPersistence userPersistence;
	private List<User> users;
	private User user;
	private int currentUser;

	public AccessUsers()
	{
		this(Services.getUserPersistence());
	}

	public AccessUsers(final UserPersistence userPersistence) {
		users = null;
		user = null;
		currentUser = 0;
		this.userPersistence = userPersistence;
	}


	public List<User> getUsers()
    {
        users = userPersistence.getUserSequential();
        return Collections.unmodifiableList(users);
    }

	public User getSequential()
	{
		String result = null;
		if (users == null)
		{
            users = userPersistence.getUserSequential();
			currentUser = 0;
		}
		if (currentUser < users.size())
		{
			user = (User) users.get(currentUser);
			currentUser++;
		}
		else
		{
			users = null;
			user = null;
			currentUser = 0;
		}
		return user;
	}

	public User insertUser(User currentUser)
	{
		return userPersistence.insertUser(currentUser);
	}

	public User getUserById(String id)
	{
		try {
			return userPersistence.getUserById(id);
		} catch (Exception e) {
			return null;
		}
	}

	public User getUserByEmail(String email)
	{
		try {
			return userPersistence.getUserByEmail(email);
		} catch (Exception e) {
			return null;
		}
	}

	public User updateUser(User currentUser)
	{
		return userPersistence.updateUser(currentUser);
	}

	public void deleteUser(User currentUser)
	{
		userPersistence.deleteUser(currentUser);
	}
}
