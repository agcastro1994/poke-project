package com.pokeprojects.pokefilter.api.model.pokemon;

import com.pokeprojects.pokefilter.api.model.type.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PokemonType {
    private Integer slot;
    private Type type;
}