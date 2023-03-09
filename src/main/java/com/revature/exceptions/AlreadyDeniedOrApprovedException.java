package com.revature.exceptions;

public class AlreadyDeniedOrApprovedException extends Exception{
    public AlreadyDeniedOrApprovedException(String message){
        super(message);
    }
}
