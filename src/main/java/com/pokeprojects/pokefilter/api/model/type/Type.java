package com.pokeprojects.pokefilter.api.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Type {
    private Integer id;
    private String name;
    private TypeRelations damageRelations;
    //private List<TypeRelationsPast> pastDamageRelations;
    //private List<GenerationGameIndex> gameIndices;
    //private NamedApiResource<Generation> generation;

    //private List<TypePokemon> pokemon;
    //private List<NamedApiResource<Move>> moves;
}