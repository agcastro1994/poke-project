package com.pokeprojects.pokefilter.api.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Getter
public enum Region {
    KANTO(0, 151),
    JOHTO( 151, 100),
    HOENN(251, 135),
    SINNOH( 386, 107),
    TESELIA( 493, 156),
    KALOS(649, 72),
    ALOLA( 721, 88),
    GALAR(809, 96),
    PALDEA(905, 105),
    ALL(0, 1010);

    private final Integer offset;
    private final Integer limit;

    Region(Integer offset, Integer limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public static List<Region> getAllRegions(){
        return Arrays.stream(Region.values()).filter(r-> !r.equals(Region.ALL)).collect(Collectors.toList());
    }

    public static Region getRegionByPokemonId(Integer id) {
        for (Region region : Region.values()) {
            if (id > region.offset && id <= region.offset + region.limit) {
                return region;
            }
        }
        throw new NoSuchElementException("Invalid Pokémon ID: " + id);
    }
}
