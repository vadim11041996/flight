package com.transaction.flightserviceexample.exception;

public class InsufficientAmountException extends RuntimeException {

    public InsufficientAmountException(String name) {
        super(name);
    }
}
