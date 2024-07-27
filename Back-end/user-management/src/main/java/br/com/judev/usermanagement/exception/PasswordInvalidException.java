package br.com.judev.usermanagement.exception;

public class PasswordInvalidException extends RuntimeException{
    public PasswordInvalidException(String message){
        super(message);
    }
}
