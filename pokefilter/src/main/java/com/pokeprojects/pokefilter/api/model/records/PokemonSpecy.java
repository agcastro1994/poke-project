package com.pokeprojects.pokefilter.api.model.records;

public record PokemonSpecy(
       Integer id,
       Integer evolves_from_species_id,
       String name,
       Boolean is_legendary,
       Boolean is_mythical,
       Boolean is_baby
) {
}
