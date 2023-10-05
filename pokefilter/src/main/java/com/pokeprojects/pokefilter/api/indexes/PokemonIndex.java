package com.pokeprojects.pokefilter.api.indexes;

import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import java.util.List;
import java.util.Set;

public interface PokemonIndex {
    void addToIndex(Pokemon pokemon);

    void loadIndex(List<Pokemon> pokemonList);

    List<Pokemon> getListByIndex(String key);

    Boolean containsKey(String key);

    Boolean isLoaded();

    Set getKeySet();

}
