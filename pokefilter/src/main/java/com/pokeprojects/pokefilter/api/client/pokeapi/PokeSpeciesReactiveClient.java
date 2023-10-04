package com.pokeprojects.pokefilter.api.client.pokeapi;

import com.pokeprojects.pokefilter.api.client.GenericReactiveClient;
import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonClientDTO;
import com.pokeprojects.pokefilter.api.dto.pokemon_species.EvolutionChainDTO;
import com.pokeprojects.pokefilter.api.dto.pokemon_species.PokemonSpeciesDTO;
import com.pokeprojects.pokefilter.api.dto.type.TypeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PokeSpeciesReactiveClient extends GenericReactiveClient {
    private String baseUrl = "pokemon-species";
    private Logger logger = LoggerFactory.getLogger(PokeSpeciesReactiveClient.class);

    @Autowired
    public PokeSpeciesReactiveClient(WebClient webClient){
        super(webClient);
    }

    public Mono<PokemonSpeciesDTO> getPokemonSpecie(String identifier){
        //logger.info("Trying to fetch pokemon specie with identification: {}", identifier);
        Mono<PokemonSpeciesDTO> mono = this.getResource(PokemonSpeciesDTO.class, identifier, baseUrl)
                .doOnError(WebClientException -> logger.warn("Fetch failed for pokemon with identification: {}", identifier, WebClientException));
        return mono;
    }

    public Mono<EvolutionChainDTO> getPokemonEvolutionChain(String identifier){
        logger.info("Trying to fetch evolution chain from pokemon : {}", identifier);
        return getResource(PokemonSpeciesDTO.class, identifier, baseUrl)
                .flatMap(specie -> getSingleResource(specie.getEvolutionChain(), EvolutionChainDTO.class));
    }
}
