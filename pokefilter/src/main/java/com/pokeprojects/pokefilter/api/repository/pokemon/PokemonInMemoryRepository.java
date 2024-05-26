package com.pokeprojects.pokefilter.api.repository.pokemon;

import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import com.pokeprojects.pokefilter.api.repository.pokemon.indexes.Indexes;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.util.CollectionUtils;

@Repository
public class PokemonInMemoryRepository  implements Indexes<Pokemon,Integer> {
    private ConcurrentMap<Integer, Pokemon> pokemonConcurrentMap = new ConcurrentHashMap<>();
    private final int KNOWN_POKEMON_QUANTITY = 1010;

    public Optional<Pokemon> getPokemonById(Integer id){
        return Optional.ofNullable(pokemonConcurrentMap.get(id));
    }

    public List<Pokemon> getAllPokemon(){
        return !CollectionUtils.isEmpty(pokemonConcurrentMap) ? pokemonConcurrentMap.values().stream().toList() : new ArrayList<>();
    }

    @Override
    public void addToIndex(Pokemon element) {
        pokemonConcurrentMap.put(element.getId(), element);
    }

    @Override
    public void loadIndex(List<Pokemon> elementList) {
        elementList.forEach(this::addToIndex);
    }

    @Override
    public List<Pokemon> getListByIndex(Integer key) {
        return getPokemonById(key).map(List::of).orElse(new ArrayList<>());
    }

    @Override
    public Boolean containsKey(Integer key) {
        return pokemonConcurrentMap.containsKey(key);
    }

    @Override
    public Boolean isLoaded() {
        return pokemonConcurrentMap.keySet().size() == KNOWN_POKEMON_QUANTITY &&
                pokemonConcurrentMap.values().stream().noneMatch(Objects::isNull);
    }

    @Override
    public Set<Integer> getKeySet() {
        return pokemonConcurrentMap.keySet();
    }
}
