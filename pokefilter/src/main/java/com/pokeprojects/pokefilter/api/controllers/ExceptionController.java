package com.pokeprojects.pokefilter.api.controllers;

import com.pokeprojects.pokefilter.api.dto.StandardErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<StandardErrorDTO> handleClientError(WebClientResponseException e){
        return new ResponseEntity<>(new StandardErrorDTO(HttpStatus.NOT_FOUND.name(), "We could not found the resource"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WebClientException.class)
    public ResponseEntity<StandardErrorDTO> handleServerError(WebClientException e){
        return new ResponseEntity<>(new StandardErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.name(), "There was an error with your request"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<StandardErrorDTO> handleServerError(NoSuchElementException e){
        return new ResponseEntity<>(new StandardErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.name(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardErrorDTO> handleServerError(Exception e){
        return new ResponseEntity<>(new StandardErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.name(), "There was an error in our server, please try again"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
