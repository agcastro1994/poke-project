package com.pokeprojects.pokefilter.api.services;

import com.pokeprojects.pokefilter.api.enums.MatchStrategy;
import com.pokeprojects.pokefilter.api.enums.PokemonFilters;
import com.pokeprojects.pokefilter.api.enums.Region;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Predicate;

@Service
public class FilterService {

    private Predicate<Pokemon> predicateBuilder(List<Predicate<Pokemon>> criteria, MatchStrategy strategy){
        return criteria.stream()
                .reduce(strategy.accumulator())
                .orElse(noFilter -> true);
    }

    public List<Pokemon> filterObjects(List<Pokemon> objects, List<Predicate<Pokemon>> criteria, MatchStrategy strategy){
       return objects.stream().filter(predicateBuilder(criteria,strategy)).toList();
    }

}
