package com.pokeprojects.pokefilter.api.exceptions;

public class ApiClientErrorException extends RuntimeException{
    public ApiClientErrorException(String message, Exception e){
        super(message, e);
    }

    public ApiClientErrorException(String message){
        super(message);
    }
}
