package com.pokeprojects.pokefilter.api.repository.pokemon;

import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class PokemonInMemoryRepository {
    private ConcurrentMap<Integer, Pokemon> pokemonConcurrentMap = new ConcurrentHashMap<>();

    public void addPokemonList(List<Pokemon> pokemonList){
        pokemonList.forEach(this::addPokemon);
    }

    public void addPokemon(Pokemon pokemon){
        pokemonConcurrentMap.put(pokemon.getId(), pokemon);
    }

    public Optional<Pokemon> getPokemonById(Integer id){
        return Optional.ofNullable(pokemonConcurrentMap.get(id));
    }

    public List<Pokemon> getAllPokemon(){
        return pokemonConcurrentMap.values().size() > 0 ? pokemonConcurrentMap.values().stream().toList() : new ArrayList<>();
    }

}
