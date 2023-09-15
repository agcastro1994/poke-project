package com.pokeprojects.pokefilter.api.model.pokemon;

import com.pokeprojects.pokefilter.api.model.stat.Stat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PokemonStat {
    private Stat stat;
    private Integer effort;
    private Integer baseStat;
}
