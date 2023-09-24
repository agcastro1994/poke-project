package com.pokeprojects.pokefilter.filters;

import com.pokeprojects.pokefilter.api.enums.MatchStrategy;
import com.pokeprojects.pokefilter.api.enums.PokemonFilters;
import com.pokeprojects.pokefilter.api.enums.Region;
import com.pokeprojects.pokefilter.api.model.pokemon.*;
import com.pokeprojects.pokefilter.api.model.sprites.PokemonSprites;
import com.pokeprojects.pokefilter.api.model.type.Type;
import com.pokeprojects.pokefilter.api.services.FilterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.function.Predicate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class FilterServiceTest {
    private List<Pokemon> pokemonList;
    @Autowired
    private FilterService filterService;
    private Pokemon charizard;
    private Pokemon venusaur;
    private Pokemon blastoise;
    private Pokemon arcanine;
    private Pokemon typhlosion;

    @BeforeEach
    public void setup(){
        PokemonAbility ability = mock(PokemonAbility.class);
        PokemonMove move = mock(PokemonMove.class);
        PokemonSprites sprites = mock(PokemonSprites.class);
        PokemonStat stat = mock(PokemonStat.class);
        PokemonType fireType = mock(PokemonType.class);
        PokemonType waterType = mock(PokemonType.class);
        PokemonType grassType = mock(PokemonType.class);

        when(fireType.getType()).thenReturn(new Type(10,"fire",null,null,null));
        when(waterType.getType()).thenReturn(new Type(11,"water",null,null,null));
        when(grassType.getType()).thenReturn(new Type(12,"grass",null,null,null));

        charizard = new Pokemon(6,"charizard", 267, 17, true, 7, 905, List.of(ability), List.of(move), sprites, List.of(stat), List.of(fireType));
        venusaur = new Pokemon(3,"venusaur", 263, 20, true, 3, 1000, List.of(ability), List.of(move), sprites, List.of(stat), List.of(grassType));
        blastoise = new Pokemon(9,"blastoise", 265, 16, true, 12, 855, List.of(ability), List.of(move), sprites, List.of(stat), List.of(waterType));
        arcanine = new Pokemon(59,"arcanine", 194, 19, true, 98, 1550, List.of(ability), List.of(move), sprites, List.of(stat), List.of(fireType));
        typhlosion = new Pokemon(157,"typhlosion", 240, 17, true, 254, 795, List.of(ability), List.of(move), sprites, List.of(stat), List.of(fireType));

        pokemonList = List.of(charizard,venusaur,blastoise,arcanine,typhlosion);
    }

    @Test
    public void filterPokemonOneCriteriaListTest(){
        List<Predicate<Pokemon>> criteria = List.of(PokemonFilters.REGION.getFilterCondition(Region.KANTO.name()));
        List<Pokemon> filteredList = filterService.filterObjects(pokemonList,criteria, MatchStrategy.ALL);
        Assertions.assertEquals(4,filteredList.size());
        Assertions.assertFalse(filteredList.contains(typhlosion));

        criteria = List.of(PokemonFilters.TYPE.getFilterCondition("fire"));
        filteredList = filterService.filterObjects(pokemonList,criteria, MatchStrategy.ALL);
        Assertions.assertEquals(3,filteredList.size());
        Assertions.assertTrue(filteredList.contains(arcanine));
        Assertions.assertTrue(filteredList.contains(charizard));
        Assertions.assertTrue(filteredList.contains(typhlosion));
    }

    @Test
    public void filterPokemonTwoCriteriaListTest(){
        List<Predicate<Pokemon>> criteria = List.of(PokemonFilters.REGION.getFilterCondition(Region.KANTO.name()), PokemonFilters.TYPE.getFilterCondition("fire"));
        List<Pokemon> filteredList = filterService.filterObjects(pokemonList,criteria, MatchStrategy.ALL);
        Assertions.assertEquals(2,filteredList.size());
        Assertions.assertTrue(filteredList.contains(arcanine));
        Assertions.assertTrue(filteredList.contains(charizard));
    }
}
