package com.pokeprojects.pokefilter.api.listeners;

import com.pokeprojects.pokefilter.api.services.pokemon.PokeApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@Profile("default")
public class StartupListener {

    PokeApiService pokeApiService;

    private Logger logger = LoggerFactory.getLogger(StartupListener.class);

    public StartupListener(PokeApiService pokeApiService) {
        this.pokeApiService = pokeApiService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        pokeApiService.loadStartupData();
        logger.info("Pokemon list loaded in memory");
        logger.info("Pokemon indexes loaded in memory");
    }
}
