package com.pokeprojects.pokefilter.api.services.pokemon;

import com.pokeprojects.pokefilter.api.client.pokeapi.PokeReactiveClient;
import com.pokeprojects.pokefilter.api.dto.move.MoveDTO;
import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonClientDTO;
import com.pokeprojects.pokefilter.api.dto.pokemon_species.ChainDTO;
import com.pokeprojects.pokefilter.api.dto.pokemon_species.EvolutionChainDTO;
import com.pokeprojects.pokefilter.api.dto.type.TypeDTO;
import com.pokeprojects.pokefilter.api.enums.MatchStrategy;
import com.pokeprojects.pokefilter.api.enums.PokemonFilters;
import com.pokeprojects.pokefilter.api.enums.Region;
import com.pokeprojects.pokefilter.api.indexes.PokemonIndex;
import com.pokeprojects.pokefilter.api.indexes.PokemonTypeIndex;
import com.pokeprojects.pokefilter.api.model.move.Move;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import com.pokeprojects.pokefilter.api.model.pokemon_species.Chain;
import com.pokeprojects.pokefilter.api.model.pokemon_species.EvolutionChain;
import com.pokeprojects.pokefilter.api.model.type.Type;
import com.pokeprojects.pokefilter.api.repository.pokemon.PokemonInMemoryRepository;
import com.pokeprojects.pokefilter.api.services.FilterService;
import com.pokeprojects.pokefilter.api.services.pokemon_species.PokemonSpeciesService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class PokeApiService {
    private PokeReactiveClient reactiveClient;
    private ModelMapper mapper;
    private FilterService filterService;
    private PokemonInMemoryRepository inMemoryRepository;
    private PokemonSpeciesService speciesService;
    private PokemonIndex typeIndex;
    private PokemonIndex regionIndex;
    private Logger logger = LoggerFactory.getLogger(PokeApiService.class);
    private volatile boolean isLoading = false;


    public PokeApiService(PokeReactiveClient reactiveClient, ModelMapper mapper, FilterService filterService, PokemonInMemoryRepository inMemoryRepository, PokemonSpeciesService speciesService, @Qualifier("pokemonTypeIndex") PokemonIndex typeIndex, @Qualifier("pokemonRegionIndex") PokemonIndex regionIndex) {
        this.reactiveClient = reactiveClient;
        this.mapper = mapper;
        this.filterService = filterService;
        this.inMemoryRepository = inMemoryRepository;
        this.speciesService = speciesService;
        this.typeIndex = typeIndex;
        this.regionIndex = regionIndex;
    }

    private PokemonIndex getIndexForFilter(PokemonFilters filter) {
        return switch (filter) {
            case TYPE -> typeIndex;
            case REGION -> regionIndex;
            default        -> null;
        };
    }

    public Pokemon getPokemon(String identifier){
        //Try to find the Pokemon in memory, if that is not possible uses the PokeAPi
        Optional<Pokemon> pokemon = inMemoryRepository.getPokemonById(Integer.valueOf(identifier));
        return pokemon.orElseGet(() -> getPokemonByIdOrName(identifier));
    }

    public Move getMoveByIdOrName(String identifier){
        MoveDTO moveDTO = reactiveClient.getMove(identifier).block();
        return this.mapper.map(moveDTO, Move.class);
    }


    public List<Type> getPokemonTypes(String id){
        //Try to find the Pokemon in memory, if that is not possible uses the PokeAPi
        Pokemon pokemon = inMemoryRepository.getPokemonById(Integer.valueOf(id)).orElseGet(() -> getPokemonByIdOrName(id));

        PokemonClientDTO dto = mapper.map(pokemon, PokemonClientDTO.class);
        List<TypeDTO> dtoList = dto.getTypes().stream().map(pokeType-> reactiveClient.getNamedResource(pokeType.getType(), TypeDTO.class).block()).toList();
        return dtoList.stream().map(t->mapper.map(t, Type.class)).toList();
    }

    public Type getPokemonMoveType(Move move){
        return reactiveClient.getNamedResource(move.getType(), Type.class).block();
    }

    public List<Pokemon> getAllPokemon() {
        return inMemoryRepository.getAllPokemon();
    }

    private List<Pokemon> getAllPokemonByFilters(List<Pokemon> pokemonList, List<Predicate<Pokemon>> criteria) {
        return filterService.filterObjects(pokemonList, criteria, MatchStrategy.ALL);
    }

    public List<Pokemon> getAllPokemonByFilters(Map<String, String> criteriaMap){
        // Create a list of predicates to represent filter criteria
        List<Predicate<Pokemon>> criteria = new ArrayList<>();
        List<Pokemon> pokemonList = getAllPokemon();
        Set<Pokemon> filteredResults = new HashSet<>(pokemonList);

        // Iterate through the request parameters and build the filter criteria dynamically
        for (Map.Entry<String, String> entry : criteriaMap.entrySet()) {
            String paramName = entry.getKey();
            String paramValue = entry.getValue();

            // Find the corresponding filter in the PokemonFilters enum
            PokemonFilters filter = findFilterByName(paramName);

            if (!paramValue.equals(filter.getDefaultValue())) {
                if (filter.isIndexed() && !isLoading) {
                    List<Pokemon> indexList = getIndexForFilter(filter).getPokemonByIndex(paramValue);
                    // Intersect the indexList with the filteredResults using retainAll
                    filteredResults.retainAll(indexList);
                } else {
                    Predicate<Pokemon> filterPredicate = filter.getFilterCondition(paramValue);
                    criteria.add(filterPredicate);
                }
            }
        }
        return getAllPokemonByFilters(new ArrayList<>(filteredResults), criteria);
    }

    private PokemonFilters findFilterByName(String paramName) {
        for (PokemonFilters filter : PokemonFilters.values()) {
            if (filter.getFilterName().equals(paramName)) {
                return filter;
            }
        }
        throw new NoSuchElementException("At least one of your filter parameters is not valid, please try again"); // Return null for unknown filter names
    }

    public void loadStartupData(){
        isLoading = true;
        for(Region region : Region.getAllRegions()){
            List<Pokemon> regionPokemon = reactiveClient.getAllPokemonByRegion(region).stream().map(poke -> mapper.map(poke, Pokemon.class)).toList();
            loadPokemonInMemory(regionPokemon);
            typeIndex.loadIndex(regionPokemon);
            regionIndex.loadIndex(regionPokemon);
        }
        isLoading = false;
    }

    public void loadPokemonInMemory(List<Pokemon> pokemonList){
        inMemoryRepository.addPokemonList(pokemonList);
    }

    public Pokemon getPokemonByIdOrName(String identifier){
        PokemonClientDTO pokemonDTO = reactiveClient.getPokemon(identifier).block();
        logger.info("Mapping to model the pokemon with id {}", pokemonDTO.getId());
        return this.mapper.map(pokemonDTO, Pokemon.class);
    }


    public boolean isPokemonFullyEvolved(String id){
        String pokemonName = getPokemon(id).getName();
        EvolutionChain evolutionChain = mapper.map(speciesService.getEvolutionChain(id), EvolutionChain.class);
        return isPokemonFullyEvolvedAux(evolutionChain.getChain(), pokemonName);
    }

    private boolean isPokemonFullyEvolvedAux(Chain element, String pokemonName){
        if (element.isBaby()) return false;

        if(element.getEvolvesTo().isEmpty()){
            return true;
        } else {
            if(element.getSpecies().getName().equals(pokemonName)){
                return false;
            }
        }

        return isPokemonFullyEvolvedAux(element.getEvolvesTo().get(0), pokemonName);

    }

    //Possibly deprecated
    public List<Type> getPokemonTypesInfo(String identifier){
        return reactiveClient.getPokemonTypesInfo(identifier)
                .map(type -> mapper.map(type, Type.class))
                .collect(Collectors.toList()).block();
    }

}
