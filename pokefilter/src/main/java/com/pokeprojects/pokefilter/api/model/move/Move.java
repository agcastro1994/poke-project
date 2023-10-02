package com.pokeprojects.pokefilter.api.model.move;

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
public class Move implements StandardApiResource {
    private Integer id;
    private String name;
    private Integer accuracy;
    private Integer effectChance;
    private Integer pp;
    private Integer priority;
    private Integer power;
    private NamedApiResource<MoveDamageClass> damageClass;
    private NamedApiResource<Type> type;
}
