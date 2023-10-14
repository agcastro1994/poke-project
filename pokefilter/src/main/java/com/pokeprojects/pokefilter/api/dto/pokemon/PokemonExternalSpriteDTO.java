package com.pokeprojects.pokefilter.api.dto.pokemon;

import com.pokeprojects.pokefilter.api.dto.sprites.OtherExternalSpritesDTO;
import com.pokeprojects.pokefilter.api.dto.sprites.OtherSpritesDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PokemonExternalSpriteDTO {
    private String frontDefault;
    private String frontShiny;
    private String frontFemale;
    private String frontShinyFemale;
    private String backDefault;
    private String backShiny;
    private String backFemale;
    private String backShinyFemale;
    private OtherExternalSpritesDTO other;
}