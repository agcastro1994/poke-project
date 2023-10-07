package com.pokeprojects.pokefilter.api.resources;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResource<T extends StandardApiResource> {
    private String url;
}
