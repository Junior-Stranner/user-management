package br.com.judev.usermanagement.exception;

public class EntityAlreadyExists extends RuntimeException {
    public EntityAlreadyExists(String message){
        super(message);
    }
}
