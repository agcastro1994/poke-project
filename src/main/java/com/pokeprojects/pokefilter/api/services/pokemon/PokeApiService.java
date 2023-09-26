package com.pokeprojects.pokefilter.api.services.pokemon;

import com.pokeprojects.pokefilter.api.client.pokeapi.PokeReactiveClient;
import com.pokeprojects.pokefilter.api.dto.move.MoveDTO;
import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonClientDTO;
import com.pokeprojects.pokefilter.api.dto.type.TypeDTO;
import com.pokeprojects.pokefilter.api.enums.MatchStrategy;
import com.pokeprojects.pokefilter.api.model.move.Move;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import com.pokeprojects.pokefilter.api.model.type.Type;
import com.pokeprojects.pokefilter.api.repository.pokemon.PokemonInMemoryRepository;
import com.pokeprojects.pokefilter.api.services.FilterService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class PokeApiService {
    private PokeReactiveClient reactiveClient;
    private ModelMapper mapper;
    private FilterService filterService;
    private PokemonInMemoryRepository inMemoryRepository;
    private Logger logger = LoggerFactory.getLogger(PokeApiService.class);

    public PokeApiService(PokeReactiveClient reactiveClient, ModelMapper mapper, FilterService filterService, PokemonInMemoryRepository inMemoryRepository) {
        this.reactiveClient = reactiveClient;
        this.mapper = mapper;
        this.filterService = filterService;
        this.inMemoryRepository = inMemoryRepository;
    }


    public Pokemon getPokemonInMemory(String identifier){
        Optional<Pokemon> pokemon = inMemoryRepository.getPokemonById(Integer.valueOf(identifier));
        if(pokemon.isPresent()){
            return pokemon.get();
        }
       throw new NoSuchElementException("The pokemon could not be found in memory");
    }

    public Move getMoveByIdOrName(String identifier){
        MoveDTO moveDTO = reactiveClient.getMove(identifier).block();
        return this.mapper.map(moveDTO, Move.class);
    }


    public List<Type> getPokemonTypes(String id){
        List<Type> typeList = new ArrayList<>();
        Optional<Pokemon> pokemon = inMemoryRepository.getPokemonById(Integer.valueOf(id));
        if(pokemon.isPresent()){
            PokemonClientDTO dto = mapper.map(pokemon.get(), PokemonClientDTO.class);
            List<TypeDTO> dtoList = dto.getTypes().stream().map(pokeType-> reactiveClient.getNamedResource(pokeType.getType(), TypeDTO.class).block()).toList();
            typeList = dtoList.stream().map(t->mapper.map(t, Type.class)).toList();
        }
        return typeList;
    }

    public Type getPokemonMoveType(Move move){
        return reactiveClient.getNamedResource(move.getType(), Type.class).block();
    }

    public List<Pokemon> getAllPokemon() {
        return inMemoryRepository.getAllPokemon();
    }

    public List<Pokemon> getAllPokemonByFilters(List<Predicate<Pokemon>> criteria) {
        List<Pokemon> pokemonList = getAllPokemon();
        return filterService.filterObjects(pokemonList, criteria, MatchStrategy.ALL);
    }

    public void loadAllPokemonInMemory(){
        List<Pokemon> pokemonList = reactiveClient.getAllPokemon().stream().map(poke -> mapper.map(poke, Pokemon.class)).toList();
        loadPokemonInMemory(pokemonList);
    }

    public void loadPokemonInMemory(List<Pokemon> pokemonList){
        inMemoryRepository.addPokemonList(pokemonList);
    }


    //TODO If we cannot find the Pokemon in memory we can use these methods and try searching for it in PokeApi.
    public Pokemon getPokemonByIdOrName(String identifier){
        PokemonClientDTO pokemonDTO = reactiveClient.getPokemon(identifier).block();
        logger.info("Mapping to model the pokemon with id {}", pokemonDTO.getId());
        return this.mapper.map(pokemonDTO, Pokemon.class);
    }

    public List<Type> getPokemonTypesInfo(String identifier){
        return reactiveClient.getPokemonTypesInfo(identifier)
                .map(type -> mapper.map(type, Type.class))
                .collect(Collectors.toList()).block();
    }

}
