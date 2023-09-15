package com.pokeprojects.pokefilter.api.client.pokeapi;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokeprojects.pokefilter.api.exceptions.ApiClientErrorException;
import com.pokeprojects.pokefilter.api.exceptions.ApiServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@Component
public abstract class GenericApiClient {
    private RestTemplate restTemplate;

    public GenericApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T doGetExchange(Class<T> clazz, String url, HttpEntity headers, Map<String, String> params) {
        try {
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, headers, clazz, params);
            return response.getBody();
        } catch (HttpClientErrorException exception) {
            throw new ApiClientErrorException("Element not found");
        } catch (HttpServerErrorException exception) {
            throw new ApiServerErrorException("Error in api server");
        }
    }

}
