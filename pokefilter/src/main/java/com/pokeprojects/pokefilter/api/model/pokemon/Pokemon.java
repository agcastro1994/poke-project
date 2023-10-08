package com.pokeprojects.pokefilter.api.model.pokemon;

import com.pokeprojects.pokefilter.api.model.records.PokemonSpecy;
import com.pokeprojects.pokefilter.api.model.sprites.PokemonSprites;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pokemon {
    private Integer id;
    private String name;
    private Integer baseExperience;
    private Integer height;
    private Boolean isDefault;
    private Integer order;
    private Integer weight;
    private List<PokemonAbility> abilities;
    private List<PokemonMove> moves;
    private PokemonSprites sprites;
    private List<PokemonStat> stats;
    private List<PokemonType> types;
    private PokemonSpecy species;
    private Boolean isFullyEvolved;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return Objects.equals(id, pokemon.id) && name.equals(pokemon.name);
    }

    @Override
    public int hashCode() {
        return 31 * id + name.hashCode();
    }
}