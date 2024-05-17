package com.librarymanagementsystem.exceptionhandlers;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

public class ValidationException extends RuntimeException {

    private final transient BindingResult errors;

    public ValidationException(BindingResult errors) {
        this.errors = errors;
    }

    public List<String> getMessages() {
        return getValidationMessage(this.errors);
    }

    @Override
    public String getMessage() {
        return this.getMessages().toString();
    }

    //demonstrate how to extract a message from the binging result
    private static List<String> getValidationMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(ValidationException::getValidationMessage)
                .toList();
    }

    private static String getValidationMessage(ObjectError error) {
        if (error instanceof FieldError fieldError) {
            return fieldError.getDefaultMessage();
        }
        return String.format("%s: %s", error.getObjectName(), error.getDefaultMessage());
    }

}