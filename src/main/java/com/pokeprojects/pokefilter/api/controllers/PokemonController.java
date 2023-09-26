package com.pokeprojects.pokefilter.api.controllers;


import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonExternalDTO;
import com.pokeprojects.pokefilter.api.dto.pokemon.PokemonSmallDTO;
import com.pokeprojects.pokefilter.api.dto.type.TypeExternalDTO;
import com.pokeprojects.pokefilter.api.enums.PokemonFilters;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

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
    public ResponseEntity<PokemonExternalDTO> getPokemon(@PathVariable String id) {
        logger.info("Received request for retrieving pokemon with identifier {}", id);
        Pokemon response = pokeApiService.getPokemonInMemory(id);
        logger.info("Retrieving pokemon with identifier {}", id);
        return ResponseEntity.ok(modelMapper.map(response, PokemonExternalDTO.class));
    }

    @GetMapping("/damage")
    public ResponseEntity<Double> getPokemon(@RequestParam String attackerId, @RequestParam String defenderId, @RequestParam String moveId) {
        Pokemon attacker = pokeApiService.getPokemonInMemory(attackerId);
        Pokemon defender = pokeApiService.getPokemonInMemory(defenderId);
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

        // Create a list of predicates to represent filter criteria
        List<Predicate<Pokemon>> criteria = new ArrayList<>();

        // Iterate through the request parameters and build the filter criteria dynamically
        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            String paramName = entry.getKey();
            String paramValue = entry.getValue();

            // Find the corresponding filter in the PokemonFilters enum
            PokemonFilters filter = findFilterByName(paramName);

            if (!paramValue.equals(filter.getDefaultValue())) {
                Predicate<Pokemon> filterPredicate = filter.getFilterCondition(paramValue);
                criteria.add(filterPredicate);
            }
        }
        List<Pokemon> pokemonList = pokeApiService.getAllPokemonByFilters(criteria);

        return ResponseEntity.ok(pokemonList.stream().map(poke -> modelMapper.map(poke, PokemonSmallDTO.class)).toList());
    }

    private PokemonFilters findFilterByName(String paramName) {
        for (PokemonFilters filter : PokemonFilters.values()) {
            if (filter.getFilterName().equals(paramName)) {
                return filter;
            }
        }
        throw new NoSuchElementException("At least one of your filter parameters is not valid, please try again"); // Return null for unknown filter names
    }

}
