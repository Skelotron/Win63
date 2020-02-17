package ru.skelotron.win63.http_entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Photo {
    @JsonProperty("img")
    private String imageUrl;

    @JsonProperty("alt")
    private String description;

    private transient byte[] content;
}
