package com.pokeprojects.pokefilter.api.dto.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pokeprojects.pokefilter.api.dto.ability.PokemonAbilityDTO;
import com.pokeprojects.pokefilter.api.dto.sprites.PokemonSpritesDTO;
import com.pokeprojects.pokefilter.api.dto.type.PokemonTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PokemonClientDTO {
    private Integer id;
    private String name;
    private Integer baseExperience;
    private Integer height;
    private Boolean isDefault;

    private Integer order;
    private Integer weight;
    private List<PokemonAbilityDTO> abilities;
//    private List<NamedApiResource<PokemonForm>> forms;
//    private List<VersionGameIndex> gameIndices;
//    private List<PokemonHeldItem> heldItems;
//    private String locationAreaEncounters;
//    private List<PokemonMove> moves;
    private PokemonSpritesDTO sprites;
//    private NamedApiResource<PokemonSpecies> species;
    private List<PokemonStatDTO> stats;
    private List<PokemonTypeDTO> types;
//    private List<PokemonTypePast> pastTypes;
}

