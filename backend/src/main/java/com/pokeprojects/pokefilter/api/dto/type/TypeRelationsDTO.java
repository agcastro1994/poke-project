package com.pokeprojects.pokefilter.api.dto.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pokeprojects.pokefilter.api.resources.NamedApiResource;
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
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TypeRelationsDTO {
    private List<NamedApiResource<TypeDTO>> noDamageTo;
    private List<NamedApiResource<TypeDTO>> halfDamageTo;
    private List<NamedApiResource<TypeDTO>> doubleDamageTo;
    private List<NamedApiResource<TypeDTO>> noDamageFrom;
    private List<NamedApiResource<TypeDTO>> halfDamageFrom;
    private List<NamedApiResource<TypeDTO>> doubleDamageFrom;
}
