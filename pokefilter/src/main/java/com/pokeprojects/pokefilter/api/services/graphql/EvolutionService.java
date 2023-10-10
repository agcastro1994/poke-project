package com.pokeprojects.pokefilter.api.services.graphql;

import com.pokeprojects.pokefilter.api.client.graphql.GraphqlClient;
import com.pokeprojects.pokefilter.api.model.records.PokemonSpecy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class EvolutionService {
    private GraphqlClient client;

    public EvolutionService(GraphqlClient client){
        this.client = client;
    }


    public List<PokemonSpecy> getSpecies(){
        String document = """
                query {
                    species: pokemon_v2_pokemonspecies {
                        id
                        evolves_from_species_id
                        name
                        is_legendary
                        is_mythical
                        is_baby
                    }                        
                }   
                """;

        Mono<List<PokemonSpecy>> species = client.getResourceList(document, PokemonSpecy.class, "species");
        return species.block();
    }

    public List<PokemonSpecy> getSpeciesEvolutionData(){
        String document = """
                query {
                    species: pokemon_v2_pokemonspecies(where: {_or: [{is_legendary: {_eq: true}}, {is_mythical: {_eq: true}}, {_not: {evolves_from_species_id: {_is_null: true}}}], _and: {is_baby: {_eq: false}}}) {
                        id
                        evolves_from_species_id
                        name
                        is_legendary
                        is_mythical
                    }
                        
                }   
                """;

        Mono<List<PokemonSpecy>> species = client.getResourceList(document, PokemonSpecy.class, "species");
        return species.block();
    }
}


