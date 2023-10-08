package com.pokeprojects.pokefilter.api.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NamedApiResource<T extends StandardApiResource>{
    private String name;
    private String url;
}
