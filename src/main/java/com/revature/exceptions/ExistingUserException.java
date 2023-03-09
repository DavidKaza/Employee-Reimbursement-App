package com.revature.exceptions;

public class ExistingUserException extends Exception{
    public ExistingUserException(String message){
        super(message);
    }
}
