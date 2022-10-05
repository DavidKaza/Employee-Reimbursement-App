package com.revature.webapp.exceptions;

public class AlreadyDeniedOrApprovedException extends Exception{
    public AlreadyDeniedOrApprovedException(String message){
        super(message);
    }
}
