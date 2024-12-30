package br.com.judev.usermanagement.exception;

public class UserEmailNotFoundException extends RuntimeException{
    public UserEmailNotFoundException(String message){
        super(message);
    }
}
