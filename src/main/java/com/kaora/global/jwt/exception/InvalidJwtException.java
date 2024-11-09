package com.kaora.global.jwt.exception;

public class InvalidJwtException extends RuntimeException{
    public InvalidJwtException(String message){
        super(message);
    }
}
