package com.librarymanagementsystem.exceptionhandlers.patron;

public class PatronNotFoundException extends PatronException{
    public PatronNotFoundException(String message) {
        super(message);
    }
}
