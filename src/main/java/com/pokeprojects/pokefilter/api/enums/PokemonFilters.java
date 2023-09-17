package com.pokeprojects.pokefilter.api.enums;

import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;

import java.util.function.Predicate;

public enum PokemonFilters{
    TYPE{
        @Override
        public Predicate<Pokemon> getFilterCondition(String type){
            PokemonTypes searchType = PokemonTypes.typeMatch(type);
            return pokemon -> pokemon.getTypes().stream().anyMatch(t->PokemonTypes.isAMatch(t.getType(),searchType));
        }
    },
    REGION{
        @Override
        public Predicate<Pokemon> getFilterCondition(String region){
            Region selectedRegion = Region.valueOf(region);
            return pokemon -> selectedRegion.getOffset() < pokemon.getId() && pokemon.getId() <= selectedRegion.getLimit();
        }
    };

    public abstract Predicate<Pokemon> getFilterCondition(String object);

}
