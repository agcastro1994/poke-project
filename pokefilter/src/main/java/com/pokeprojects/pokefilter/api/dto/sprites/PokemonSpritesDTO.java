package com.pokeprojects.pokefilter.api.dto.sprites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pokeprojects.pokefilter.api.dto.sprites.OtherSpritesDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PokemonSpritesDTO {
    private String frontDefault;
    private String frontShiny;
    private String frontFemale;
    private String frontShinyFemale;
    private String backDefault;
    private String backShiny;
    private String backFemale;
    private String backShinyFemale;
    private OtherSpritesDTO other;
}
