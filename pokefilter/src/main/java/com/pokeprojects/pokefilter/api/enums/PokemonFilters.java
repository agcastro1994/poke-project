package com.pokeprojects.pokefilter.api.enums;

import com.pokeprojects.pokefilter.api.indexes.PokemonIndex;
import com.pokeprojects.pokefilter.api.indexes.PokemonTypeIndex;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import com.pokeprojects.pokefilter.api.model.pokemon.PokemonStat;
import com.pokeprojects.pokefilter.api.model.stat.Stat;
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
    FULLY_EVOLVED("false","fully_evolved", false){
        @Override
        public Predicate<Pokemon> getFilterCondition(String condition){
            return null;
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
