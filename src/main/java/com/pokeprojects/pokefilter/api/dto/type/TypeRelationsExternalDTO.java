package com.pokeprojects.pokefilter.api.dto.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
    private List<TypeDTO> noDamageTo;
    private List<TypeDTO> halfDamageTo;
    private List<TypeDTO> doubleDamageTo;
    private List<TypeDTO> noDamageFrom;
    private List<TypeDTO> halfDamageFrom;
    private List<TypeDTO> doubleDamageFrom;
}
