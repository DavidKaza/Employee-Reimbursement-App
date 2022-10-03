package com.revature.consoleapp.repository;

import com.revature.consoleapp.model.User;

import java.util.HashMap;
import java.util.Map;

public class AuthRepository {

    // We do not have a SQL database to connect to,
    // So we are going to store our users temporarily in data-structure
    public Map<String, User> users = new HashMap<>();

    // key-value pairs
    //  key (String): username
    //  value (User object): User object associated with the username

    // Ex. "user123": User (user123, password12345)
    //     "john_doe": User (john_doe, testing123)

    public void addUser(User user) {
        // Add a key-value pair to the Map
        users.put(user.username, user);
    }

    public User getUserByUsername(String username) {
        return users.get(username);
    }
}