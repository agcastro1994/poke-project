package com.pokeprojects.pokefilter.api.dto.ability;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pokeprojects.pokefilter.api.resources.StandardApiResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AbilityDTO implements StandardApiResource {
    private Integer id;
    private String name;
}
