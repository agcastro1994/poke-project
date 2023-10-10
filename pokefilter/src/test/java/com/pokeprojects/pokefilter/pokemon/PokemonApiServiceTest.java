package com.pokeprojects.pokefilter.pokemon;

import com.pokeprojects.pokefilter.api.model.pokemon.*;
import com.pokeprojects.pokefilter.api.model.sprites.PokemonSprites;
import com.pokeprojects.pokefilter.api.services.pokemon.PokeApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.Mockito.mock;

@SpringBootTest
public class PokemonApiServiceTest {
    @Autowired
    PokeApiService service;
    List<Pokemon> pokemonList;

    @BeforeEach
    public void setup(){
        PokemonAbility ability = mock(PokemonAbility.class);
        PokemonMove move = mock(PokemonMove.class);
        PokemonSprites sprites = mock(PokemonSprites.class);
        PokemonStat stat = mock(PokemonStat.class);
        PokemonType type = mock(PokemonType.class);
        Pokemon pokemon1 = new Pokemon(6,"charizard", 267, 17, true, 7, 905, List.of(ability), List.of(move), sprites, List.of(stat), List.of(type),null,true);
        Pokemon pokemon2 = new Pokemon(3,"venusaur", 263, 20, true, 3, 1000, List.of(ability), List.of(move), sprites, List.of(stat), List.of(type),null,true);
        Pokemon pokemon3 = new Pokemon(9,"blastoise", 265, 16, true, 12, 855, List.of(ability), List.of(move), sprites, List.of(stat), List.of(type), null,true);
        Pokemon pokemon4 = new Pokemon(59,"arcanine", 194, 19, true, 98, 1550, List.of(ability), List.of(move), sprites, List.of(stat), List.of(type), null,true);
        Pokemon pokemon5 = new Pokemon(157,"typhlosion", 240, 17, true, 254, 795, List.of(ability), List.of(move), sprites, List.of(stat), List.of(type), null,true);

        pokemonList = List.of(pokemon1,pokemon2,pokemon3,pokemon4,pokemon5);
    }

}
