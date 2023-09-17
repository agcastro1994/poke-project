package com.pokeprojects.pokefilter.api.dto.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pokeprojects.pokefilter.api.model.type.Type;
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
public class TypeRelationsExternalDTO {
    private List<NamedApiResource<TypeExternalDTO>> noDamageTo;
    private List<NamedApiResource<TypeExternalDTO>> halfDamageTo;
    private List<NamedApiResource<TypeExternalDTO>> doubleDamageTo;
    private List<NamedApiResource<TypeExternalDTO>> noDamageFrom;
    private List<NamedApiResource<TypeExternalDTO>> halfDamageFrom;
    private List<NamedApiResource<TypeExternalDTO>> doubleDamageFrom;
}

