package com.pokeprojects.pokefilter.api.services.pokemon_species;

import com.pokeprojects.pokefilter.api.client.pokeapi.PokeReactiveClient;
import com.pokeprojects.pokefilter.api.client.pokeapi.PokeSpeciesReactiveClient;
import com.pokeprojects.pokefilter.api.dto.pokemon_species.ChainDTO;
import com.pokeprojects.pokefilter.api.dto.pokemon_species.EvolutionChainDTO;
import com.pokeprojects.pokefilter.api.dto.pokemon_species.PokemonSpeciesDTO;
import com.pokeprojects.pokefilter.api.model.pokemon_species.EvolutionChain;
import com.pokeprojects.pokefilter.api.model.pokemon_species.PokemonSpecies;
import com.pokeprojects.pokefilter.api.repository.pokemon.PokemonInMemoryRepository;
import com.pokeprojects.pokefilter.api.services.pokemon.PokeApiService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PokemonSpeciesService {
    private PokeSpeciesReactiveClient reactiveClient;
    private PokemonInMemoryRepository pokemonInMemoryRepository;
    private ModelMapper mapper;


    public PokemonSpeciesService(PokeSpeciesReactiveClient reactiveClient, PokemonInMemoryRepository pokemonInMemoryRepository, ModelMapper mapper) {
        this.reactiveClient = reactiveClient;
        this.pokemonInMemoryRepository = pokemonInMemoryRepository;
        this.mapper = mapper;
    }

    public EvolutionChainDTO getEvolutionChain(String id){
        EvolutionChainDTO chain = reactiveClient.getPokemonEvolutionChain(id).block();
        chain.setName(chain.getChain().getSpecies().getName() + " evolution chain");
        return chain;
    }

    public PokemonSpecies getPokemonSpecies(String id){
        PokemonSpeciesDTO dto = reactiveClient.getPokemonSpecie(id).block();
        EvolutionChain chain = mapper.map(reactiveClient.getSingleResource(dto.getEvolutionChain(),EvolutionChainDTO.class).block(), EvolutionChain.class);
        PokemonSpecies species = mapper.map(dto, PokemonSpecies.class);
        species.setFullEvolutionChain(chain);
        return species;
    }
}
