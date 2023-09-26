package com.pokeprojects.pokefilter.api.enums;

import com.pokeprojects.pokefilter.api.model.type.Type;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PokemonTypes {
    NORMAL(1, "normal" ),
    GRASS(12, "grass"),
    ELECTRIC(13, "electric"),
    WATER(11, "water"),
    FIRE(10, "fire"),
    BUG(7, "bug"),
    GHOST(8, "ghost"),
    PSYCHIC(14, "psychic"),
    STEEL(9, "steel"),
    DARK(17, "dark"),
    FLYING(3, "flying"),
    ICE(15, "ice"),
    POISON(4, "poison"),
    GROUND(5, "ground"),
    ROCK(6,"rock"),
    DRAGON(16, "dragon"),
    FIGHTING(2, "fighting"),
    FAIRY(18, "fairy");

    private final Integer id;
    private final String name;

    PokemonTypes(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static PokemonTypes typeMatch(String identifier){
        return Arrays.stream(PokemonTypes.values()).filter(type -> type.getId().toString().equals(identifier) || type.getName().equals(identifier)).findFirst().orElseThrow();
    }

    public static boolean isAMatch(String pokeType, PokemonTypes searchType){
        return pokeType.equals(searchType.getName());
    }
}
