package com.pokeprojects.pokefilter.api.model.pokemon_species;

import com.pokeprojects.pokefilter.api.resources.NamedApiResource;
import com.pokeprojects.pokefilter.api.resources.StandardApiResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PokemonSpecies implements StandardApiResource {
    private Integer id;
    private String name;
    private EvolutionChain fullEvolutionChain;
    private NamedApiResource<PokemonSpecies> evolvesFromSpecies;
}
