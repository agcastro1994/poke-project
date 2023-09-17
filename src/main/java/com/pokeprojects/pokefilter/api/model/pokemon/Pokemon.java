package com.pokeprojects.pokefilter.api.model.pokemon;

import com.pokeprojects.pokefilter.api.model.sprites.PokemonSprites;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pokemon {
    private Long id;
    private String name;
    private Integer baseExperience;
    private Integer height;
    private Boolean isDefault;
    private Integer order;
    private Integer weight;
    private List<PokemonAbility> abilities;
//    private List<PokemonMove> moves;
    private PokemonSprites sprites;
    private List<PokemonStat> stats;
    private List<PokemonType> types;
}