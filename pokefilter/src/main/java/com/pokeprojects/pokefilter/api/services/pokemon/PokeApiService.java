package com.pokeprojects.pokefilter.api.services.pokemon;

import com.pokeprojects.pokefilter.api.client.pokeapi.PokeReactiveClient;
import com.pokeprojects.pokefilter.api.dto.move.MoveDTO;
import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonClientDTO;
import com.pokeprojects.pokefilter.api.dto.type.TypeDTO;
import com.pokeprojects.pokefilter.api.enums.MatchStrategy;
import com.pokeprojects.pokefilter.api.enums.PokemonFilters;
import com.pokeprojects.pokefilter.api.enums.Region;
import com.pokeprojects.pokefilter.api.model.move.Move;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;

import com.pokeprojects.pokefilter.api.model.type.Type;
import com.pokeprojects.pokefilter.api.repository.pokemon.PokemonInMemoryRepository;
import com.pokeprojects.pokefilter.api.repository.pokemon.indexes.Indexes;
import com.pokeprojects.pokefilter.api.services.FilterService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;

@Service
public class PokeApiService {
    private final PokeReactiveClient reactiveClient;
    private final ModelMapper mapper;
    private final FilterService filterService;
    private final PokemonInMemoryRepository inMemoryRepository;

    private final Map<PokemonFilters, Indexes> indexStrategy;

    private Indexes<Pokemon, String> typeIndex;
    private Indexes<Pokemon, String> regionIndex;
    private final Logger logger = LoggerFactory.getLogger(PokeApiService.class);
    private volatile boolean isLoading = false;


    public PokeApiService(PokeReactiveClient reactiveClient, ModelMapper mapper, FilterService filterService, PokemonInMemoryRepository inMemoryRepository, @Qualifier("pokeApiIndexStrategy") Map<PokemonFilters, Indexes> indexStrategy, @Lazy @Qualifier("pokemonTypeIndex") Indexes<Pokemon, String> typeIndex, @Lazy @Qualifier("pokemonRegionIndex") Indexes<Pokemon, String> regionIndex) {
        this.reactiveClient = reactiveClient;
        this.mapper = mapper;
        this.filterService = filterService;
        this.inMemoryRepository = inMemoryRepository;
        this.indexStrategy = indexStrategy;
        this.typeIndex = typeIndex;
        this.regionIndex = regionIndex;
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
        List<Pokemon> pokemonList;

        //If our index is fully loaded we shall not fear any upcoming filters
        if(regionIndex.isLoaded()){
            pokemonList = getAllPokemon();
        } else {
            pokemonList = getBaseListToFilter(criteriaMap);
        }

        Set<Pokemon> filteredResults = new HashSet<>(pokemonList);

        // Iterate through the request parameters and build the filter criteria dynamically
        for (Map.Entry<String, String> entry : criteriaMap.entrySet()) {
            String paramName = entry.getKey();
            String paramValue = entry.getValue();

            // Find the corresponding filter in the PokemonFilters enum
            PokemonFilters filter = findFilterByName(paramName);

            if (!paramValue.equals(filter.getDefaultValue())) {
                if (filter.isIndexed() && !isLoading) {
                    Indexes<Pokemon, String> index = indexStrategy.get(filter);
                    if(index != null) {
                        List<Pokemon> indexList = index.getListByIndex(paramValue);
                        // Intersect the indexList with the filteredResults using retainAll
                        filteredResults.retainAll(indexList);
                    }
                } else {
                    Predicate<Pokemon> filterPredicate = filter.getFilterCondition(paramValue);
                    criteria.add(filterPredicate);
                }
            }
        }
        List<Pokemon> filteredList = getAllPokemonByFilters(new ArrayList<>(filteredResults), criteria);

        //Remove possible duplicates
        Set<Pokemon> set = new HashSet<>(filteredList);
        List<Pokemon> finalList = new ArrayList<>(set);
        finalList.sort(Comparator.comparing(Pokemon::getId));
        return finalList;
    }

    private PokemonFilters findFilterByName(String paramName) {
        for (PokemonFilters filter : PokemonFilters.values()) {
            if (filter.getFilterName().equals(paramName)) {
                return filter;
            }
        }
        throw new NoSuchElementException("At least one of your filter parameters is not valid, please try again"); // Return null for unknown filter names
    }

    /**
     *  This function is used in the case that our index is not fully loaded. If the region filter is present
     *  this criterion is removed from the map because it's going to be applied here.
     *  Then checks is the region is already loaded, if not, search for it using the API.
     *  If there is no region filter we search in the API for the missing regions to complete the whole Pokemon list
     * @param criteriaMap Map with the filters received
     * @return The list of Pokemon to start the search
     */
    private List<Pokemon> getBaseListToFilter(Map<String,String> criteriaMap){
        String regionFilterName = PokemonFilters.REGION.getFilterName();
        boolean useRegion = criteriaMap.containsKey(regionFilterName);

        if(useRegion){
            Region reg =  Region.valueOf(criteriaMap.get(regionFilterName));
            criteriaMap.remove(regionFilterName);
            return regionIndex.containsKey(reg.name())
                   ? regionIndex.getListByIndex(reg.name())
                   : reactiveClient.getAllPokemonByRegion(reg).stream().map(p -> mapper.map(p, Pokemon.class)).toList();
        }

        List<Region> missingRegions = Region.getAllRegions().stream()
                .filter(region -> !regionIndex.getKeySet().contains(region.name()))
                .toList();

        List<Pokemon> currentList = getAllPokemon();
        List<Pokemon> mergedList = new ArrayList<>(currentList);

        missingRegions.forEach(region -> {
            List<PokemonClientDTO> pokemonDTOs = reactiveClient.getAllPokemonByRegion(region);
            List<Pokemon> mappedPokemon = pokemonDTOs.stream().map(p -> mapper.map(p, Pokemon.class)).toList();
            mergedList.addAll(mappedPokemon);
        });
        return mergedList;
    }

    public Pokemon getPokemonByIdOrName(String identifier){
        PokemonClientDTO pokemonDTO = reactiveClient.getPokemon(identifier).block();
        logger.info("Mapping to model the pokemon with id {}", identifier);
        return this.mapper.map(pokemonDTO, Pokemon.class);
    }


}
