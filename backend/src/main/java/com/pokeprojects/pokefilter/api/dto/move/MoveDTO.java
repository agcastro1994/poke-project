package com.pokeprojects.pokefilter.api.dto.move;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pokeprojects.pokefilter.api.dto.type.TypeDTO;
import com.pokeprojects.pokefilter.api.model.type.Type;
import com.pokeprojects.pokefilter.api.resources.NamedApiResource;
import com.pokeprojects.pokefilter.api.resources.StandardApiResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MoveDTO implements StandardApiResource {
    private Integer id;
    private String name;
    private Integer accuracy;
    private Integer effectChance;
    private Integer pp;
    private Integer priority;
    private Integer power;
    private NamedApiResource<MoveDamageClassDTO> damageClass;
    private NamedApiResource<TypeDTO> type;
}
