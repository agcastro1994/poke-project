package com.pokeprojects.pokefilter.api.client;

import com.pokeprojects.pokefilter.api.resources.NamedApiResource;
import com.pokeprojects.pokefilter.api.resources.StandardApiResource;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class GenericReactiveClient {

    private WebClient webClient;

    public GenericReactiveClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public <T extends StandardApiResource> Mono<T> getResource(Class<T> resourceClass, String nameOrId, String url) {
         return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url)
                        .path("/{id}")
                        .build(nameOrId))
                .retrieve()
                .bodyToMono(resourceClass);
    }

    public <T extends StandardApiResource> Mono<T> followResource(Supplier<NamedApiResource<T>> resourceSupplier, Class<T> cls) {
        return Mono.fromSupplier(resourceSupplier)
                .flatMap(resource -> getNamedResource(resource, cls));
    }

    public <T extends StandardApiResource> Flux<T> followResources(Supplier<List<NamedApiResource<T>>> resourcesSupplier, Class<T> cls) {
        return Mono.fromSupplier(resourcesSupplier)
                .flatMapMany(resources -> getNamedResources(resources, cls));
    }

    private <T extends StandardApiResource> Mono<T> getNamedResource(NamedApiResource<T> resource, Class<T> resourceClass) {
        return webClient.get()
                .uri(URI.create(resource.getUrl()))
                .retrieve()
                .bodyToMono(resourceClass);
    }

    private <T extends StandardApiResource> Flux<T> getNamedResources(List<NamedApiResource<T>> resources, Class<T> resourceClass) {
        List<Mono<T>> resourceMonos = resources.stream()
                .map(resource -> getNamedResource(resource, resourceClass))
                .collect(Collectors.toList());

        return Flux.merge(resourceMonos);
    }

}
