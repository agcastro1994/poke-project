package com.pokeprojects.pokefilter.api.client.pokeapi;

import com.pokeprojects.pokefilter.api.client.GenericReactiveClient;
import com.pokeprojects.pokefilter.api.dto.PageResponseDTO;
import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonClientDTO;
import com.pokeprojects.pokefilter.api.dto.type.TypeDTO;
import com.pokeprojects.pokefilter.api.enums.Region;
import com.pokeprojects.pokefilter.api.resources.NamedApiResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

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

    public Flux<PokemonClientDTO> getPokemonListByType(String type){
        return getResource(TypeDTO.class, type, "type")
                .flatMapIterable(TypeDTO::getPokemon)
                .flatMap(poke-> followResource(poke::getPokemon, PokemonClientDTO.class));
    }

    public Flux<TypeDTO> getPokemonTypesInfo(String identifier){
        return getResource(PokemonClientDTO.class, identifier, baseUrl)
                .flatMapIterable(PokemonClientDTO::getTypes)
                .flatMap(type -> followResource(type::getType, TypeDTO.class));
    }

    public List<PokemonClientDTO> getAllPokemon(){
        List<PokemonClientDTO> pokemonList = new ArrayList<>();
        for(Region region : Region.getAllRegions()){
            pokemonList.addAll(getAllPokemonByRegion(region));
        }
        return pokemonList;
    }

    public List<PokemonClientDTO> getAllPokemonByRegion(Region region){
        Mono<PageResponseDTO<PokemonClientDTO>> response = getPaginatedResource(baseUrl, region.getLimit(), region.getOffset(), PokemonClientDTO.class);
        List<NamedApiResource<PokemonClientDTO>> pokemonUrlList = response.block().getResults();
        return getNamedResources(pokemonUrlList, PokemonClientDTO.class).collectList().block();
    }

}
