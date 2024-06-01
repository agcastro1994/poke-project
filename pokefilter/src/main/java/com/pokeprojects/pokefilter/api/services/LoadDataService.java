package com.pokeprojects.pokefilter.api.services;

import com.pokeprojects.pokefilter.api.client.pokeapi.PokeReactiveClient;
import com.pokeprojects.pokefilter.api.enums.Region;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import com.pokeprojects.pokefilter.api.model.records.PokemonSpecy;
import com.pokeprojects.pokefilter.api.repository.pokemon.PokemonInMemoryRepository;
import com.pokeprojects.pokefilter.api.repository.pokemon.indexes.Indexes;
import com.pokeprojects.pokefilter.api.services.graphql.EvolutionService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoadDataService {
    private final EvolutionService evolutionService;
    private final PokeReactiveClient reactiveClient;
    private final ModelMapper mapper;
    private Indexes<Pokemon, String> typeIndex;
    private Indexes<Pokemon, String> regionIndex;
    private final PokemonInMemoryRepository inMemoryRepository;
    private volatile boolean isLoading = false;
    private Logger logger = LoggerFactory.getLogger(LoadDataService.class);

    public LoadDataService(EvolutionService evolutionService, PokeReactiveClient reactiveClient, ModelMapper mapper, @Qualifier("pokemonTypeIndex") Indexes<Pokemon, String> typeIndex, @Qualifier("pokemonRegionIndex") Indexes<Pokemon, String> regionIndex, PokemonInMemoryRepository inMemoryRepository) {
        this.evolutionService = evolutionService;
        this.reactiveClient = reactiveClient;
        this.mapper = mapper;
        this.typeIndex = typeIndex;
        this.regionIndex = regionIndex;
        this.inMemoryRepository = inMemoryRepository;

    }
    @PostConstruct
    public void loadStartupData() {
        isLoading = true;
        List<PokemonSpecy> species  = evolutionService.getSpecies();
        List<PokemonSpecy> evolvedSpecies  = evolutionService.getSpeciesEvolutionData();
        List<PokemonSpecy> lastEvolutions =  fullyEvolvedSpecies(evolvedSpecies);
        for(Region region : Region.getAllRegions()){
            List<Pokemon> regionPokemon = reactiveClient.getAllPokemonByRegion(region).stream().map(poke -> mapper.map(poke, Pokemon.class)).toList();

            regionPokemon.forEach(poke -> {
                PokemonSpecy spe = species.stream().filter(sp -> Objects.equals(sp.id(), poke.getId())).findFirst().get();
                poke.setSpecies(spe);
                poke.setIsFullyEvolved(lastEvolutions.stream().anyMatch(spec-> Objects.equals(spec.id(), poke.getId())) || poke.getSpecies().is_legendary() || poke.getSpecies().is_mythical());
            });
            inMemoryRepository.loadIndex(regionPokemon);
            typeIndex.loadIndex(regionPokemon);
            regionIndex.loadIndex(regionPokemon);
        }
        isLoading = false;
        logger.info("Pokemon list loaded in memory");
        logger.info("Pokemon indexes loaded in memory");
    }


    private List<PokemonSpecy> fullyEvolvedSpecies(List<PokemonSpecy> species){
        Set<Integer> evolvesFromSet = species.stream()
                .map(PokemonSpecy::evolves_from_species_id)
                .collect(Collectors.toSet());
        return species.stream()
                .filter(sp -> !evolvesFromSet.contains(sp.id()))
                .toList();
    }

}
