package com.revature.webapp.service;

import com.revature.webapp.exceptions.ExistingUserException;
import com.revature.webapp.exceptions.InvalidLoginException;
import com.revature.webapp.exceptions.MissingRequiredFieldException;
import com.revature.webapp.model.User;
import com.revature.webapp.repository.UserRepository;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class AuthService {
    private final UserRepository userRepo = new UserRepository();

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
