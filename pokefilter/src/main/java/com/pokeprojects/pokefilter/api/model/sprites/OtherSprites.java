package com.pokeprojects.pokefilter.api.model.sprites;

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
public class OtherSprites {
    @JsonProperty("official-artwork")
    private AdditionalArtwork officialArtwork;
    private AdditionalArtwork home;
    private AdditionalArtwork dreamWorld;
}
