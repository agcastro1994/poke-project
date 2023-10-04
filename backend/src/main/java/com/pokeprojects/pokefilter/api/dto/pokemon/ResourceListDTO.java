package com.pokeprojects.pokefilter.api.dto.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pokeprojects.pokefilter.api.resources.NamedApiResource;
import com.pokeprojects.pokefilter.api.resources.StandardApiResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResourceListDTO <T extends StandardApiResource> {
    private List<NamedApiResource<T>> results;
}
