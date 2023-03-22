package ru.itmentor.spring.boot_security.demo.exception;

public class NoSuchException extends RuntimeException{
    public NoSuchException(String message){
        super(message);
    }
}
