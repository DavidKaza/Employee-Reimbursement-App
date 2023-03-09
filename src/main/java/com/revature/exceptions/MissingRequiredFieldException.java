package com.revature.exceptions;
public class MissingRequiredFieldException extends Exception{
    public MissingRequiredFieldException(String message){
        super(message);
    }
}
