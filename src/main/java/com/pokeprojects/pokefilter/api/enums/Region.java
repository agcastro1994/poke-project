package com.pokeprojects.pokefilter.api.enums;

import lombok.Getter;

@Getter
public enum Region {
    KANTO(0, 151),
    JOHTO( 151, 251),
    HOENN(251, 386),
    SINNOH( 386, 493),
    TESELIA( 493, 649),
    KALOS(649, 721),
    ALOLA( 721, 809),
    GALAR(809, 905),
    PALDEA(905, 1010),
    ALL(0, 1010);

    private final Integer offset;
    private final Integer limit;

    Region(Integer offset, Integer limit) {
        this.offset = offset;
        this.limit = limit;
    }
}
