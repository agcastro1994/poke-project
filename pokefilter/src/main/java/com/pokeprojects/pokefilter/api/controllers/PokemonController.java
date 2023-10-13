package com.pokeprojects.pokefilter.api.controllers;

import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonExternalDTO;
import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonSmallDTO;
import com.pokeprojects.pokefilter.api.dto.type.TypeExternalDTO;
import com.pokeprojects.pokefilter.api.model.move.Move;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import com.pokeprojects.pokefilter.api.model.type.Type;
import com.pokeprojects.pokefilter.api.services.pokemon.PokeApiService;
import com.pokeprojects.pokefilter.api.utils.PokemonDamageCalculator;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@ResponseBody
@RequestMapping("pokemon")
public class PokemonController {
    private PokeApiService pokeApiService;
    private PokemonDamageCalculator damageCalculator;
    private ModelMapper modelMapper;

    private Logger logger = LoggerFactory.getLogger(PokemonController.class);

    public PokemonController(PokeApiService pokeApiService, PokemonDamageCalculator damageCalculator, ModelMapper modelMapper) {
        this.pokeApiService = pokeApiService;
        this.damageCalculator = damageCalculator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PokemonSmallDTO>> getAllPokemon(){
        logger.info("Received request for all pokemon");
        return ResponseEntity.ok(pokeApiService.getAllPokemon().stream().map(poke->modelMapper.map(poke, PokemonSmallDTO.class)).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PokemonSmallDTO> getPokemon(@PathVariable String id) {
        logger.info("Received request for retrieving pokemon with identifier {}", id);
        Pokemon response = pokeApiService.getPokemon(id);
        logger.info("Retrieving pokemon with identifier {}", id);
        return ResponseEntity.ok(modelMapper.map(response, PokemonSmallDTO.class));
    }

    @GetMapping("/damage")
    public ResponseEntity<Double> getPokemon(@RequestParam String attackerId, @RequestParam String defenderId, @RequestParam String moveId) {
        Pokemon attacker = pokeApiService.getPokemon(attackerId);
        Pokemon defender = pokeApiService.getPokemon(defenderId);
        Move move = pokeApiService.getMoveByIdOrName(moveId);
        return ResponseEntity.ok(damageCalculator.damageCalculator(attacker,defender,move));
    }

    @GetMapping("/{id}/types")
    public ResponseEntity<List<TypeExternalDTO>> getPokemonTypes(@PathVariable String id) {
        List<Type> response = pokeApiService.getPokemonTypes(id);
        return ResponseEntity.ok(response.stream().map(item -> modelMapper.map(item, TypeExternalDTO.class)).toList());
    }

    @GetMapping("/filter-params")
    public ResponseEntity<List<PokemonSmallDTO>> filterPokemonByParams(@RequestParam Map<String, String> requestParams) {
        List<Pokemon> pokemonList = pokeApiService.getAllPokemonByFilters(requestParams);
        return ResponseEntity.ok(pokemonList.stream().map(poke -> modelMapper.map(poke, PokemonSmallDTO.class)).toList());
    }

}
