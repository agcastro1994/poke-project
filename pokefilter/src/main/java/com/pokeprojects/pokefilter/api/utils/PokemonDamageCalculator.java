package com.pokeprojects.pokefilter.api.utils;

import com.pokeprojects.pokefilter.api.model.move.Move;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import com.pokeprojects.pokefilter.api.model.type.Type;
import com.pokeprojects.pokefilter.api.services.pokemon.PokeApiService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PokemonDamageCalculator {
    private static final Double RANDOM_WORST_CASE = 0.85;
    private static final Double RANDOM_BEST_CASE = 1.0;
    private static final Double STAB = 1.50;
    private static final Double CRITICAL = 1.50;
    private static final Double RESISTED = 0.5;
    private static final Double DOUBLE_RESISTED = 0.25;
    private static final Double NO_DAMAGE = 0.0;
    private static final Double BURN = 0.5;
    private static final Double WEAK = 2.0;
    private static final Double DOUBLE_WEAK = 4.0;
    private static final Integer LEVEL = 50;
    private static final Integer IV = 31;
    private static final Integer EV = 0;
    private static final Double REGULAR_DAMAGE = 1.0;
    private PokeApiService pokeApiService;


    public PokemonDamageCalculator(PokeApiService pokeApiService) {
        this.pokeApiService = pokeApiService;
    }

    public Double damageCalculator(Pokemon attacker, Pokemon defender, Move moveUsed){
        String statName = moveUsed.getDamageClass().getName().equals("physical") ? "attack" : "special-attack";
        String defenseStatName = statName.equals("attack") ? "defense" : "special-defense";

        Integer attackerStat = attacker.getStats().stream().filter(s->s.getStat().getName().equals(statName)).findFirst().get().getBaseStat();
        Integer defenderStat = defender.getStats().stream().filter(s->s.getStat().getName().equals(defenseStatName)).findFirst().get().getBaseStat();

        Integer defenderHP = defender.getStats().stream().filter(s->s.getStat().getName().equals("hp")).findFirst().get().getBaseStat();

        int hpScaledFactor = (2 * defenderHP + IV + EV/4 + 100) * LEVEL/100 +10;
        int attackScaledFactor = (2 * attackerStat + IV + EV/4) * LEVEL/100 +5;
        int defenseScaledFactor = (2 * defenderStat + IV + EV/4) * LEVEL/100 +5;

        double powerAndStats = (double) moveUsed.getPower() * attackScaledFactor/defenseScaledFactor;
        double levelFactor = (double) ((2 * 50)/5 + 2)/50;

        List<Type> attackerTypes = pokeApiService.getPokemonTypes(attacker.getId().toString());
        List<Type> defenderTypes = pokeApiService.getPokemonTypes(defender.getId().toString());
        Type moveType = pokeApiService.getPokemonMoveType(moveUsed);

        double stabMultiplier = attackerTypes.stream().anyMatch(type-> type.getName().equals(moveType.getName())) ? STAB : REGULAR_DAMAGE;

        double immunity = defenderTypes.stream().anyMatch(type -> type.getDamageRelations().getNoDamageFrom().stream().anyMatch(t -> t.getName().equals(moveType.getName()))) ? NO_DAMAGE : 1.0;
        double weaknessMult = REGULAR_DAMAGE;
        double resistenceMult = REGULAR_DAMAGE;
        if(immunity != NO_DAMAGE){
            weaknessMult = defenderTypes.stream().mapToDouble(type-> type.getDamageRelations().getDoubleDamageFrom().stream().anyMatch(t -> t.getName().equals(moveType.getName())) ? WEAK : NO_DAMAGE).sum();
            resistenceMult = defenderTypes.stream().map(type-> type.getDamageRelations().getHalfDamageFrom().stream().anyMatch(t -> t.getName().equals(moveType.getName())) ? RESISTED : REGULAR_DAMAGE)
                    .reduce(resistenceMult, (a, b) -> a * b);
        }

        return 100 - ( (hpScaledFactor - levelFactor * powerAndStats * stabMultiplier * immunity * weaknessMult * resistenceMult)/hpScaledFactor ) * 100;
    }
}
