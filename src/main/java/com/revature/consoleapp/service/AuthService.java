package com.revature.consoleapp.service;

import com.revature.consoleapp.exception.NotOldEnoughException;
import com.revature.consoleapp.exception.UsernameTakenException;
import com.revature.consoleapp.model.User;
import com.revature.consoleapp.repository.AuthRepository;

public class AuthService {

    public AuthRepository authRepository = new AuthRepository();

    public void register(String username, String password, String age) throws NotOldEnoughException, UsernameTakenException {
        int convertedAge = Integer.parseInt(age); // Convert a String into an int

        if (convertedAge < 18) {
            throw new NotOldEnoughException("You must be at least 18 or older to register");
        }

        // Insert code below that will talk to the Repository layer to store the newly registered user
        // ....

        // Check if user w/ username already exists
        if (authRepository.getUserByUsername(username) != null) {
            throw new UsernameTakenException("Username is already taken");
        }

        User user = new User(username, password);
        authRepository.addUser(user);
    }


}