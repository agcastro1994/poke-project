package com.pokeprojects.pokefilter.api.services;

import com.pokeprojects.pokefilter.api.controllers.PokemonController;
import com.pokeprojects.pokefilter.api.enums.MatchStrategy;
import com.pokeprojects.pokefilter.api.enums.PokemonFilters;
import com.pokeprojects.pokefilter.api.enums.Region;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Predicate;

@Service
public class FilterService {
    private Logger logger = LoggerFactory.getLogger(FilterService.class);

    private Predicate<Pokemon> predicateBuilder(List<Predicate<Pokemon>> criteria, MatchStrategy strategy){

        return criteria.stream()
                .reduce(strategy.accumulator())
                .orElse(Pokemon -> true);
    }

    public List<Pokemon> filterObjects(List<Pokemon> objects, List<Predicate<Pokemon>> criteria, MatchStrategy strategy){
        logger.info("Preparing list to be filtered, size: {}", objects.size());
        List<Pokemon> pokemonFiltered = objects.stream().filter(predicateBuilder(criteria,strategy)).toList();
        logger.info("Filtered list size: {}", pokemonFiltered.size());
        return pokemonFiltered;
    }

}
