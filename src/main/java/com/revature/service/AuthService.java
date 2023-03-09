package com.revature.service;

import com.revature.exceptions.ExistingUserException;
import com.revature.exceptions.InvalidLoginException;
import com.revature.exceptions.MissingRequiredFieldException;
import com.revature.exceptions.model.User;
import com.revature.repository.UserRepository;

import java.sql.SQLException;

public class AuthService {
    private UserRepository userRepo = new UserRepository();

    public User register(User user) throws ExistingUserException, MissingRequiredFieldException, SQLException {
        if(user.getPassword().isEmpty() | user.getUsername().isEmpty() | user.getFirstName().isEmpty() | user.getLastName().isEmpty()){
            throw new MissingRequiredFieldException("All fields are required!");
        }
        User existing = userRepo.getUserInfo(user.getUsername());
        if(existing != null){
            throw new ExistingUserException("Username taken!");
        }
        User newUser = new User(0, user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), 1);
        return userRepo.addUser(newUser);
    }

    public User login(String un, String pw) throws SQLException, InvalidLoginException{
        User user = userRepo.getUserByUsernameAndPassword(un, pw);
        if(user == null){
            throw new InvalidLoginException("Invalid credentials");
        }
        return user;
    }

}
