package com.pokeprojects.pokefilter.api.model.sprites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PokemonSprites {
    private String frontDefault;
    private String frontShiny;
    private String frontFemale;
    private String frontShinyFemale;
    private String backDefault;
    private String backShiny;
    private String backFemale;
    private String backShinyFemale;
    private OtherSprites other;
}
