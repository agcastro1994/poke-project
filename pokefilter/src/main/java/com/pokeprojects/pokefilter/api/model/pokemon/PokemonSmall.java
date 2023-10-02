package com.pokeprojects.pokefilter.api.model.pokemon;

import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonStatExternalDTO;
import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonTypeExternalDTO;
import com.pokeprojects.pokefilter.api.dto.sprites.PokemonSpritesDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PokemonSmall {
    private Integer id;
    private String name;
    private Integer height;
    private Integer weight;
    private PokemonSpritesDTO sprites;
    private List<PokemonStatExternalDTO> stats;
    private List<PokemonTypeExternalDTO> types;
}
