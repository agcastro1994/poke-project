package com.pokeprojects.pokefilter.api.dto.sprites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OtherExternalSpritesDTO {
    private AdditionalArtworkExternalDTO officialArtwork;
    private AdditionalArtworkExternalDTO home;
    private AdditionalArtworkExternalDTO dreamWorld;
}
