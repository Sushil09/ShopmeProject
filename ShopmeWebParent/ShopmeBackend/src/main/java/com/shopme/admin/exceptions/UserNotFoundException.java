package com.shopme.admin.exceptions;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException(String message) {
     super(message);
    }
}
