package com.pokeprojects.pokefilter.api.model.pokemon_species;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pokeprojects.pokefilter.api.resources.NamedApiResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Chain {
    private List<EvolutionDetails> evolutionDetails;
    private List<Chain> evolvesTo;
    private boolean isBaby;
    private NamedApiResource<PokemonSpecies> species;
}
