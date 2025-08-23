package com.pokeprojects.pokefilter.api.repository.pokemon.indexes;

import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import com.pokeprojects.pokefilter.api.model.pokemon.PokemonType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class PokemonTypeIndex implements Indexes<Pokemon,String> {
    private ConcurrentMap<String, List<Pokemon>> typeIndex;

    private final int TYPES_QUANTITY = 18;

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
    public List<Pokemon> getListByIndex(String key) {
        return typeIndex.getOrDefault(key, Collections.emptyList());
    }

    @Override
    public Boolean containsKey(String key){
        return typeIndex.containsKey(key);
    }

    @Override
    public Boolean isLoaded(){
        return typeIndex.keySet().size() == TYPES_QUANTITY &&
                typeIndex.values().stream().noneMatch(List::isEmpty);
    }

    @Override
    public Set<String> getKeySet(){
        return typeIndex.keySet();
    }

    @Override
    public void clearIndex() {
        typeIndex.clear();
    }
}