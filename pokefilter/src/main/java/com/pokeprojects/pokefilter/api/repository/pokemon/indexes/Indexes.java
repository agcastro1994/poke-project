package com.pokeprojects.pokefilter.api.repository.pokemon.indexes;

import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;

import java.util.List;
import java.util.Set;

public interface Indexes<T,V> {
    void addToIndex(T element);

    void loadIndex(List<T> elementList);

    List<T> getListByIndex(V key);

    Boolean containsKey(V key);

    Boolean isLoaded();

    Set<V> getKeySet();

    void clearIndex();

}
