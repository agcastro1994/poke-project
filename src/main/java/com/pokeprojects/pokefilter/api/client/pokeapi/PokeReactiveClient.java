package com.pokeprojects.pokefilter.api.client.pokeapi;

import com.pokeprojects.pokefilter.api.client.GenericReactiveClient;
import com.pokeprojects.pokefilter.api.dto.PageResponseDTO;
import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonClientDTO;
import com.pokeprojects.pokefilter.api.dto.type.TypeDTO;
import com.pokeprojects.pokefilter.api.enums.Region;
import com.pokeprojects.pokefilter.api.resources.NamedApiResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class PokeReactiveClient extends GenericReactiveClient {
    private String baseUrl = "pokemon";
    private Logger logger = LoggerFactory.getLogger(PokeReactiveClient.class);

    @Autowired
    public PokeReactiveClient(WebClient webClient){
        super(webClient);
    }

    public Mono<PokemonClientDTO> getPokemon(String identifier){
        logger.info("Trying to fetch pokemon with identification: {}", identifier);
        Mono<PokemonClientDTO> mono = this.getResource(PokemonClientDTO.class, identifier, baseUrl)
                .doOnError(WebClientException -> logger.warn("Fetch failed for pokemon with identification: {}", identifier, WebClientException));
        return mono;
    }

    public Flux<PokemonClientDTO> getPokemonListByType(String type){
        logger.info("Trying to fetch pokemon with type: {}", type);
        return getResource(TypeDTO.class, type, "type")
                .flatMapIterable(TypeDTO::getPokemon)
                .flatMap(poke-> followResource(poke::getPokemon, PokemonClientDTO.class));
    }

    public Flux<TypeDTO> getPokemonTypesInfo(String identifier){
        logger.info("Trying to fetch types from pokemon : {}", identifier);
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
        logger.info("Trying to fetch all pokemon from: {}", region);
        Mono<PageResponseDTO<PokemonClientDTO>> response = getPaginatedResource(baseUrl, region.getLimit(), region.getOffset(), PokemonClientDTO.class)
                .doOnError(WebClientException ->  logger.warn("Fetch failed for pokemon from: {}", region, WebClientException));
        List<NamedApiResource<PokemonClientDTO>> pokemonUrlList = response.block().getResults();
        return getNamedResources(pokemonUrlList, PokemonClientDTO.class).collectList().block();
    }

}
