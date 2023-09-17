package com.pokeprojects.pokefilter.api.services.pokemon;

import com.pokeprojects.pokefilter.api.enums.Region;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class PokemonFilterService {
    public List<Pokemon> filterPokemonByRegion(Flux<Pokemon> pokemonList, Region region){
         return pokemonList.filter(poke -> poke != null && poke.getId() > region.getOffset() && poke.getId() <= region.getLimit()).collectList().block();
    }
}
