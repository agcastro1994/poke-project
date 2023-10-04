package com.pokeprojects.pokefilter.api.indexes;

import com.pokeprojects.pokefilter.api.enums.Region;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import com.pokeprojects.pokefilter.api.model.pokemon.PokemonType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class PokemonRegionIndex implements PokemonIndex {
    private ConcurrentMap<String, List<Pokemon>> regionIndex;

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
    public List<Pokemon> getPokemonByIndex(String key) {
        return regionIndex.getOrDefault(key, Collections.emptyList());
    }
}
