package com.stolensugar.web.controller.mappers.Mochi;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import javax.annotation.processing.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "~:name",
        "~:id",
        "~:sort",
        "~:cards"
})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class Deck {

    @JsonProperty("~:name")
    private @Getter @Setter String name = "keys";
    @JsonProperty("~:id")
    private @Getter @Setter String id = "~:UHiXar0B";
    @JsonProperty("~:sort")
    private @Getter @Setter Integer sort = 0;
    @JsonProperty("~:cards")
    private @Getter @Setter Cards cards;

}