package com.pokeprojects.pokefilter.api.controllers;

import com.pokeprojects.pokefilter.api.client.pokeapi.PokeReactiveClient;
import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonExternalDTO;
import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonSmallDTO;
import com.pokeprojects.pokefilter.api.dto.type.TypeExternalDTO;
import com.pokeprojects.pokefilter.api.enums.Region;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import com.pokeprojects.pokefilter.api.model.type.Type;
import com.pokeprojects.pokefilter.api.services.pokemon.PokeApiService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("pokemon")
public class PokemonController {
    private PokeApiService pokeApiService;
    private ModelMapper modelMapper;

    private Logger logger = LoggerFactory.getLogger(PokemonController.class);

    public PokemonController(PokeApiService pokeApiService, ModelMapper modelMapper) {
        this.pokeApiService = pokeApiService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PokemonSmallDTO>> getAllPokemon(){
        logger.info("Received request for all pokemon");
        return ResponseEntity.ok(pokeApiService.getAllPokemon().stream().map(poke->modelMapper.map(poke, PokemonSmallDTO.class)).toList());
    }

    @GetMapping
    public ResponseEntity<List<PokemonSmallDTO>> getAllPokemonByRegion(@RequestParam Region region) {
        logger.info("Received request for filtering pokemon from region {}", region);
        List<PokemonSmallDTO> pokemonList = pokeApiService.getAllPokemonByRegion(region).stream().map(poke->modelMapper.map(poke, PokemonSmallDTO.class)).toList();
        logger.info("Retrieving a list of {} pokemon", pokemonList.size());
        return ResponseEntity.ok(pokemonList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PokemonExternalDTO> getPokemon(@PathVariable String id) {
        logger.info("Received request for retrieving pokemon with identifier {}", id);
        Pokemon response = pokeApiService.getPokemonByIdOrName(id);
        logger.info("Retrieving pokemon with identifier {}", id);
        return ResponseEntity.ok(modelMapper.map(response, PokemonExternalDTO.class));
    }

    @GetMapping("/{id}/types")
    public ResponseEntity<List<TypeExternalDTO>> getPokemonTypes(@PathVariable String id) {
        List<Type> response = pokeApiService.getPokemonTypesInfo(id);
        return ResponseEntity.ok(response.stream().map(item -> modelMapper.map(item, TypeExternalDTO.class)).toList());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<PokemonSmallDTO>> getPokemonByType(@RequestParam String typeId, @RequestParam String region) {
        logger.info("Received request for filtering with type {} and region {}", typeId, region);
        List<Pokemon> pokemonList = pokeApiService.getPokemonByTypeAndRegion(typeId, region);
        logger.info("Retrieving a list of {} pokemon", pokemonList.size());
        return ResponseEntity.ok(pokemonList.stream().map(poke -> modelMapper.map(poke, PokemonSmallDTO.class)).toList());
    }
}
