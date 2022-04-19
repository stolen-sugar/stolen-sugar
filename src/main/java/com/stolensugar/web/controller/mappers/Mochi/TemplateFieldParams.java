package com.stolensugar.web.controller.mappers.Mochi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonPropertyOrder({
        "~:id",
        "~:name",
        "~:pos"
})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class TemplateFieldParams {
    @JsonProperty("~:id")
    private @Getter @Setter String id;
    @JsonProperty("~:name")
    private @Getter @Setter String name;
    @JsonProperty("~:pos")
    private @Getter @Setter String pos;

}