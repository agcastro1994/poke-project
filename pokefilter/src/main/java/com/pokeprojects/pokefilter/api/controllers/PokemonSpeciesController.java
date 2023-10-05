package com.pokeprojects.pokefilter.api.controllers;

import com.pokeprojects.pokefilter.api.dto.pokemon_species.EvolutionChainDTO;
import com.pokeprojects.pokefilter.api.services.pokemon_species.PokemonSpeciesService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("pokemon-species")
public class PokemonSpeciesController {
    private ModelMapper modelMapper;
    private Logger logger = LoggerFactory.getLogger(PokemonController.class);
    private PokemonSpeciesService service;

    public PokemonSpeciesController(ModelMapper modelMapper, PokemonSpeciesService service) {
        this.modelMapper = modelMapper;
        this.service = service;
    }

    @GetMapping("/evolution-chain/{id}")
    public ResponseEntity<EvolutionChainDTO> getPokemonEvolutionChain(@PathVariable String id) {
        logger.info("Received request for retrieving pokemon species with identifier {}", id);
        EvolutionChainDTO chain = service.getEvolutionChain(id);
        logger.info("Retrieving pokemon species with identifier {}", id);
        return ResponseEntity.ok(chain);
    }
}
