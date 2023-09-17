package com.pokeprojects.pokefilter.api.client.pokeapi;

import com.pokeprojects.pokefilter.api.client.GenericReactiveClient;
import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonClientDTO;
import com.pokeprojects.pokefilter.api.dto.type.TypeDTO;
import com.pokeprojects.pokefilter.api.dto.type.TypePokemonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

//TODO extract Flux operation to service level
@Component
public class PokeReactiveClient extends GenericReactiveClient {
    private String baseUrl = "pokemon";

    @Autowired
    public PokeReactiveClient(WebClient webClient){
        super(webClient);
    }

    public Mono<PokemonClientDTO> getPokemon(String identifier){
        Mono<PokemonClientDTO> mono = this.getResource(PokemonClientDTO.class, identifier, baseUrl);
        return mono;
    }

    public Flux<PokemonClientDTO> getPokemonListByTypeAndRange(String type){
        return getResource(TypeDTO.class, type, "type")
                .flatMapIterable(TypeDTO::getPokemon)
                .flatMap(poke-> followResource(poke::getPokemon, PokemonClientDTO.class));
    }

    public Flux<TypeDTO> getPokemonTypesInfo(String identifier){
        return getResource(PokemonClientDTO.class, identifier, baseUrl)
                .flatMapIterable(PokemonClientDTO::getTypes)
                .flatMap(type -> followResource(type::getType, TypeDTO.class));
    }

    private Mono<PokemonClientDTO> searchForInternalResource(TypePokemonDTO typePoke){
        return followResource(typePoke::getPokemon, PokemonClientDTO.class);
    }
}
