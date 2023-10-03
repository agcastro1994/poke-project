package com.pokeprojects.pokefilter.api.utils;

import com.pokeprojects.pokefilter.api.model.move.Move;
import com.pokeprojects.pokefilter.api.model.pokemon.Pokemon;
import com.pokeprojects.pokefilter.api.model.type.Type;
import com.pokeprojects.pokefilter.api.model.pokemon.PokemonStat;
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

        int attackerStat = getStat(attacker,statName);
        int defenderStat = getStat(defender,getDefenseStatName(statName));

        int defenderHP = getStat(defender, "hp");

        int hpScaledFactor = calculateHPScaledFactor(defenderHP, LEVEL);
        int attackScaledFactor = calculateStatScaledFactor(attackerStat, LEVEL);
        int defenseScaledFactor = calculateStatScaledFactor(defenderStat, LEVEL);

        double powerAndStats = calculatePowerAndStats(moveUsed,attackScaledFactor,defenseScaledFactor);
        double levelFactor = calculateLevelFactor(LEVEL);

        List<Type> attackerTypes = pokeApiService.getPokemonTypes(attacker.getId().toString());
        List<Type> defenderTypes = pokeApiService.getPokemonTypes(defender.getId().toString());
        Type moveType = pokeApiService.getPokemonMoveType(moveUsed);

        double stabMultiplier = calculateStabMultiplier(attackerTypes,moveType);

        double immunity = calculateImmunity(defenderTypes,moveType);
        double weaknessMult = REGULAR_DAMAGE;
        double resistenceMult = REGULAR_DAMAGE;
        if(immunity != NO_DAMAGE){
            weaknessMult = calculateWeaknessMultiplier(defenderTypes,moveType);
            resistenceMult = calculateResistanceMultiplier(defenderTypes, moveType);
        }

        return 100 - ( (hpScaledFactor - (levelFactor * powerAndStats + 2) * stabMultiplier * immunity * weaknessMult * resistenceMult)/hpScaledFactor ) * 100;
    }

    public int getStat(Pokemon pokemon, String statName) {
       return pokemon.getStats().stream()
                .filter(s -> s.getStat().getName().equals(statName))
                .findFirst()
                .map(PokemonStat::getBaseStat)
                .orElse(0);
    }

    public String getDefenseStatName(String damageClassName) {
        return damageClassName.equals("physical") ? "defense" : "special-defense";
    }

    public int calculateHPScaledFactor(int baseHP,  int level) {
        return (2 * baseHP + IV + EV / 4 + 100) * level / 100 + 10;
    }

    public int calculateStatScaledFactor(int baseStat, int level) {
        return (2 * baseStat + IV + EV/4) * level/100 +5;
    }

    public double calculateLevelFactor(Integer level) {
        return (double) ((2 * level) / 5 + 2) / 50;
    }

    public double calculatePowerAndStats(Move move, int attackScaledFactor, int defenseScaledFactor) {
        return (double) move.getPower() * attackScaledFactor / defenseScaledFactor;
    }

    public double calculateStabMultiplier(List<Type> attackerTypes, Type moveType) {
        return attackerTypes.stream().anyMatch(type -> type.getName().equals(moveType.getName())) ? STAB : REGULAR_DAMAGE;
    }

    private double calculateImmunity(List<Type> defenderTypes, Type moveType) {
        return defenderTypes.stream().anyMatch(type -> type.getDamageRelations().getNoDamageFrom().stream().anyMatch(t -> t.getName().equals(moveType.getName()))) ? NO_DAMAGE : 1.0;
    }

    public double calculateWeaknessMultiplier(List<Type> defenderTypes, Type moveType) {
        return defenderTypes.stream()
                .mapToDouble(type-> type.getDamageRelations()
                        .getDoubleDamageFrom().stream().anyMatch(t -> t.getName().equals(moveType.getName()))
                        ? WEAK : NO_DAMAGE).sum();

    }

    public double calculateResistanceMultiplier(List<Type> defenderTypes, Type moveType) {
        double resistanceMulti = REGULAR_DAMAGE;
        return defenderTypes.stream().map(type-> type.getDamageRelations().getHalfDamageFrom().stream().anyMatch(t -> t.getName().equals(moveType.getName())) ? RESISTED : REGULAR_DAMAGE)
                .reduce(resistanceMulti, (a, b) -> a * b);
    }
}
