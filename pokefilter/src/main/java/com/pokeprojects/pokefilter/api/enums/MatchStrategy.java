package com.pokeprojects.pokefilter.api.enums;

import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

public enum MatchStrategy {
        ANY {
            @Override
            public BinaryOperator<Predicate<Pokemon>> accumulator() {
                return Predicate::or;
            }
        },
        ALL {
            @Override
            public BinaryOperator<Predicate<Pokemon>> accumulator() {
                return Predicate::and;
            }
        };

    public abstract BinaryOperator<Predicate<Pokemon>> accumulator();

}
