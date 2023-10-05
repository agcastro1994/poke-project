package com.pokeprojects.pokefilter.api.model.pokemon_species;

import com.pokeprojects.pokefilter.api.resources.StandardApiResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvolutionChain implements StandardApiResource {
    private String name;
    private Integer id;
    private Chain chain;
}
