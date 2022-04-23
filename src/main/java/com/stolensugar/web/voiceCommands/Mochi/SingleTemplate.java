package com.stolensugar.web.voiceCommands.Mochi;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "~:id",
        "~:name",
        "~:content",
        "~:pos",
        "~:fields"
})

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class SingleTemplate {
    @JsonProperty("~:id")
    private @Getter @Setter String id;
    @JsonProperty("~:name")
    private @Getter @Setter String name;
    @JsonProperty("~:content")
    private @Getter @Setter String content;
    @JsonProperty("~:pos")
    private @Getter @Setter String pos;
    @JsonProperty("~:fields")
    private @Getter @Setter TemplateFields fields;
}
