package com.pokeprojects.pokefilter.api.client.graphql;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.client.GraphQlClient;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class GraphqlClient {
    @Value("${graphql.api.url}")
    private String graphqlApiUrl;

    GraphQlClient client;

    @PostConstruct
    void init() {
        WebClient wc = WebClient.create(graphqlApiUrl);
        client = HttpGraphQlClient.create(wc);
    }

    public <T> Mono<List<T>> getResourceList(String query, Class<T> clazz, String path){
        return client.document(query)
                .retrieve(path)
                .toEntityList(clazz);
    }
}
