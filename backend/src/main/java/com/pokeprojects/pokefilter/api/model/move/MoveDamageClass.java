package com.pokeprojects.pokefilter.api.model.move;

import com.pokeprojects.pokefilter.api.resources.NamedApiResource;
import com.pokeprojects.pokefilter.api.resources.StandardApiResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MoveDamageClass implements StandardApiResource {
    private Integer id;
    private String name;
    private List<NamedApiResource<Move>> moves;
}
