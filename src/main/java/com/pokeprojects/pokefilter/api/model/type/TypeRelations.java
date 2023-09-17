package com.pokeprojects.pokefilter.api.model.type;

import com.pokeprojects.pokefilter.api.resources.NamedApiResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TypeRelations {
    private List<NamedApiResource<Type>> noDamageTo;
    private List<NamedApiResource<Type>> halfDamageTo;
    private List<NamedApiResource<Type>> doubleDamageTo;
    private List<NamedApiResource<Type>> noDamageFrom;
    private List<NamedApiResource<Type>> halfDamageFrom;
    private List<NamedApiResource<Type>> doubleDamageFrom;
}
