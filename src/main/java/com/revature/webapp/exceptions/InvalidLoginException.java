package com.revature.webapp.exceptions;

public class InvalidLoginException extends Exception{
    public InvalidLoginException(String message){
        super(message);
    }
}
