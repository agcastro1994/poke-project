package com.pokeprojects.pokefilter.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pokeprojects.pokefilter.api.resources.NamedApiResource;
import com.pokeprojects.pokefilter.api.resources.StandardApiResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDTO<T extends StandardApiResource> implements StandardApiResource {
    private Integer count;
    private String next;
    private String previous;
    private List<NamedApiResource<T>> results;

    @Override
    @JsonIgnore
    public Integer getId() {
        return 0;
    }
    @Override
    @JsonIgnore
    public String getName() {
        return "";
    }
}
