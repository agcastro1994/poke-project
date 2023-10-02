package com.pokeprojects.pokefilter.api.model.ability;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ability {
    private Integer id;
    private String name;
    private Boolean isMainSeries;
}
