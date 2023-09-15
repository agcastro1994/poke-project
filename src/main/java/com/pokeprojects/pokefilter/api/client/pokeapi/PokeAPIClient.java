package com.pokeprojects.pokefilter.api.client.pokeapi;

import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
public class PokeAPIClient extends GenericApiClient{
    private String baseUrl = "https://pokeapi.co/api/v2/pokemon/";



    @Autowired
    public PokeAPIClient(RestTemplate restTemplate){
        super(restTemplate);
    }

    public PokemonClientDTO getPokemon(String identifier){
        return doGetExchange(PokemonClientDTO.class, baseUrl + identifier, null, new HashMap<>());
    }

}
