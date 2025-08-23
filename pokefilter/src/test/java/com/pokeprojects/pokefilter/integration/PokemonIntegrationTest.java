package com.pokeprojects.pokefilter.integration;


import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.pokeprojects.pokefilter.api.model.pokemon.*;
import com.pokeprojects.pokefilter.api.model.sprites.PokemonSprites;
import com.pokeprojects.pokefilter.api.repository.pokemon.PokemonInMemoryRepository;
import org.junit.jupiter.api.*;
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
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles(value = "test")
@WireMockTest(httpPort = 8090)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PokemonIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
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
        inMemoryRepository.clearIndex();
    }

    @AfterEach
    void teardown() { inMemoryRepository.clearIndex(); }

    @Test
    public void getPokemonTest() throws Exception {
        String pokemonResponse = FileUtils.readFileToString(new File("src/test/java/resources/charizard.json"), StandardCharsets.UTF_8);

        stubFor(get(urlPathMatching("/pokemon/6"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(pokemonResponse)
                        .withTransformers("response-template")));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/pokemon/{id}",6))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.name").value("charizard"))
                .andExpect(jsonPath("$.types[1].type.name").value("flying"))
                .andExpect(jsonPath("$.types", hasSize(2)))
                .andExpect(jsonPath("$.height").value(17))
                .andExpect(jsonPath("$.weight").value(905))
                .andExpect(jsonPath("$.stats", hasSize(6)))
                .andExpect(jsonPath("$.stats[0].stat.name").value("hp"))
                .andExpect(jsonPath("$.stats[0].baseStat").value(78))
                .andExpect(jsonPath("$.types[0].type.name").value("fire"))
                .andExpect(jsonPath("$.types[1].type.name").value("flying"));
    }

    @Disabled("Current response is PokemonSmallDTO")
    @Test
    public void getFullPokemonTest() throws Exception {
        String pokemonResponse = FileUtils.readFileToString(new File("src/test/java/resources/charizard.json"), StandardCharsets.UTF_8);

        stubFor(get(urlPathMatching("/pokemon/6"))
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
        inMemoryRepository.loadIndex(pokemonList);

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
