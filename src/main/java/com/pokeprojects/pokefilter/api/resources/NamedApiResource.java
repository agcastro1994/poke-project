package com.pokeprojects.pokefilter.api.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NamedApiResource<T extends StandardApiResource>{
    private String name;
    private String url;
}
