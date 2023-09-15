package com.pokeprojects.pokefilter.api.model.type;

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
    private List<Type> noDamageTo;
    private List<Type> halfDamageTo;
    private List<Type> doubleDamageTo;
    private List<Type> noDamageFrom;
    private List<Type> halfDamageFrom;
    private List<Type> doubleDamageFrom;
}
