package com.pokeprojects.pokefilter.api.indexes;

import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import com.pokeprojects.pokefilter.api.model.pokemon.PokemonType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class PokemonTypeIndex implements PokemonIndex {
    private ConcurrentMap<String, List<Pokemon>> typeIndex;

    public PokemonTypeIndex() {
        typeIndex = new ConcurrentHashMap<>();
    }

    @Override
    public void addToIndex(Pokemon pokemon) {
        for (PokemonType type : pokemon.getTypes()) {
            String typeName = type.getType().getName();
            typeIndex.computeIfAbsent(typeName, k -> new ArrayList<>()).add(pokemon);
        }
    }

    @Override
    public void loadIndex(List<Pokemon> pokemonList){
        for(Pokemon pokemon : pokemonList){
            addToIndex(pokemon);
        }
    }

    @Override
    public List<Pokemon> getPokemonByIndex(String key) {
        return typeIndex.getOrDefault(key, Collections.emptyList());
    }
}