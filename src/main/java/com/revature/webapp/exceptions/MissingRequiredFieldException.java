package com.revature.webapp.exceptions;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;

public class MissingRequiredFieldException extends Exception{
    public MissingRequiredFieldException(String message){
        super(message);
    }
}
