package com.pokeprojects.pokefilter.api.exceptions;

public class ApiServerErrorException extends RuntimeException{
    public ApiServerErrorException(String message, Exception e){
        super(message, e);
    }

    public ApiServerErrorException(String message){
        super(message);
    }
}
