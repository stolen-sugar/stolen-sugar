package com.stolensugar.web.voiceCommands.Mochi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "~:content",
        "~:name",
        "~:deck-id",
        "~:fields",
        "~:template-id"
})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class SingleCard {
    @JsonProperty("~:content")
    private @Getter @Setter String content = "**What is talon the phrase for action below?**";
    @JsonProperty("~:name")
    private @Getter @Setter String name;
    @JsonProperty("~:deck-id")
    private @Getter @Setter String deckId = "~:UHiXar0B";
    @JsonProperty("~:fields")
    private @Getter @Setter Fields fields;
    @JsonProperty("~:template-id")
    private @Getter @Setter String templateId = "~:ftOIZq7E";
}
