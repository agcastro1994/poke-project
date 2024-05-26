package com.pokeprojects.pokefilter.api.repository.pokemon.indexes;

import com.pokeprojects.pokefilter.api.enums.Region;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class PokemonRegionIndex implements Indexes<Pokemon,String> {
    private ConcurrentMap<String, List<Pokemon>> regionIndex;
    private final int REGIONS_QUANTITY = 9;

    public PokemonRegionIndex() {
        regionIndex = new ConcurrentHashMap<>();
    }

    @Override
    public void addToIndex(Pokemon pokemon) {
        Region region = Region.getRegionByPokemonId(pokemon.getId());
        regionIndex.computeIfAbsent(region.name(), k -> new ArrayList<>()).add(pokemon);
    }

    @Override
    public void loadIndex(List<Pokemon> pokemonList) {
        for (Pokemon pokemon : pokemonList) {
            addToIndex(pokemon);
        }
    }

    @Override
    public List<Pokemon> getListByIndex(String key) {
        return regionIndex.getOrDefault(key, Collections.emptyList());
    }

    @Override
    public Boolean containsKey(String key){
        return regionIndex.containsKey(key);
    }

    @Override
    public Boolean isLoaded(){
        return regionIndex.keySet().size() == REGIONS_QUANTITY &&
                regionIndex.values().stream().noneMatch(List::isEmpty);
    }

    @Override
    public Set<String> getKeySet(){
        return regionIndex.keySet();
    }
}
