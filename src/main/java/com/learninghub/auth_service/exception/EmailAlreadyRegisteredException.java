package com.learninghub.auth_service.exception;

public class EmailAlreadyRegisteredException extends RuntimeException{

    public EmailAlreadyRegisteredException() {
        super("Email is already registered");
    }

}
