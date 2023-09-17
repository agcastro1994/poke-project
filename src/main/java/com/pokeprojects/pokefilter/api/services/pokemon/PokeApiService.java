package com.pokeprojects.pokefilter.api.services.pokemon;

import com.pokeprojects.pokefilter.api.client.pokeapi.PokeReactiveClient;
import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonClientDTO;
import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonSmallDTO;
import com.pokeprojects.pokefilter.api.enums.Region;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import com.pokeprojects.pokefilter.api.model.type.Type;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokeApiService {
    private PokeReactiveClient reactiveClient;
    private ModelMapper mapper;
    private PokemonFilterService filterService;

    public PokeApiService(PokeReactiveClient reactiveClient, ModelMapper mapper, PokemonFilterService filterService) {
        this.reactiveClient = reactiveClient;
        this.mapper = mapper;
        this.filterService = filterService;
    }

    public Pokemon getPokemonByIdOrName(String identifier){
        PokemonClientDTO pokemonDTO = reactiveClient.getPokemon(identifier).block();
        return this.mapper.map(pokemonDTO, Pokemon.class);
    }

    public List<Pokemon> getPokemonByTypeAndRegion(String type, Region region){
        Flux<Pokemon> pokemonList = reactiveClient.getPokemonListByTypeAndRange(type)
                .map(poke -> mapper.map(poke, Pokemon.class));
        return filterService.filterPokemonByRegion(pokemonList, region);
    }

    public List<Type> getPokemonTypesInfo(String identifier){
        return reactiveClient.getPokemonTypesInfo(identifier)
                .map(type -> mapper.map(type, Type.class))
                .collect(Collectors.toList()).block();
    }
}
