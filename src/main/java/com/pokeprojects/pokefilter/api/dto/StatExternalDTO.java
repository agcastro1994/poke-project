package com.pokeprojects.pokefilter.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StatExternalDTO {
    private Integer id;
    private String name;
    private Integer gameIndex;
    private Boolean isBattleOnly;
    //private MoveStatAffectSets affectingMoves;
    //private NatureStatAffectSets affectingNatures;
    //private List<ApiResource<Characteristic>> characteristics;
    //private NamedApiResource<MoveDamageClass> moveDamageClass;
    //private List<Name> names;
}
