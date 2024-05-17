package com.librarymanagementsystem.controllers;

import com.librarymanagementsystem.exceptionhandlers.ValidationException;
import com.librarymanagementsystem.exceptionhandlers.book.BookException;
import com.librarymanagementsystem.exceptionhandlers.borrowrecord.BorrowingRecordAlreadyExistsException;
import com.librarymanagementsystem.exceptionhandlers.borrowrecord.BorrowingRecordException;
import com.librarymanagementsystem.exceptionhandlers.book.BookNotFoundException;
import com.librarymanagementsystem.exceptionhandlers.patron.PatronException;
import com.librarymanagementsystem.exceptionhandlers.patron.PatronNotFoundException;
import com.librarymanagementsystem.payloads.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            BookException.class,
            BorrowingRecordException.class,
            PatronException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse internalServerErrorExceptionHandler(RuntimeException ex) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

    @ExceptionHandler({
            BookNotFoundException.class,
            PatronNotFoundException.class,
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse notFoundExceptionHandler(RuntimeException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler({
            BorrowingRecordAlreadyExistsException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handleBorrowingRecordAlreadyExistsException(RuntimeException ex) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler({
            ValidationException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleValidationException(RuntimeException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}
