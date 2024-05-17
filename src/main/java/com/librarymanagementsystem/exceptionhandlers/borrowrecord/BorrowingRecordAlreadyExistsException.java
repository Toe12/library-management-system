package com.librarymanagementsystem.exceptionhandlers.borrowrecord;

public class BorrowingRecordAlreadyExistsException extends BorrowingRecordException{
    public BorrowingRecordAlreadyExistsException(String message) {
        super(message);
    }
}
