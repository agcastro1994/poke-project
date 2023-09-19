package com.pokeprojects.pokefilter.api.model.type;

import com.pokeprojects.pokefilter.api.model.move.Move;
import com.pokeprojects.pokefilter.api.resources.NamedApiResource;
import com.pokeprojects.pokefilter.api.resources.StandardApiResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Type implements StandardApiResource {
    private Integer id;
    private String name;
    private TypeRelations damageRelations;
    private List<TypePokemon> pokemon;
    private List<NamedApiResource<Move>> moves;
}