package com.pokeprojects.pokefilter.api.model.stat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Stat {
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
