package br.com.judev.usermanagement.exception;

public class EmailUniqueViolationException extends RuntimeException{
    public EmailUniqueViolationException(String message){
        super(message);
    }
}
