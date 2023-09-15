package com.pokeprojects.pokefilter.api.controllers;

import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonExternalDTO;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import com.pokeprojects.pokefilter.api.services.PokeApiService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("pokemon")
public class PokemonController {
    private PokeApiService pokeApiService;
    private ModelMapper modelMapper;

    public PokemonController(PokeApiService pokeApiService, ModelMapper modelMapper) {
        this.pokeApiService = pokeApiService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PokemonExternalDTO> getPokemon(@PathVariable String id) {
        Pokemon response = pokeApiService.getPokemonByIdOrName(id);
        return ResponseEntity.ok(modelMapper.map(response, PokemonExternalDTO.class));
    }
}
