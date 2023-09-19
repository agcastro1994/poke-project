package com.pokeprojects.pokefilter.api.services.pokemon;

import com.pokeprojects.pokefilter.api.client.pokeapi.PokeReactiveClient;
import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonClientDTO;
import com.pokeprojects.pokefilter.api.enums.MatchStrategy;
import com.pokeprojects.pokefilter.api.enums.PokemonFilters;
import com.pokeprojects.pokefilter.api.enums.Region;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import com.pokeprojects.pokefilter.api.model.type.Type;
import com.pokeprojects.pokefilter.api.repository.pokemon.PokemonInMemoryRepository;
import com.pokeprojects.pokefilter.api.services.FilterService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokeApiService {
    private PokeReactiveClient reactiveClient;
    private ModelMapper mapper;
    private FilterService filterService;
    private PokemonInMemoryRepository inMemoryRepository;

    public PokeApiService(PokeReactiveClient reactiveClient, ModelMapper mapper, FilterService filterService, PokemonInMemoryRepository inMemoryRepository) {
        this.reactiveClient = reactiveClient;
        this.mapper = mapper;
        this.filterService = filterService;
        this.inMemoryRepository = inMemoryRepository;
    }

    public Pokemon getPokemonByIdOrName(String identifier){
        PokemonClientDTO pokemonDTO = reactiveClient.getPokemon(identifier).block();
        return this.mapper.map(pokemonDTO, Pokemon.class);
    }

    public List<Pokemon> getPokemonByTypeAndRegion(String type, String region){
        List<Pokemon> pokemonList = reactiveClient.getPokemonListByType(type)
                .map(poke -> mapper.map(poke, Pokemon.class)).collectList().block();
        return filterService.filterObjects(pokemonList, List.of(PokemonFilters.REGION.getFilterCondition(region)), MatchStrategy.ANY);
    }

    public List<Type> getPokemonTypesInfo(String identifier){
        return reactiveClient.getPokemonTypesInfo(identifier)
                .map(type -> mapper.map(type, Type.class))
                .collect(Collectors.toList()).block();
    }

    public List<Pokemon> getAllPokemon() {
        return inMemoryRepository.getAllPokemon();
    }

    public List<Pokemon> getAllPokemonByRegion(Region region) {
        List<Pokemon> pokemonList = inMemoryRepository.getAllPokemon();
        return filterService.filterObjects(pokemonList, List.of(PokemonFilters.REGION.getFilterCondition(region.name())), MatchStrategy.ALL);
    }

    public void loadAllPokemonInMemory(){
        List<Pokemon> pokemonList = reactiveClient.getAllPokemon().stream().map(poke -> mapper.map(poke, Pokemon.class)).toList();
        loadPokemonInMemory(pokemonList);
    }

    public void loadPokemonInMemory(List<Pokemon> pokemonList){
        inMemoryRepository.addPokemonList(pokemonList);
    }

}
