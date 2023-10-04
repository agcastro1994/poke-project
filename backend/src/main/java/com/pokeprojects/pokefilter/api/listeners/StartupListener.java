package com.pokeprojects.pokefilter.api.listeners;

import com.pokeprojects.pokefilter.api.services.pokemon.PokeApiService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class StartupListener {

    PokeApiService pokeApiService;

    public StartupListener(PokeApiService pokeApiService) {
        this.pokeApiService = pokeApiService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        pokeApiService.loadAllPokemonInMemory();
        //TODO replace this with logger
        System.out.println("Pokemon list loaded in memory");
    }
}
