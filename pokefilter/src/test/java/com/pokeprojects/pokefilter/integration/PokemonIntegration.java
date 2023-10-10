package com.pokeprojects.pokefilter.integration;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.pokeprojects.pokefilter.api.model.pokemon.*;
import com.pokeprojects.pokefilter.api.model.sprites.PokemonSprites;
import com.pokeprojects.pokefilter.api.repository.pokemon.PokemonInMemoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import wiremock.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles(value = "test")
@WireMockTest
public class PokemonIntegration {
    @Autowired
    private MockMvc mockMvc;
    private WireMockServer wireMockServer;
    @Autowired
    private PokemonInMemoryRepository inMemoryRepository;
    private List<Pokemon> pokemonList;

    @BeforeAll
    public void setup() throws IOException {

        PokemonAbility ability = mock(PokemonAbility.class);
        PokemonMove move = mock(PokemonMove.class);
        PokemonSprites sprites = mock(PokemonSprites.class);
        PokemonStat stat = mock(PokemonStat.class);
        PokemonType type = mock(PokemonType.class);
        Pokemon pokemon1 = new Pokemon(6,"charizard", 267, 17, true, 7, 905, List.of(ability), List.of(move), sprites, List.of(stat), List.of(type),null,true);
        Pokemon pokemon2 = new Pokemon(3,"venusaur", 263, 20, true, 3, 1000, List.of(ability), List.of(move), sprites, List.of(stat), List.of(type),null,true);
        Pokemon pokemon3 = new Pokemon(9,"blastoise", 265, 16, true, 12, 855, List.of(ability), List.of(move), sprites, List.of(stat), List.of(type),null,true);
        Pokemon pokemon4 = new Pokemon(59,"arcanine", 194, 19, true, 98, 1550, List.of(ability), List.of(move), sprites, List.of(stat), List.of(type),null,true);
        Pokemon pokemon5 = new Pokemon(157,"typhlosion", 240, 17, true, 254, 795, List.of(ability), List.of(move), sprites, List.of(stat), List.of(type),null,true);

        pokemonList = List.of(pokemon1,pokemon2,pokemon3,pokemon4,pokemon5);

        wireMockServer = new WireMockServer(
                options().extensions(new ResponseTemplateTransformer(false))
                        .port(8090));
        configureFor("localhost", 8090);
        wireMockServer.start();
    }

    @Test
    public void getPokemonTest() throws Exception {
        String pokemonResponse = FileUtils.readFileToString(new File("src/test/java/resources/charizard.json"), StandardCharsets.UTF_8);

        wireMockServer.stubFor(get(urlPathMatching("/pokemon/6"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(pokemonResponse)
                        .withTransformers("response-template")));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/pokemon/{id}",6))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.name").value("charizard"))
                .andExpect(jsonPath("$.abilities", hasSize(2)))
                .andExpect(jsonPath("$.abilities[0].ability.name").value("blaze"))
                .andExpect(jsonPath("$.abilities[0].is_hidden").value(false))
                .andExpect(jsonPath("$.abilities[1].ability.name").value("solar-power"))
                .andExpect(jsonPath("$.abilities[1].is_hidden").value(true))
                .andExpect(jsonPath("$.types[1].type.name").value("flying"))
                .andExpect(jsonPath("$.types", hasSize(2)))
                .andExpect(jsonPath("$.types[0].type.name").value("fire"))
                .andExpect(jsonPath("$.types[1].type.name").value("flying"));
    }

    @Test
    public void getAllPokemonTest() throws Exception {
        //Storing all my Pokemon in memory
        inMemoryRepository.addPokemonList(pokemonList);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/pokemon/all"))
                .andDo(print())
                .andExpect(jsonPath("$[0].name").value("venusaur"))
                .andExpect(jsonPath("$[0].id").value(3))
                .andExpect(jsonPath("$[1].name").value("charizard"))
                .andExpect(jsonPath("$[1].id").value(6))
                .andExpect(jsonPath("$[2].name").value("blastoise"))
                .andExpect(jsonPath("$[2].id").value(9))
                .andExpect(jsonPath("$[3].name").value("arcanine"))
                .andExpect(jsonPath("$[3].id").value(59))
                .andExpect(jsonPath("$[4].name").value("typhlosion"))
                .andExpect(jsonPath("$[4].id").value(157));
    }
}
