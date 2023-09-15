package com.pokeprojects.pokefilter.api.services;

import com.pokeprojects.pokefilter.api.client.pokeapi.PokeAPIClient;
import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonClientDTO;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PokeApiService {
    private PokeAPIClient pokeAPIClient;
    private ModelMapper mapper;

    public PokeApiService(PokeAPIClient pokeAPIClient, ModelMapper mapper) {
        this.pokeAPIClient = pokeAPIClient;
        this.mapper = mapper;
    }

    public Pokemon getPokemonByIdOrName(String identifier){
        PokemonClientDTO pokemonDTO = pokeAPIClient.getPokemon(identifier);
        return this.mapper.map(pokemonDTO, Pokemon.class);
    }
}
