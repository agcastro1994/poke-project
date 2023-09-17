package com.pokeprojects.pokefilter.api.model.type;

import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonClientDTO;
import com.pokeprojects.pokefilter.api.resources.NamedApiResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TypePokemon {
    private Integer slot;
    private NamedApiResource<PokemonClientDTO> pokemon;
}