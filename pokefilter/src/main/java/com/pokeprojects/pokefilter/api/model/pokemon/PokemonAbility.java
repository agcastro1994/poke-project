package com.pokeprojects.pokefilter.api.model.pokemon;

import com.pokeprojects.pokefilter.api.model.ability.Ability;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PokemonAbility {
    private Boolean isHidden;
    private Integer slot;
    private Ability ability;
}
