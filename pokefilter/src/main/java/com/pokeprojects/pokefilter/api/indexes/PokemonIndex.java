package com.pokeprojects.pokefilter.api.indexes;

import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import java.util.List;

public interface PokemonIndex {
    void addToIndex(Pokemon pokemon);

    void loadIndex(List<Pokemon> pokemonList);

    List<Pokemon> getPokemonByIndex(String key);
}
