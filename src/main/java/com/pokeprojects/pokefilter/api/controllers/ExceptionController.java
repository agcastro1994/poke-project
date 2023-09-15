package com.pokeprojects.pokefilter.api.controllers;

import com.pokeprojects.pokefilter.api.dto.StandardErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<StandardErrorDTO> handleServerError(HttpServerErrorException e){
        return new ResponseEntity<>(new StandardErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.name(), "There was an error with your request"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<StandardErrorDTO> handleClientError(HttpClientErrorException e){
        return new ResponseEntity<>(new StandardErrorDTO(HttpStatus.NOT_FOUND.name(), "We could not found the resource"), HttpStatus.NOT_FOUND);
    }
}
