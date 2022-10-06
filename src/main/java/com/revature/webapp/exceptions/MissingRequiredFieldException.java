package com.revature.webapp.exceptions;
public class MissingRequiredFieldException extends Exception{
    public MissingRequiredFieldException(String message){
        super(message);
    }
}
