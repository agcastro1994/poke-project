package com.pokeprojects.pokefilter.api.enums;

import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import com.pokeprojects.pokefilter.api.model.pokemon.PokemonStat;
import lombok.Getter;

import java.util.Comparator;
import java.util.function.Predicate;

@Getter
public enum PokemonFilters{

    TYPE("all", "type", true){
        @Override
        public Predicate<Pokemon> getFilterCondition(String type){
            PokemonTypes searchType = PokemonTypes.typeMatch(type);
            return pokemon -> pokemon.getTypes().stream().anyMatch(t->PokemonTypes.isAMatch(t.getType().getName(),searchType));
        }
    },
    HAS_MOVE("any", "has_move", false){
        @Override
        public Predicate<Pokemon> getFilterCondition(String move){
            return pokemon -> pokemon.getMoves().stream().anyMatch(m->m.getMove().getName().equals(move));
        }
    },
    HAS_ABILITY("any", "ability", false){
        @Override
        public Predicate<Pokemon> getFilterCondition(String ability){
            return pokemon -> pokemon.getAbilities().stream().anyMatch(m->m.getAbility().getName().equals(ability));
        }
    },
    PRIMARY_STAT("any", "primary_stat", false){
        @Override
        public Predicate<Pokemon> getFilterCondition(String statName){
            return pokemon -> pokemon.getStats().stream().max(Comparator.comparing(PokemonStat::getBaseStat)).get().getStat().getName().equals(statName);
        }
    },
    LOWER_STAT("any", "lower_stat", false){
        @Override
        public Predicate<Pokemon> getFilterCondition(String statName){
            return pokemon -> pokemon.getStats().stream().min(Comparator.comparing(PokemonStat::getBaseStat)).get().getStat().getName().equals(statName);
        }
    },
    REGION("all", "region", true){
        @Override
        public Predicate<Pokemon> getFilterCondition(String region){
            Region selectedRegion = Region.valueOf(region);
            return pokemon -> selectedRegion.getOffset() < pokemon.getId() && pokemon.getId() <= selectedRegion.getLimit()+ selectedRegion.getOffset();
        }
    },
    FULLY_EVOLVED("","fully_evolved", false){
        @Override
        public Predicate<Pokemon> getFilterCondition(String condition){
            return pokemon -> pokemon.getIsFullyEvolved().equals(Boolean.parseBoolean(condition));
        }
    },
    LEGENDARY("","legendary", false){
        @Override
        public Predicate<Pokemon> getFilterCondition(String condition){
            return pokemon -> pokemon.getSpecies().is_legendary().equals(Boolean.parseBoolean(condition));
        }
    },
    MYTHICAL("","mythical", false){
        @Override
        public Predicate<Pokemon> getFilterCondition(String condition){
            return pokemon -> pokemon.getSpecies().is_mythical().equals(Boolean.parseBoolean(condition));
        }
    },
    BABY("false","baby", false){
        @Override
        public Predicate<Pokemon> getFilterCondition(String condition){
            return pokemon -> pokemon.getSpecies().is_baby().equals(Boolean.parseBoolean(condition));
        }
    };

    private final String defaultValue;
    private final String filterName;
    private final boolean indexed;

    PokemonFilters(String defaultValue, String filterName, boolean isIndexed) {
        this.defaultValue = defaultValue;
        this.filterName = filterName;
        this.indexed = isIndexed;
    }


    public abstract Predicate<Pokemon> getFilterCondition(String object);

}
