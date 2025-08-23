package com.pokeprojects.pokefilter.api.beans;

import com.pokeprojects.pokefilter.api.enums.PokemonFilters;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import com.pokeprojects.pokefilter.api.repository.pokemon.indexes.Indexes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class PokeIndexStrategyDefinition {
    private Indexes<Pokemon, String> typeIndex;
    private Indexes<Pokemon, String> regionIndex;

    public PokeIndexStrategyDefinition(@Lazy @Qualifier("pokemonTypeIndex") Indexes<Pokemon, String> typeIndex, @Lazy @Qualifier("pokemonRegionIndex") Indexes<Pokemon, String> regionIndex) {
        this.typeIndex = typeIndex;
        this.regionIndex = regionIndex;
    }

    @Bean
    public Map<PokemonFilters, Indexes> pokeApiIndexStrategy(){
        Map<PokemonFilters, Indexes> strategyMap = new EnumMap<>(PokemonFilters.class);

        strategyMap.put(PokemonFilters.TYPE, typeIndex);
        strategyMap.put(PokemonFilters.REGION, regionIndex);

        return strategyMap;
    }

}
