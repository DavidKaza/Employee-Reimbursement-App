package com.revature.consoleapp.exception;

public class UsernameTakenException extends Exception {

    public UsernameTakenException(String message) {
        super(message);
    }
}