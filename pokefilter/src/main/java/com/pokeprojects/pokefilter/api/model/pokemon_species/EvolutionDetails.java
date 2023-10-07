package com.pokeprojects.pokefilter.api.model.pokemon_species;

import com.pokeprojects.pokefilter.api.resources.NamedApiResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvolutionDetails {
    private String gender;
    private Integer minLevel;
    private Integer minHappiness;
    private NamedApiResource trigger;
}
