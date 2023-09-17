package com.pokeprojects.pokefilter.api.controllers;

import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonExternalDTO;
import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonSmallDTO;
import com.pokeprojects.pokefilter.api.dto.type.TypeExternalDTO;
import com.pokeprojects.pokefilter.api.enums.Region;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import com.pokeprojects.pokefilter.api.model.type.Type;
import com.pokeprojects.pokefilter.api.services.pokemon.PokeApiService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}/types")
    public ResponseEntity<List<TypeExternalDTO>> getPokemonTypes(@PathVariable String id) {
        List<Type> response = pokeApiService.getPokemonTypesInfo(id);
        return ResponseEntity.ok(response.stream().map(item -> modelMapper.map(item, TypeExternalDTO.class)).toList());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<PokemonSmallDTO>> getPokemonByType(@RequestParam String typeId, @RequestParam String region) {
        List<Pokemon> pokemonList = pokeApiService.getPokemonByTypeAndRegion(typeId, region);
        return ResponseEntity.ok(pokemonList.stream().map(poke -> modelMapper.map(poke, PokemonSmallDTO.class)).toList());
    }
}
